package ec.edu.utpl.TrabajoTitulacion.Model;

import java.util.HashSet;
import java.util.Set;

public class Person {
    private String identificador;
    private String nombres;
    /*
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
*/

    public Person(String identificador, String nombres) {
        this.identificador = identificador;
        this.nombres = nombres;
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

}
