package ec.edu.utpl.TrabajoTitulacion.Model;

public class Administracion {

    private String dominio;
    private String puerto;
    private String nombreBD;
    private String user;
    private String password;

    public Administracion(){}

    public Administracion(String dominio, String puerto, String nombreBD,
                          String user, String password) {
        this.dominio = dominio;
        this.puerto = puerto;
        this.nombreBD = nombreBD;
        this.user = user;
        this.password = password;
    }

    public String getDominio() {
        return dominio;
    }

    public void setDominio(String dominio) {
        this.dominio = dominio;
    }

    public String getPuerto() {
        return puerto;
    }

    public void setPuerto(String puerto) {
        this.puerto = puerto;
    }

    public String getNombreBD() {
        return nombreBD;
    }

    public void setNombreBD(String nombreBD) {
        this.nombreBD = nombreBD;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
