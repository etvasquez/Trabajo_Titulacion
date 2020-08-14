package ec.edu.utpl.TrabajoTitulacion.Controller;

public class test {
    public static void main(String[] args) {
        consultasBD query1 = new consultasBD();
        String var = "IDEL 2008: Primer Taller Presencial de Implementación y desarrollo E-Learning";
        String a=query1.getGrapProject("61");
        System.out.println(a);
    }
}
