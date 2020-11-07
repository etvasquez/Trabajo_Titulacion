package ec.edu.utpl.TrabajoTitulacion.Controller;

import ec.edu.utpl.TrabajoTitulacion.Model.Administracion;

import java.io.*;

public class test {
    public static void main(String[] args) throws FileNotFoundException {
        trasformacionRDF t = new trasformacionRDF();
        Administracion administracion = new Administracion("localhost","3306","repositorio","root","");
        t.obtenerDatosBase(administracion);
    }



}
