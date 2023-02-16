import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class Thompson {

    // Definiendo el símbolo para las transiciones.
    public String simbolo = "&";

    // Definiendo la variable que va a llevar la contabilidad de los estados.
    public static int estados = 0;

    // Definiendo una lista para las transiciones que se hicieron en el AFN.
    public static List<Transiciones> transiciones = new CopyOnWriteArrayList<Transiciones>();

    // Definiendo Stack para la expresión postfix invertida.
    private Stack<String> expresion_postfix = new Stack<String>();

    // Defininendo una pila para el alfabeto.
    private ArrayList<String> alfabeto = new ArrayList<String>();

    // Definiendo una pila para las operaciones de la expresión.
    private Stack<String> operaciones = new Stack<String>();

    // Definiendo un stack para el estado inicial final.
    private Stack<Estado> estados_iniciales = new Stack<Estado>();

    // Definiendo el stack para los estados de aceptación.
    private Stack<Estado> estados_aceptacion = new Stack<Estado>();

    // Definiendo otro stack para tener una copia de los estados de aceptación.
    private Stack<Estado> estados_aceptacion2 = new Stack<Estado>();

    // Definiendo una pila para los estados de la expresión.
    private Stack<Estado> estadoss = new Stack<Estado>();

    private String operacion = "";

    // Variable para sacar primer elemento a operar.
    private String elemento1 = "";

    private String recorrido = ""; // Variable para recorrer la expresión regular.

    // Definiendo un Arraylist temporal para la expresión invertida.
    ArrayList<String> temporal = new ArrayList<String>();

    private String expresion_postfixs = ""; // Variable para la expresión regular.

    public void post(String expresion_postfixs) {

        // Guardando la expresión postfix en una variable de la clase.
        this.expresion_postfixs = expresion_postfixs;

        // System.out.println("Expreión en variable local: " + expresion_postfixs);

        // Arreglo para determinar la precedencia de operaciones.
        String[][] precedencia = { { "*", "+", ".", "|" }, { "100", "80", "60", "40" } };

        // Definiendo los estados de las transiciones. Esto es una clase aparte.
        // ArrayList<Transiciones> transiciones = new ArrayList<Transiciones>();

        // Invirtiendo la expresión regular.
        StringBuilder strb = new StringBuilder(expresion_postfixs);

        expresion_postfixs = strb.reverse().toString(); // Invierto la expresión regular.

        System.out.println("Expresión regular invertida: " + expresion_postfixs);

        // System.out.println("Expresión regular invertida: " + postfix);

        // Imprimiendo la matriz de precedencia.
        // for (int i = 0; i < precedencia.length; i++) {
        // for (int j = 0; j < precedencia[i].length; j++) {
        // System.out.print(precedencia[i][j] + " ");
        // }
        // System.out.println();
        // }

        // Insertando la expresión postfix invertida al Stack.
        for (int x = 0; x < expresion_postfixs.length(); x++) {
            expresion_postfix.push(String.valueOf(expresion_postfixs.charAt(x))); // Insertando la expresión postfix
                                                                                  // invertida al
            // Stack.
        }

        // System.out.println("Expresión regular postfix invertida en el Stack: " +
        // expresion_postfix);

        // Insertando la expresión postfix invertida al ArrayList temporal.
        for (int x = 0; x < expresion_postfixs.length(); x++) {
            temporal.add(String.valueOf(expresion_postfixs.charAt(x))); // Insertando la expresión postfix invertida al
                                                                        // ArrayList
            // temporal.
        }

        // System.out.println("Expresión regular postfix en el ArrayList temporal: " +
        // temporal.toString());

        // Identificando el alfabeto de la expresión regular.
        for (int i = 0; i < expresion_postfix.size(); i++) {
            if (expresion_postfix.get(i).equals("*") || expresion_postfix.get(i).equals(".")
                    || expresion_postfix.get(i).equals("|")) {
                continue;
            } else {
                // Insertando una vez cada caracter del alfabeto.
                if (!alfabeto.contains(expresion_postfix.get(i))) {
                    alfabeto.add(expresion_postfix.get(i));
                }
            }
        }
        // Agregando sort al alfabeto.
        Collections.sort(alfabeto);

        // System.out.println("Alfabeto: " + alfabeto);

        // Recorriendo el Stack para identificar las operaciones.

        while (!expresion_postfix.isEmpty()) {

            // System.out.println("Valor de la expresión postfix invertida: " +
            // expresion_postfix.get(i)).

            // Recorriendo el ArrayList temporal hacia atrás.
            for (int i = temporal.size() - 1; i >= 0; i--) {
                // System.out.println("Valor de la expresión postfix invertida: " +

                recorrido = temporal.get(i);

                switch (recorrido) {
                    case "*": // Operación para el Kleene.

                        // System.out.println("Operación CERRADURA DE KLEENE.");
                        operacion = expresion_postfix.pop(); // Sacando la operación del stack.
                        operaciones.push(operacion);
                        // Se optiene el estado inicial y de aceptación de la expresión que se tenga
                        // armada.
                        Estado estado1 = estados_iniciales.pop();
                        Estado estado2 = estados_aceptacion.pop();

                        // Se manda a hacer la expresión en su método.
                        cerraduraKleene(estado1, estado2);
                        break;

                    case "+": // Operación para la cerradura positiva.

                        // System.out.println("Operación CERRADURA POSITIVA.");
                        operacion = expresion_postfix.pop(); // Sacando la operación del stack.
                        operaciones.push(operacion);

                        Estado inic = estados_iniciales.pop(); // Estado inicial del autómata.
                        Estado fina = estados_aceptacion.pop(); // Estado final del autómata.

                        // System.out.println("Estado inicial del autómata: " + inic);
                        // System.out.println("Estado final del autómata: " + fina);

                        cerraduraPositiva(inic, fina);
                        break;

                    case "?": // Operación para cero o una instancia de.

                        // System.out.println("Operación CERO O UNA INSTANCIA DE.");
                        operacion = expresion_postfix.pop(); // Sacando la operación del stack.
                        operaciones.push(operacion);

                        Estado inicia = estados_iniciales.pop(); // Estado inicial del autómata.
                        Estado finali = estados_aceptacion.pop(); // Estado final del autómata.

                        // System.out.println("Estado inicial del autómata: " + inicia);
                        // System.out.println("Estado final del autómata: " + finali);

                        ceroUnaInstancia(inicia, finali);

                        break;

                    case ".": // Operación para la cerradura positiva.

                        // System.out.println("Operación CONCATENACIÓN.");
                        operacion = expresion_postfix.pop(); // Sacando la operación del stack.
                        operaciones.push(operacion);

                        Estado final_2 = estados_aceptacion.pop(); // Estado final del autómata derecho.
                        Estado fin = estados_aceptacion.pop(); // Estado final del autómata izquierdo.
                        Estado inicioo = estados_iniciales.pop(); // Estado inicial del autómata derecho.

                        // System.out.println("Estado final del autómata izquierdo: " + fin);
                        // System.out.println("Estado inicial del autómata derecho: " + inicioo);
                        // System.out.println("Estado final del autómata derecho: " + final_2);
                        // System.out.println();

                        concatenacion(fin, inicioo, final_2);
                        break;

                    case "|": // Operación para el OR.

                        // System.out.println("Operación UNIÓN.");
                        operacion = expresion_postfix.pop(); // Sacando la operación del stack.
                        operaciones.push(operacion); // Guardando la operación en el stack de operaciones.

                        // Se agarra el estado inicial y de aceptación de la expresión de arriba del
                        // barco.
                        Estado inicio_arriba = estados_iniciales.pop();
                        Estado aceptacion_arriba = estados_aceptacion.pop();

                        // Se agarra el estado inicial y de aceptación de la expresión de abajo del
                        // barco.
                        Estado inicio_abajo = estados_iniciales.pop();
                        Estado aceptacion_abajo = estados_aceptacion.pop();

                        // Se manda a hacer la expresión en su método.
                        union(inicio_arriba, aceptacion_arriba, inicio_abajo, aceptacion_abajo);
                        break;

                    default: // Operación unitaria.

                        // System.out.println("Operando: " + recorrido);
                        elemento1 = expresion_postfix.pop();
                        defaultop(elemento1);
                        break;
                }
            }

        }

        // // Imprimiendo las transiciones.
        // for (int i = 0; i < transiciones.size(); i++) {
        // System.out.println(transiciones.get(i).toString());
        // }

        setEstadoInicial_y_Final();

    }

    // Método para hacer la operación default, que es crear un estado para un
    // caracter en específico.

    public void defaultop(String elemento) {
        // Teniendo el elemento, se crea un estado inicial y uno final.
        Transiciones transicion = new Transiciones(elemento);

        // Agregando la o las transiciones que se hayan hecho con el elemento.
        transiciones.add(transicion);

        // Convirtiendo a estado el inicio y el fin de la transición.
        Estado inicio = transicion.getDe();
        Estado fin = transicion.getA();

        // Agregando los estados al sus Stacks correspondientes.
        estados_iniciales.push(inicio);
        estados_aceptacion.push(fin);
        estados_aceptacion2.push(fin); // Guardando el estado de aceptación en un Stack diferente.
    }

    // Se reciben los estado inicial y de aceptación, para así ya armar el
    // automata. (O sea el barco).
    public void cerraduraKleene(Estado elemento1, Estado elemento2) {
        // Creando nuevos estados de entrada y salida para el autómata.
        Estado nuevo_inicio = new Estado(estados); // Se pasa como parámetro el estado para que no se repitan los
                                                   // estados anteriores.
        Estado nuevo_fin = new Estado(estados); // Se pasa como parámetro el estado para que no se repitan los estados
                                                // siguientes.

        // Creando transiciones con los nuevos estados y con nuestro kleene.
        Transiciones transicion1 = new Transiciones(elemento2, elemento1, simbolo); // Transición del estado de
                                                                                    // aceptación al
                                                                                    // estado inicial del automata que
                                                                                    // se
                                                                                    // tenga kleenado.
        Transiciones transicion2 = new Transiciones(nuevo_inicio, nuevo_fin, simbolo); // Transición del nuevo estado
                                                                                       // inicial al nuevo estado de
                                                                                       // aceptación.
        Transiciones transicion3 = new Transiciones(nuevo_inicio, elemento1, simbolo); // Transción desde el nuevo
                                                                                       // estado de inicio
                                                                                       // al estado inicial del automata
                                                                                       // kleenado.
        Transiciones tranisicion4 = new Transiciones(elemento2, nuevo_fin, simbolo);// Transición desde el estado de
                                                                                    // aceptación del automata
                                                                                    // kleenado al estado inicial del
                                                                                    // automata kleenado.
        // Guardando las transiciones que se van haciendo.
        transiciones.add(transicion1);
        transiciones.add(transicion2);
        transiciones.add(transicion3);
        transiciones.add(tranisicion4);

        // Agregando los nuevos estados al Stack de estados iniciales y de aceptación.
        estados_iniciales.push(nuevo_inicio);
        estados_aceptacion.push(nuevo_fin);
        estados_aceptacion2.push(nuevo_fin); // Guardando el estado de aceptación en un Stack diferente.

    }

    private void cerraduraPositiva(Estado uno, Estado dos) { // Método para hacer la cerradura positiva.
        // Creando dos estados nuevos. Uno inicial y otro final.
        Estado nuevo_inicio = new Estado(estados);
        Estado nuevo_fin = new Estado(estados);

        // Creando las nuevas transiciones para el automata.
        // Nuevo estado inicial. Se conecta el nuevo estado inicial con el estado
        // inicial viejo.
        Transiciones transicion1 = new Transiciones(nuevo_inicio, uno, simbolo);

        // Nuevo estado final. Se conecta el viejo estado de acetpación con el nuevo
        // estado de aceptación.
        Transiciones transicion2 = new Transiciones(dos, nuevo_fin, simbolo);

        // Transición del nuevo estado inicial al nuevo estado de aceptación del
        // autómata.
        Transiciones transicion3 = new Transiciones(nuevo_inicio, nuevo_fin, simbolo);

        // Agregando las nuevas transiciones.
        transiciones.add(transicion1);
        transiciones.add(transicion2);
        transiciones.add(transicion3);

        // Agregando los nuevos estados al Stack de estados iniciales y de aceptación.
        estados_iniciales.push(nuevo_inicio);
        estados_aceptacion.push(nuevo_fin);
        estados_aceptacion2.push(nuevo_fin); // Guardando el estado de aceptación en un Stack diferente.
    }

    private void ceroUnaInstancia(Estado uno, Estado dos) { // Método para hcer el método de cero o una instancia.

        // Creando dos estados nuevos. Uno inicial y otro final.
        Estado nuevo_inicio = new Estado(estados);
        Estado nuevo_fin = new Estado(estados);

        // Creando las nuevas transiciones para el automata.
        // Nuevo estado inicial. Se conecta el nuevo estado inicial con el estado
        // inicial viejo.
        Transiciones transicion1 = new Transiciones(nuevo_inicio, uno, simbolo);

        // Nuevo estado final. Se conecta el viejo estado de acetpación con el nuevo
        // estado de aceptación.
        Transiciones transicion2 = new Transiciones(dos, nuevo_fin, simbolo);

        // Transición del nuevo estado inicial al nuevo estado de aceptación del
        // autómata.
        Transiciones transicion3 = new Transiciones(nuevo_inicio, nuevo_fin, simbolo);

        // Agregando las nuevas transiciones.
        transiciones.add(transicion1);
        transiciones.add(transicion2);
        transiciones.add(transicion3);

        // Agregando los nuevos estados al Stack de estados iniciales y de aceptación.
        estados_iniciales.push(nuevo_inicio);
        estados_aceptacion.push(nuevo_fin);
        estados_aceptacion2.push(nuevo_fin); // Guardando el estado de aceptación en un Stack diferente.

    }

    // Método para hacer la operación de unión.
    private void union(Estado inicio_arriba, Estado aceptacion_arriba, Estado inicio_abajo, Estado aceptacion_abajo) {
        // Se crea un nuevo estado inicial y uno de aceptación.
        Estado nuevo_inicio = new Estado(estados);
        Estado nuevo_fin = new Estado(estados);

        // Creando las nuevas transiciones para el automata.

        // Transiciones para la parte de arriba.
        Transiciones transicion1_arriba = new Transiciones(nuevo_inicio, inicio_arriba, simbolo);
        Transiciones transicion2_arriba = new Transiciones(aceptacion_arriba, nuevo_fin, simbolo);

        // Transiciones para la parte de abajo.
        Transiciones transicion1_abajo = new Transiciones(nuevo_inicio, inicio_abajo, simbolo);
        Transiciones transicion2_abajo = new Transiciones(aceptacion_abajo, nuevo_fin, simbolo);

        // Agregando las transiciones a la lista de transiciones.
        // Transiciones de arriba.
        transiciones.add(transicion1_arriba);
        transiciones.add(transicion2_arriba);

        // Transiciones de abajo.
        transiciones.add(transicion1_abajo);
        transiciones.add(transicion2_abajo);

        // Agregando los nuevos estados al Stack de estados iniciales y de aceptación.
        estados_iniciales.push(nuevo_inicio);
        estados_aceptacion.push(nuevo_fin);
        estados_aceptacion2.push(nuevo_fin); // Guardando el estado de aceptación en un Stack diferente.
    }

    // Método para hacer la operación de concatenación.
    private void concatenacion(Estado elemento1, Estado elemento2, Estado final2) {

        // El elemento 1 es el estado final del primer automata.

        // El elemento 2 es el estado inicial del segundo automata.

        // El final2 es el estado de "aceptación" temporal del segundo autómata.

        // // Imprimiendo el estado de aceptación del primer automata.
        // System.out.println("Estado de aceptación del primer automata: " + elemento1);

        // // Imprimeiendo el estado inicial del segundo automata.
        // System.out.println("Estado inicial del segundo automata: " + elemento2);

        // Obteniendo la transición del estado inicial del segundo automata.
        for (Transiciones transicion : transiciones) {

            if (transicion.getDe().equals(elemento2)) { // Si el estado de la transición es igual al estado inicial del
                                                        // segundo automata.
                // System.out.println("Transición del estado inicial del segundo: " +
                // transicion.vis());

                // Obteniendo el simbolo de la transición.
                String s = transicion.getSimbolo();

                System.out.println(s);

                // Eliminando la transición del segundo autómata.
                transiciones.remove(transicion);

                // Creando una transición a los siguientes estados del segundo automata.
                Transiciones tr = new Transiciones(elemento1, transicion.getA(), s);

                // Agregando la nueva transición a la lista de transiciones.
                transiciones.add(tr);
            }
        }

        // Seteando el nuevo estado de aceptación.
        estados_aceptacion.push(final2);
    }

    public void guardandoEstados() { // Método para guardar los estados del autómata.

        for (int i = 0; i < transiciones.size(); i++) {
            if (!estadoss.contains(transiciones.get(i).getDe())) {
                estadoss.push(transiciones.get(i).getDe());

                // System.out.println("Estado: " + transiciones.get(i).getDe());
            }
            if (!estadoss.contains(transiciones.get(i).getA())) {
                estadoss.push(transiciones.get(i).getA());

                // System.out.println("Estado: " + transiciones.get(i).getDe());
            }
        }
    }

    // Haciendo un getter para el estado inicial.
    public Estado getEstadoInicial() {
        return estados_iniciales.peek();
    }

    // Haciendo un método para settear formalmente el estado inicial y final.
    public void setEstadoInicial_y_Final() {
        // Seteando el estado inicial.
        Estado inicial = estados_iniciales.pop();
        inicial.setInicio(true);
        // Pusheado el estado inicial al Stack de estados.
        estados_iniciales.push(inicial);

        // Seteando el estado final.
        Estado fin = estados_aceptacion.pop();
        fin.setFinal(true);

        // Pusheado el estado final al Stack de estados.
        estados_aceptacion.push(fin);

        if (alfabeto.contains("&")) { // Si el alfabeto contiene el simbolo de lambda.
            // Eliminando el simbolo de lambda del alfabeto.
            alfabeto.remove("&");
        }
    }

    // Haciendo un getter de la lista de transiciones.
    public List<Transiciones> getTransiciones() {

        return transiciones;
    }

    // Método para obtener el estado de aceptación.
    public Estado getEstadoAceptacion() {

        return estados_aceptacion.pop();
    }

    // Haciendo un getter para el Stack de estados iniciales.
    public Stack<Estado> getEstados_iniciales() {
        return estados_iniciales;
    }

    // Haciendo un getter para el Stack de estados de aceptación.
    public Stack<Estado> getEstados_aceptacion() {
        return estados_aceptacion2;
    }

    // Haciendo un getter para los símbolos.
    public ArrayList<String> getAlfabeto() {
        return alfabeto;
    }

    // Haciendo un getter para el Stack de estados.
    public Stack<Estado> getEstadoss() {
        return estadoss;
    }

    // Método para escribir un archivo de texto con el autómata.
    public void escribirArchivo() {

        try {
            // Creando el archivo con codificado UTF-8.
            File archivo = new File("Automata.txt");

            // Creando el archivo si no existe.
            if (!archivo.exists()) {
                archivo.createNewFile();
            }

            // Creando el escritor.
            FileWriter escritor = new FileWriter(archivo);

            BufferedWriter bw = new BufferedWriter(escritor);

            // System.out.println("Expresión regular en el método escribirArchivo: " +
            // expresion_postfixs + "");

            // escritor.write("Expresión regular: " + expresion_postfixs);

            // Escribiendo los estados iniciales.
            bw.write("Estado inicial: " + estados_iniciales + "");
            bw.newLine();

            // Escribiendo el estado de aceptación oficial.
            bw.write("Estado de aceptación oficial: " + estados_aceptacion + "");

            bw.newLine();

            // Escribiendo el estado de aceptación temporal.
            bw.write("Estado de aceptación temporal: " + estados_aceptacion2 + "");
            bw.newLine();

            guardandoEstados(); // Guardando los estados del AFN.

            // Escribiendo los estados computados.
            bw.write("Estados: " + estadoss + "");

            bw.newLine();

            // Escribiendo las transiciones.
            bw.write("Transiciones: " + transiciones.toString() + "");

            bw.newLine();

            // Escribiendo los símbolos.
            bw.write("Símbolos: " + alfabeto + "");
            bw.newLine();

            // // Escribiendo la expresión regular.
            // bw.write("Expresión regular: " + expresion_postfixs + "");
            // bw.newLine();

            // Cerrando el escritor.
            bw.close();

            // Escribiendo en el archivo.
        } catch (Exception e) {
            System.out.println("Error al escribir el archivo.");
        }
    }

    // Método para poder simular el autómata cuando ya esté armado.
    public void simulation(Estado aceptacion) {
        System.out.println("Ingrese la cadena a evaluar: ");

    }
}

