import java.lang.reflect.Array;
import java.util.*;

public class Transiciones {

    // Variables globales de la clase Transiciones.
    Estado de, a; // de es el estado de inicio y a es el estado de fin.
    String simbolo; // simbolo de la transicion.

    public Transiciones(String c) {
        this.simbolo = c;
        this.de = new Estado(Thompson.estados);
        this.a = new Estado(Thompson.estados);

        // Enlazando los estados para armar el AFN.
        this.de.agregarEstadoSiguiente(a);
        this.a.agregarEstadoAnterior(de);

    } // Constructor de la clase Transicion. Esto servirá para almacenar las
      // transiciones de los estados.

    // Conctanteación de transiciones del AFN.
    public Transiciones(Estado de, Estado a, String simbolo) {
        this.de = de;
        this.a = a;
        this.simbolo = simbolo;

        // Enlazando los estados para armar el AFN.
        this.de.agregarEstadoSiguiente(a);
        this.a.agregarEstadoAnterior(de);

    }

    public Transiciones() {

    }

    public Estado getDe() { // Método para obtener el estado de inicio.
        return this.de;
    }

    public Estado getA() { // Método para obtener el estado final.
        return this.a;
    }

    public Estado setDe(Estado estado) {
        this.de = estado;
        return this.de;
    }

    // Método para hacer set.
    public Estado setA(Estado estado) {
        this.a = estado;
        return this.a;
    }

    public String getSimbolo() { // Método para obtener el símbolo de la transición.

        return this.simbolo;

    }

    public String getDeS() {
        return this.de.toString();
    }

    public String getAS() {
        return this.a.toString();
    }

    public void replaceA(Estado viejoa, Estado a) {

        String[] estados;

        // Buscando el estado viejo en la lista de estados siguientes del estado
        // siguiente.
        // for (int i = 0; i < viejoa.getEstadoSiguiente().size(); i++) {
        // if (viejoa.getEstadoSiguiente().get(i).equals(viejoa)) {
        // // System.out.println(viejoa.getEstadoSiguiente().get(i));
        // // Agregando el estado nuevo a la lista de estados siguientes del estado.
        // viejoa.getEstadoSiguiente().remove(i);
        // viejoa.getEstadoSiguiente().add(i, a); // Reemplazando el estado viejo por el
        // nuevo.
        // }
        // }

        System.out.println("El estado va hacia: " + a.getEstadoSiguiente().get(0));
        System.out.println("El estado viene de: " + viejoa.getEstadoAnterior().get(0));
    }

    public void replaceDe(Estado viejode, Estado de) {
        // Buscando el estado viejo en la lista de estados anteriores del estado de.
        for (int i = 0; i < viejode.getEstadoAnterior().size(); i++) {
            if (viejode.getEstadoAnterior().get(i).equals(viejode)) {
                System.out.println(viejode.getEstadoAnterior().get(i));
                viejode.getEstadoAnterior().remove(i);
                viejode.getEstadoAnterior().add(i, de);
            }
        }

        System.out.println(de.getEstadoAnterior().get(0));
    }

    public void replaceElement(Estado de, Estado a, String simbolo) {
        this.de = de;
        this.a = a;
        this.simbolo = simbolo;
    }

    // Método para obtener el símbolo en una transición.
    public String getSimboloTransicion() {
        return this.simbolo;
    }

    // Método para obtener la transición anterior.
    public Transiciones getTransicionAnterior() {
        return this;
    }

    // Método para obtener la transición siguiente.
    public Transiciones getTransicionSiguiente() {
        return this;
    }

    public String toString() { // Método para poder ver las transiciones.
        return this.de.toString() + " -- " + this.simbolo + " --> " + this.a.toString();
    }

    // Método para obtener las transiciones de un estado.
    public ArrayList<Transiciones> getTransicionesEstado(Estado estado) {
        ArrayList<Transiciones> transiciones = new ArrayList<Transiciones>();
        for (int i = 0; i < Thompson.transiciones.size(); i++) {
            if (Thompson.transiciones.get(i).getDe().equals(estado)) {
                transiciones.add(Thompson.transiciones.get(i));
            }
        }

        return transiciones;
    }

    // Método para obtener el estado final.
    public Estado getEstadoFinal() {
        return this.a;
    }

}
