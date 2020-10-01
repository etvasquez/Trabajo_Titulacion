package ec.edu.utpl.TrabajoTitulacion.Model;

public class Recurso {

    private String nombrecompleto;
    private String nombrereal;
    private String descripcion;
    private String tipo;

    public Recurso() {
    }

    public Recurso(String nombrecompleto, String nombrereal, String descripcion, String tipo) {
        this.nombrecompleto = nombrecompleto;
        this.nombrereal = nombrereal;
        this.descripcion = descripcion;
        this.tipo = tipo;
    }

    public String getNombrecompleto() {
        return nombrecompleto;
    }

    public void setNombrecompleto(String nombrecompleto) {
        this.nombrecompleto = nombrecompleto;
    }

    public String getNombrereal() {
        return nombrereal;
    }

    public void setNombrereal(String nombrereal) {
        this.nombrereal = nombrereal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
