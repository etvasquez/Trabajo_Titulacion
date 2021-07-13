package ec.edu.utpl.TrabajoTitulacion.Controller;

import ec.edu.utpl.TrabajoTitulacion.Model.Administracion;

public class test {
    public static void main(String[] args) {
        trasformacionRDF t = new trasformacionRDF();
        Administracion administracion = new Administracion("localhost","3306","repositorio","root","");
        t.obtenerDatosBase(administracion);
    }



}
