package ec.edu.utpl.TrabajoTitulacion.Model;

public class ListaProyecto {
    private Nodo nodo1;
    private Nodo nodo2;
    private Nodo nodo3;

    public ListaProyecto() {
    }

    public ListaProyecto(Nodo nodo1, Nodo nodo2, Nodo nodo3) {
        this.nodo1 = nodo1;
        this.nodo2 = nodo2;
        this.nodo3 = nodo3;
    }

    public Nodo getNodo1() {
        return nodo1;
    }

    public void setNodo1(Nodo nodo1) {
        this.nodo1 = nodo1;
    }

    public Nodo getNodo2() {
        return nodo2;
    }

    public void setNodo2(Nodo nodo2) {
        this.nodo2 = nodo2;
    }

    public Nodo getNodo3() {
        return nodo3;
    }

    public void setNodo3(Nodo nodo3) {
        this.nodo3 = nodo3;
    }
}
