package ec.edu.utpl.TrabajoTitulacion.Model;

public class Persona {
    private String identificacion;
    private String nombre;
    private String apellido;
    private String rol;
    private String area;

    public Persona() {
    }

    public Persona(String identificacion, String nombre, String apellido, String rol, String area) {
        this.identificacion = identificacion;
        this.nombre = nombre;
        this.apellido = apellido;
        this.rol = rol;
        this.area = area;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
}
