/**
 * Nombre: Javier Valle 
 * Carnet: 20159
 * Fecha: 18/02/2023
 * Clase Errores.
 */

import java.util.*;

public class Errores {
    

    public int deteccion(String regex){
        // Recibiendo la expresión regular.

        System.out.println("La expresión regular es: " + regex);

        int error = 0; // Variable para verificar si hay errores en la expresión regular.
        
        // Verificando que no hayan paréntesis sin cerrar en la expresión regular.
        for (int i = 0; i < regex.length(); i++) {
            if (regex.charAt(i) == '(') {
                for (int j = i; j < regex.length(); j++) {
                    if (regex.charAt(j) == ')') {
                        System.out.println("La expresión regular es correcta.");
                        error = 1;
                        break;
                    } else if (j == regex.length() - 1) {
                        //System.out.println("Error: Paréntesis sin cerrar.");
                        error = 0;
                        break;
                    }
                }
            }
        }

        return error;
    }
}
