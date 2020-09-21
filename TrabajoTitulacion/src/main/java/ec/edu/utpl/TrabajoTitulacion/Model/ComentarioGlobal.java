package ec.edu.utpl.TrabajoTitulacion.Model;

import java.util.ArrayList;

public class ComentarioGlobal {
    private Comentario comentario;
    private ArrayList<Comentario> listaComentario;

    public ComentarioGlobal() {
    }

    public ComentarioGlobal(Comentario comentario, ArrayList<Comentario> listaComentario) {
        this.comentario = comentario;
        this.listaComentario = listaComentario;
    }

    public Comentario getComentario() {
        return comentario;
    }

    public void setComentario(Comentario comentario) {
        this.comentario = comentario;
    }

    public ArrayList<Comentario> getListaComentario() {
        return listaComentario;
    }

    public void setListaComentario(ArrayList<Comentario> listaComentario) {
        this.listaComentario = listaComentario;
    }
}
