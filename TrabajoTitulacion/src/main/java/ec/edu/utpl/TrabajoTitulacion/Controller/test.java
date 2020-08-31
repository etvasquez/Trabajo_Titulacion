package ec.edu.utpl.TrabajoTitulacion.Controller;

import ec.edu.utpl.TrabajoTitulacion.Model.Proyecto;

public class test {
    public static void main(String[] args) {
        consultasBD query1 = new consultasBD();
        String var = "IDEL 2008: Primer Taller Presencial de Implementación y desarrollo E-Learning";
        //Proyecto a=query1.InformacionProyecto("31");
        //System.out.println(query1.getGrapProjectID("31"));
        System.out.println(query1.getGrapAreaProject("Educación"));
    }
}
