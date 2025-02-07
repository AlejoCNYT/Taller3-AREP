package org.example;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

public class InvokeMain {
    public static void main(String... args) {
        try {
            // Cargar la clase especificada en el primer argumento
            Class<?> c = Class.forName(args[0]);

            // Definir el tipo de argumento que espera el método main (un array de Strings)
            Class[] argTypes = new Class[]{String[].class};

            // Obtener el método main de la clase
            Method main = c.getDeclaredMethod("main", argTypes);

            // Copiar los argumentos restantes para pasarlos al método main
            String[] mainArgs = Arrays.copyOfRange(args, 1, args.length);

            // Invocar el método main de la clase especificada
            System.out.format("invoking %s.main()%n", c.getName());
            main.invoke(null, (Object) mainArgs);

        } catch (ClassNotFoundException x) {
            // Manejar la excepción si la clase no se encuentra
            x.printStackTrace();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException x) {
            // Manejar otras excepciones relacionadas con la invocación del método
            x.printStackTrace();
        }
    }
}
