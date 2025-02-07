package org.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

public class InvokePrintMembers {
    public static void main(String... args) {
        try {
            // Cargar la clase especificada en el primer argumento (args[0])
            Class<?> c = Class.forName(args[0]);

            // Definir los tipos de argumentos que espera el método "printMembers":
            // - Un array de objetos que implementan la interfaz Member (Member[].class)
            // - Un objeto de tipo String (obtenido mediante "Pedro".getClass())
            Class[] argTypes = new Class[]{Member[].class, "Pedro".getClass()};

            // Obtener el método "printMembers" de la clase cargada, con los tipos de argumentos especificados
            Method main = c.getDeclaredMethod("printMembers", argTypes);

            // Obtener la clase Integer para usarla como ejemplo
            Class otraClase = Integer.class;

            // Obtener todos los métodos públicos de la clase Integer (que implementan la interfaz Member)
            Member[] members = otraClase.getMethods();

            // Imprimir un mensaje indicando que se está invocando el método "main" de la clase Integer
            System.out.format("invoking %s.main()%n", otraClase.getName());

            // Invocar el método "printMembers" de la clase cargada dinámicamente:
            // - El primer argumento es null porque se asume que "printMembers" es un método estático
            // - Los argumentos son el array de métodos (members) y un String ("Methods")
            main.invoke(null, members, "Methods");

            // En un entorno de producción, estas excepciones deberían manejarse de manera más elegante
        } catch (ClassNotFoundException x) {
            // Manejar la excepción si la clase no se encuentra
            x.printStackTrace();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException x) {
            // Manejar excepciones relacionadas con la invocación del método:
            // - NoSuchMethodException: Si el método "printMembers" no existe
            // - IllegalAccessException: Si no se tiene acceso al método
            // - InvocationTargetException: Si el método lanza una excepción durante su ejecución
            x.printStackTrace();
        }
    }
}
