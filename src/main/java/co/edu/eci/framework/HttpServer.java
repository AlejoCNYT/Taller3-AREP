package co.edu.eci.framework;

import java.io.*;
import java.net.*;

public class HttpServer {
    private int port;
    private static Router router = new Router();

    public HttpServer(int port) {
        this.port = port;
    }

    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        System.out.println("Server started on port " + port);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            handleRequest(clientSocket);
        }
    }

    private static void handleRequest(Socket clientSocket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            String requestLine = in.readLine();
            if (requestLine != null) {
                System.out.println("[INFO] Request: " + requestLine);
                String[] parts = requestLine.split(" ");
                if (parts.length < 2) return;

                String method = parts[0];
                String path = parts[1];

                Request req = new Request(path);
                Response res = new Response();
                String responseBody = "";

                // Leer encabezados
                String line;
                int contentLength = 0;
                while (!(line = in.readLine()).isEmpty()) {
                    if (line.startsWith("Content-Length:")) {
                        contentLength = Integer.parseInt(line.split(":")[1].trim());
                    }
                }

                // Leer el cuerpo de la solicitud si es POST
                if ("POST".equals(method) && contentLength > 0) {
                    char[] buffer = new char[contentLength];
                    in.read(buffer);
                    req.setBody(new String(buffer));

                    responseBody = router.handlePostRequest(path, req, res);
                } else if ("GET".equals(method)) {
                    responseBody = router.handleGetRequest(path, req, res);
                } else {
                    res.setStatus(400);
                    responseBody = "{\"status\": 400, \"error\": \"Unsupported HTTP method\"}";
                }

                // Enviar respuesta
                res.setBody(responseBody);
                out.print(res.formatResponse());
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public Router getRouter() {
        return router;
    }
}
