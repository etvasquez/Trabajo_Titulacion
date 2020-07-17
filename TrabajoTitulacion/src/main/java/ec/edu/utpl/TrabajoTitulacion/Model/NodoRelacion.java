package ec.edu.utpl.TrabajoTitulacion.Model;

import java.util.ArrayList;

public class NodoRelacion {
    private ArrayList<Nodo> nodes = new ArrayList<>();
    private ArrayList<Relacion> edges = new ArrayList<>();

    public NodoRelacion(ArrayList<Nodo> nodes, ArrayList<Relacion> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public ArrayList<Nodo> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Nodo> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Relacion> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Relacion> edges) {
        this.edges = edges;
    }
}
