package co.edu.eci.framework;

import java.io.*;
import java.lang.reflect.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;

public class MicroSpringBoot {
    private static final String STATIC_FILES_PATH = "src/main/resources/webroot/public"; // Carpeta de archivos estáticos

    public static void main(String[] args) throws Exception {
        if (args.length < 1) {
            System.out.println("Debe proporcionar el nombre de la clase controladora.");
            return;
        }

        String className = args[0];
        Class<?> controllerClass = Class.forName(className);

        if (!controllerClass.isAnnotationPresent(RestController.class)) {
            System.out.println("Error: La clase no está anotada con @RestController");
            return;
        }

        Object controllerInstance = controllerClass.getDeclaredConstructor().newInstance();
        Map<String, Method> routes = new HashMap<>();

        for (Method method : controllerClass.getDeclaredMethods()) {
            if (method.isAnnotationPresent(GetMapping.class) && method.getReturnType().equals(String.class)) {
                String path = method.getAnnotation(GetMapping.class).value();
                routes.put(path, method);
            }
        }

        int port = 8080;
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Servidor corriendo en el puerto " + port);

        while (true) {
            Socket client = serverSocket.accept();
            new Thread(() -> handleClient(client, routes, controllerInstance)).start();
        }
    }

    private static void handleClient(Socket client, Map<String, Method> routes, Object controller) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
             OutputStream out = client.getOutputStream()) {

            String requestLine = in.readLine();
            if (requestLine == null) return;

            String[] requestParts = requestLine.split(" ");
            if (requestParts.length < 2) return;

            String fullPath = requestParts[1];
            String path = fullPath.split("\\?")[0]; // Extraer la ruta sin parámetros

            if (routes.containsKey(path)) {
                // Ruta dinámica: Llamar al controlador
                Method method = routes.get(path);
                String response = (String) method.invoke(controller);

                sendResponse(out, "200 OK", "text/plain", response.getBytes());
            } else {
                // Ruta de archivo estático
                File file = new File(STATIC_FILES_PATH + path);
                if (file.exists() && !file.isDirectory()) {
                    byte[] fileBytes = Files.readAllBytes(file.toPath());
                    String contentType = getContentType(file.getName());
                    sendResponse(out, "200 OK", contentType, fileBytes);
                } else {
                    sendResponse(out, "404 Not Found", "text/plain", "404 - Not Found".getBytes());
                }
            }

            client.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void sendResponse(OutputStream out, String status, String contentType, byte[] content) throws IOException {
        PrintWriter writer = new PrintWriter(out, false);
        writer.println("HTTP/1.1 " + status);
        writer.println("Content-Type: " + contentType);
        writer.println("Content-Length: " + content.length);
        writer.println();
        writer.flush();
        out.write(content);
        out.flush();
    }

    private static String getContentType(String fileName) {
        if (fileName.endsWith(".html")) return "text/html";
        if (fileName.endsWith(".css")) return "text/css";
        if (fileName.endsWith(".js")) return "application/javascript";
        if (fileName.endsWith(".png")) return "image/png";
        if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) return "image/jpeg";
        if (fileName.endsWith(".gif")) return "image/gif";
        if (fileName.endsWith(".json")) return "application/json";
        return "application/octet-stream";
    }


}
