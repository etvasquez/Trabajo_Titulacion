package ec.edu.utpl.TrabajoTitulacion.Models;

public class Person {
    private String identificador;
    private String nombres;
    private String apellidos;
    private String email;
    private String direccion;
    private String extension;
    private String genero;
    private String miembro;
    private String telefono;
    private String estado;
    private String area;
    private String aocupacion;
    private String seccion;
    private String nacionalidad;
    private String modalidad;
    private String departamento;

    public Person() {
    }

    public Person(String identificador, String nombres, String apellidos, String email, String direccion, String extension, String genero, String miembro, String telefono, String estado, String area, String aocupacion, String seccion, String nacionalidad, String modalidad, String departamento) {
        this.identificador = identificador;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.email = email;
        this.direccion = direccion;
        this.extension = extension;
        this.genero = genero;
        this.miembro = miembro;
        this.telefono = telefono;
        this.estado = estado;
        this.area = area;
        this.aocupacion = aocupacion;
        this.seccion = seccion;
        this.nacionalidad = nacionalidad;
        this.modalidad = modalidad;
        this.departamento = departamento;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getMiembro() {
        return miembro;
    }

    public void setMiembro(String miembro) {
        this.miembro = miembro;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAocupacion() {
        return aocupacion;
    }

    public void setAocupacion(String aocupacion) {
        this.aocupacion = aocupacion;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public void setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
    }

    public String getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
}
