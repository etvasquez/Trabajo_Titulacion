package ec.edu.utpl.TrabajoTitulacion.Model;

public class Proyecto {
    private String titulo;
    private String descripcion;
    private String tipo;
    private String incluye_estudiantes;
    private String cobertura;
    private String fechainicio;
    private String fechafin;
    private String ife;
    private String smartland;
    private String reprogramado;
    private String avance;
    private String financiamiento_utpl;
    private String financiamiento_externo;
    private String financiamiento_general;
    private String estado;
    private String programa;
    private String objetivo;
    private String presupuesto;

    public Proyecto() {
    }

    public Proyecto(String titulo, String descripcion, String tipo, String incluye_estudiantes, String cobertura,
                    String fechainicio, String fechafin, String ife, String smartland, String reprogramado, String avance,
                    String financiamiento_utpl, String financiamiento_externo, String financiamiento_general, String estado,
                    String programa, String objetivo, String presupuesto) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.tipo = tipo;
        this.incluye_estudiantes = incluye_estudiantes;
        this.cobertura = cobertura;
        this.fechainicio = fechainicio;
        this.fechafin = fechafin;
        this.ife = ife;
        this.smartland = smartland;
        this.reprogramado = reprogramado;
        this.avance = avance;
        this.financiamiento_utpl = financiamiento_utpl;
        this.financiamiento_externo = financiamiento_externo;
        this.financiamiento_general = financiamiento_general;
        this.estado = estado;
        this.programa = programa;
        this.objetivo = objetivo;
        this.presupuesto = presupuesto;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
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

    public String getIncluye_estudiantes() {
        return incluye_estudiantes;
    }

    public void setIncluye_estudiantes(String incluye_estudiantes) {
        this.incluye_estudiantes = incluye_estudiantes;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public String getFechainicio() {
        return fechainicio;
    }

    public void setFechainicio(String fechainicio) {
        this.fechainicio = fechainicio;
    }

    public String getFechafin() {
        return fechafin;
    }

    public void setFechafin(String fechafin) {
        this.fechafin = fechafin;
    }

    public String getIfe() {
        return ife;
    }

    public void setIfe(String ife) {
        this.ife = ife;
    }

    public String getSmartland() {
        return smartland;
    }

    public void setSmartland(String smartland) {
        this.smartland = smartland;
    }

    public String getReprogramado() {
        return reprogramado;
    }

    public void setReprogramado(String reprogramado) {
        this.reprogramado = reprogramado;
    }

    public String getAvance() {
        return avance;
    }

    public void setAvance(String avance) {
        this.avance = avance;
    }

    public String getFinanciamiento_utpl() {
        return financiamiento_utpl;
    }

    public void setFinanciamiento_utpl(String financiamiento_utpl) {
        this.financiamiento_utpl = financiamiento_utpl;
    }

    public String getFinanciamiento_externo() {
        return financiamiento_externo;
    }

    public void setFinanciamiento_externo(String financiamiento_externo) {
        this.financiamiento_externo = financiamiento_externo;
    }

    public String getFinanciamiento_general() {
        return financiamiento_general;
    }

    public void setFinanciamiento_general(String financiamiento_general) {
        this.financiamiento_general = financiamiento_general;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(String presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
