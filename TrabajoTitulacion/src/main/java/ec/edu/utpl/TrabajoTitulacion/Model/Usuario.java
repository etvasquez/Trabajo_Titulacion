package ec.edu.utpl.TrabajoTitulacion.Model;

public class Usuario {
    private String id;
    private String nombre;
    private String area;
    private String departamento;
    private String seccion;
    private String modalidad;
    private String tipo;
    private String status;
    private String nacionalidad;
    private String extension;
    private String telefono;
    private String mbox;

    public Usuario(){}

    public Usuario(String id, String nombre, String area, String departamento, String seccion, String modalidad, String tipo, String status, String nacionalidad, String extension, String telefono, String mbox) {
        this.id = id;
        this.nombre = nombre;
        this.area = area;
        this.departamento = departamento;
        this.seccion = seccion;
        this.modalidad = modalidad;
        this.tipo = tipo;
        this.status = status;
        this.nacionalidad = nacionalidad;
        this.extension = extension;
        this.telefono = telefono;
        this.mbox = mbox;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getExtensoion() {
        return extension;
    }

    public void setExtensoion(String extension) {
        this.extension = extension;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getMbox() {
        return mbox;
    }

    public void setMbox(String mbox) {
        this.mbox = mbox;
    }
}
