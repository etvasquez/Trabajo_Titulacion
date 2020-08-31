package ec.edu.utpl.TrabajoTitulacion.Model;

public class Relacion {
    private String from;
    private String to;
    private int value;
    private String title;

    public Relacion() {
    }

    public Relacion(String from, String to, int value, String title) {
        this.from = from;
        this.to = to;
        this.value = value;
        this.title = title;
    }

    public Relacion(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
