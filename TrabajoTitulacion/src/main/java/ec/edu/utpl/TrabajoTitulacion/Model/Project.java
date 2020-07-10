package ec.edu.utpl.TrabajoTitulacion.Model;

public class Project {
    private String identificador;
    private String titulo;
    /*
    private String rol_proyecto;
    private String fecha_fin_participacion;
    private String fecha_inicio_participacion;
    private String area_conocimiento;
    private String area_conocimiento_especifica;
    private String avance;
    private String cobertura;
    private String codigo_proyecto;
    private String descripcion;
    private String estado_validacion;
    private String fecha_inicio;
    private String fecha_fin;
    private String fondo_externo;
    private String fondo_utpl;
    private String incluye_estudiantes;
    private String incluye_financiamiento_externo;
    private String linea_investigacion;
    private String moneda;
    private String objetivos;
    private String presupuesto;
    private String programa;
    private String reprogramado;
    private String smarland;
    private String sub_area_conocimiento;
    private String tipo_convocatoria;
    private String total_general;
    private String tipo_proyecto;
    private String tipo_participante;*/

    public Project(String identificador, String titulo) {
        this.identificador = identificador;
        this.titulo = titulo;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

}
