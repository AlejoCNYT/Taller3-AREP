package org.example;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import static java.lang.System.out;

public class ecispring
{

    public static void main(String[] args) {
        // Obtiene la clase del objeto String "Mundo"
        Class c = "Mundo".getClass();

        // Llama al método printMembers para imprimir los métodos de la clase String
        printMembers(c.getMethods(), "Methods");
    }

    /**
     * Método para imprimir los miembros (campos, constructores o métodos) de una clase.
     *
     * @param mbrs Arreglo de miembros (Field, Constructor o Method) que se desean imprimir.
     * @param s    Cadena que describe el tipo de miembros que se están imprimiendo (por ejemplo, "Methods").
     */
    public static void printMembers(Member[] mbrs, String s) {
        // Imprime el título que describe el tipo de miembros
        out.format("%s:%n", s);

        // Itera sobre cada miembro en el arreglo
        for (Member mbr : mbrs) {
            // Si el miembro es un campo (Field), imprime su representación genérica
            if (mbr instanceof Field)
                out.format(" %s%n", ((Field)mbr).toGenericString());

                // Si el miembro es un constructor, imprime su representación genérica
            else if (mbr instanceof Constructor)
                out.format(" %s%n", ((Constructor)mbr).toGenericString());

                // Si el miembro es un método, imprime su representación genérica
            else if (mbr instanceof Method)
                out.format(" %s%n", ((Method)mbr).toGenericString());
        }

        // Si no hay miembros en el arreglo, imprime un mensaje indicando que no hay miembros
        if (mbrs.length == 0) {
            out.format(" -- No %s --%n", s);
            out.format("%n");
        }
    }
}