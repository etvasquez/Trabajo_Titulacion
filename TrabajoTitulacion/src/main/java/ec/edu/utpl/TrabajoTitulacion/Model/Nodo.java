package ec.edu.utpl.TrabajoTitulacion.Model;

public class Nodo {

    private String id;
    private String label;
    private String group;
    private String title;

    public Nodo() {
    }

    public Nodo(String id, String label, String group, String title) {
        this.id = id;
        this.label = label;
        this.group = group;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
