package ec.edu.utpl.TrabajoTitulacion.Model;

public class Colaboradores {

    private String idpersona;
    private String nombre;
    private String correo;
    private String area;
    private String relaciones;
    private String foto;

    public Colaboradores(){

    }

    public Colaboradores(String idpersona, String nombre, String correo, String area, String relaciones,String foto) {
        this.idpersona = idpersona;
        this.nombre = nombre;
        this.correo = correo;
        this.area = area;
        this.relaciones = relaciones;
        this.foto = foto;
    }

    public String getIdpersona() {
        return idpersona;
    }

    public void setIdpersona(String idpersona) {
        this.idpersona = idpersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getRelaciones() {
        return relaciones;
    }

    public void setRelaciones(String relaciones) {
        this.relaciones = relaciones;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }
}
