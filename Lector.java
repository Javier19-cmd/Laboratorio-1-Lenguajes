import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Lector {
    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);
        regex postfix = new regex(); // Instanciando la clase regex para pasar a postfix la expresión regular.
        Thompson thompson = new Thompson(); // Instanciando la clase Thompson para crear el AFN.

        String r = "";

        System.out.println("Introduzca la expresión regular: ");
        r = teclado.nextLine(); // lee la expresión regular
        String post_value = postfix.evaluar(r); // pasar a postfix la expresión regular
        System.out.println("Valor postfix: " + post_value);

        thompson.post(post_value); // Mando a evaluar la expresión regular.

        thompson.escribirArchivo(); // Escribir el archivo de salida.

        // Haciendo el getter de la lista de transiciones.
        List<Transiciones> tr = thompson.getTransiciones();

        // System.out.println("Transiciones: " + tr);

        Estado inicial = thompson.getEstadoInicial(); // Obteniendo el estado inicial.

        // System.out.println("Estado inicial: " + inicial);

        // Haciendo el getter de la lista de estados iniciales.
        List<Estado> estadosIniciales = thompson.getEstados_iniciales();

        // Haciendo el getter de la lista de estados finales.
        List<Estado> estadosFinales = thompson.getEstados_aceptacion();

        // System.out.println("Estados inicial: " + estadosIniciales);

        // System.out.println("Estados finales: " + estadosFinales);

        // Haciendo getter de los símbolos del alfabeto.
        ArrayList<String> alfabeto = thompson.getAlfabeto();

        // Haciendo getter de los estados.
        Stack<Estado> estados = thompson.getEstadoss();

        // Haciendo el getter del estado de aceptación.
        Estado aceptacion = thompson.getEstadoAceptacion();


        // Simulando el AFN construído.
        //thompson.simulation(aceptacion);

    }
}