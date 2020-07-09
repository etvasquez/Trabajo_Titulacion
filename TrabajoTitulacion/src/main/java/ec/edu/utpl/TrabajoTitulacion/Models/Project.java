package ec.edu.utpl.TrabajoTitulacion.Models;

public class Project {
    private String identificador;
    private String fecha_fin_participacion;
    private String fecha_inicio_participacion;
    private String rol_proyecto;
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
    private String titulo;
    private String tipo_proyecto;
    private String tipo_participante;

    public Project() {
    }

    public Project(String identificador, String fecha_fin_participacion, String fecha_inicio_participacion, String rol_proyecto, String area_conocimiento, String area_conocimiento_especifica, String avance, String cobertura, String codigo_proyecto, String descripcion, String estado_validacion, String fecha_inicio, String fecha_fin, String fondo_externo, String fondo_utpl, String incluye_estudiantes, String incluye_financiamiento_externo, String linea_investigacion, String moneda, String objetivos, String presupuesto, String programa, String reprogramado, String smarland, String sub_area_conocimiento, String tipo_convocatoria, String total_general, String titulo, String tipo_proyecto, String tipo_participante) {
        this.identificador = identificador;
        this.fecha_fin_participacion = fecha_fin_participacion;
        this.fecha_inicio_participacion = fecha_inicio_participacion;
        this.rol_proyecto = rol_proyecto;
        this.area_conocimiento = area_conocimiento;
        this.area_conocimiento_especifica = area_conocimiento_especifica;
        this.avance = avance;
        this.cobertura = cobertura;
        this.codigo_proyecto = codigo_proyecto;
        this.descripcion = descripcion;
        this.estado_validacion = estado_validacion;
        this.fecha_inicio = fecha_inicio;
        this.fecha_fin = fecha_fin;
        this.fondo_externo = fondo_externo;
        this.fondo_utpl = fondo_utpl;
        this.incluye_estudiantes = incluye_estudiantes;
        this.incluye_financiamiento_externo = incluye_financiamiento_externo;
        this.linea_investigacion = linea_investigacion;
        this.moneda = moneda;
        this.objetivos = objetivos;
        this.presupuesto = presupuesto;
        this.programa = programa;
        this.reprogramado = reprogramado;
        this.smarland = smarland;
        this.sub_area_conocimiento = sub_area_conocimiento;
        this.tipo_convocatoria = tipo_convocatoria;
        this.total_general = total_general;
        this.titulo = titulo;
        this.tipo_proyecto = tipo_proyecto;
        this.tipo_participante = tipo_participante;
    }

    public String getIdentificador() {
        return identificador;
    }

    public void setIdentificador(String identificador) {
        this.identificador = identificador;
    }

    public String getFecha_fin_participacion() {
        return fecha_fin_participacion;
    }

    public void setFecha_fin_participacion(String fecha_fin_participacion) {
        this.fecha_fin_participacion = fecha_fin_participacion;
    }

    public String getFecha_inicio_participacion() {
        return fecha_inicio_participacion;
    }

    public void setFecha_inicio_participacion(String fecha_inicio_participacion) {
        this.fecha_inicio_participacion = fecha_inicio_participacion;
    }

    public String getRol_proyecto() {
        return rol_proyecto;
    }

    public void setRol_proyecto(String rol_proyecto) {
        this.rol_proyecto = rol_proyecto;
    }

    public String getArea_conocimiento() {
        return area_conocimiento;
    }

    public void setArea_conocimiento(String area_conocimiento) {
        this.area_conocimiento = area_conocimiento;
    }

    public String getArea_conocimiento_especifica() {
        return area_conocimiento_especifica;
    }

    public void setArea_conocimiento_especifica(String area_conocimiento_especifica) {
        this.area_conocimiento_especifica = area_conocimiento_especifica;
    }

    public String getAvance() {
        return avance;
    }

    public void setAvance(String avance) {
        this.avance = avance;
    }

    public String getCobertura() {
        return cobertura;
    }

    public void setCobertura(String cobertura) {
        this.cobertura = cobertura;
    }

    public String getCodigo_proyecto() {
        return codigo_proyecto;
    }

    public void setCodigo_proyecto(String codigo_proyecto) {
        this.codigo_proyecto = codigo_proyecto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado_validacion() {
        return estado_validacion;
    }

    public void setEstado_validacion(String estado_validacion) {
        this.estado_validacion = estado_validacion;
    }

    public String getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(String fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public String getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(String fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getFondo_externo() {
        return fondo_externo;
    }

    public void setFondo_externo(String fondo_externo) {
        this.fondo_externo = fondo_externo;
    }

    public String getFondo_utpl() {
        return fondo_utpl;
    }

    public void setFondo_utpl(String fondo_utpl) {
        this.fondo_utpl = fondo_utpl;
    }

    public String getIncluye_estudiantes() {
        return incluye_estudiantes;
    }

    public void setIncluye_estudiantes(String incluye_estudiantes) {
        this.incluye_estudiantes = incluye_estudiantes;
    }

    public String getIncluye_financiamiento_externo() {
        return incluye_financiamiento_externo;
    }

    public void setIncluye_financiamiento_externo(String incluye_financiamiento_externo) {
        this.incluye_financiamiento_externo = incluye_financiamiento_externo;
    }

    public String getLinea_investigacion() {
        return linea_investigacion;
    }

    public void setLinea_investigacion(String linea_investigacion) {
        this.linea_investigacion = linea_investigacion;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(String presupuesto) {
        this.presupuesto = presupuesto;
    }

    public String getPrograma() {
        return programa;
    }

    public void setPrograma(String programa) {
        this.programa = programa;
    }

    public String getReprogramado() {
        return reprogramado;
    }

    public void setReprogramado(String reprogramado) {
        this.reprogramado = reprogramado;
    }

    public String getSmarland() {
        return smarland;
    }

    public void setSmarland(String smarland) {
        this.smarland = smarland;
    }

    public String getSub_area_conocimiento() {
        return sub_area_conocimiento;
    }

    public void setSub_area_conocimiento(String sub_area_conocimiento) {
        this.sub_area_conocimiento = sub_area_conocimiento;
    }

    public String getTipo_convocatoria() {
        return tipo_convocatoria;
    }

    public void setTipo_convocatoria(String tipo_convocatoria) {
        this.tipo_convocatoria = tipo_convocatoria;
    }

    public String getTotal_general() {
        return total_general;
    }

    public void setTotal_general(String total_general) {
        this.total_general = total_general;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo_proyecto() {
        return tipo_proyecto;
    }

    public void setTipo_proyecto(String tipo_proyecto) {
        this.tipo_proyecto = tipo_proyecto;
    }

    public String getTipo_participante() {
        return tipo_participante;
    }

    public void setTipo_participante(String tipo_participante) {
        this.tipo_participante = tipo_participante;
    }
}
