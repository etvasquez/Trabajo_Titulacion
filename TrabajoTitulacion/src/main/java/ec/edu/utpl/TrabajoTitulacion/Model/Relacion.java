package ec.edu.utpl.TrabajoTitulacion.Model;

public class Relacion {
    private String target;
    private String source;

    public Relacion(String target, String source) {
        this.target = target;
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
