package ec.edu.utpl.TrabajoTitulacion.Model;

public class Nodo {

    private String id;
    private String label;
    private String group;
    private String title;
    private String image;
    private String shape;

    public Nodo() {
    }

    public Nodo(String id, String label, String group, String title, String image,String shape) {
        this.id = id;
        this.label = label;
        this.group = group;
        this.title = title;
        this.image = image;
        this.shape = shape;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShape() {
        return shape;
    }

    public void setShape(String shape) {
        this.shape = shape;
    }

}
