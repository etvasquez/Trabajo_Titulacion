package ec.edu.utpl.TrabajoTitulacion.Model;

public class Comentario {
    private String idCom;
    private String nombre;
    private String fecha;
    private String comentarios;
    private String correo;

    public Comentario() {
    }

    public Comentario(String idCom, String nombre,String fecha, String comentarios, String correo) {
        this.idCom = idCom;
        this.nombre = nombre;
        this.fecha = fecha;
        this.comentarios = comentarios;
        this.correo = correo;
    }

    public String getIdCom() {
        return idCom;
    }

    public void setIdCom(String idCom) {
        this.idCom = idCom;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}
