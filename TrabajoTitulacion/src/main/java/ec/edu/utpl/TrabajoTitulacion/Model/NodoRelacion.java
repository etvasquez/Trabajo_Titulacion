package ec.edu.utpl.TrabajoTitulacion.Model;

import java.util.ArrayList;

public class NodoRelacion {
    private ArrayList<Nodo> nodes = new ArrayList<>();
    private ArrayList<Relacion> likes = new ArrayList<>();

    public ArrayList<Nodo> getNodes() {
        return nodes;
    }

    public void setNodes(ArrayList<Nodo> nodes) {
        this.nodes = nodes;
    }

    public ArrayList<Relacion> getLikes() {
        return likes;
    }

    public void setLikes(ArrayList<Relacion> likes) {
        this.likes = likes;
    }
}
