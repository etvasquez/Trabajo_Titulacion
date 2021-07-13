package ec.edu.utpl.TrabajoTitulacion.Controller;

import ec.edu.utpl.TrabajoTitulacion.Model.Administracion;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.RDFWriter;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.sparql.vocabulary.FOAF;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.vocabulary.SKOS;

import java.io.*;
import java.sql.*;
import java.util.regex.Pattern;

public class trasformacionRDF {

    public void obtenerCSV(String docente, String proyecto, String codificacion) throws FileNotFoundException {
            String [][] docentes = leerDocentesCSV(docente, codificacion);
            String [][] proyectos = leerProyectosCSV(proyecto, codificacion);
            crearOntologia(docentes,proyectos,docentes.length,proyectos.length);
    }

    public void obtenerDatosBase(Administracion administracion){
        String jdbcURL = "jdbc:mysql://"+administracion.getDominio()+":"+administracion.getPuerto()
                +"/"+administracion.getNombreBD()+
                "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
        String username = administracion.getUser();
        String password = administracion.getPassword();
        String csvFileDocente = "data-docente-export.csv";
        String csvFileProyecto = "data-proyecto-export.csv";
        try (Connection connection = DriverManager.getConnection(jdbcURL, username, password)) {
            String sql = "SELECT * FROM _docentes";
            Statement statement = connection.createStatement();
            ResultSet result = statement.executeQuery(sql);
            BufferedWriter fileWriter = new BufferedWriter(new OutputStreamWriter
                    (new FileOutputStream(csvFileDocente), "ISO-8859-15"));
            int contadorDocente = 0;
            while (result.next()) {
                if(contadorDocente != 0){
                    fileWriter.newLine();
                }
                contadorDocente++;
                String ID_DOCENTE = result.getString("ID_DOCENTE");
                String CEDULA = result.getString("CEDULA");
                String PRIMER_APELLIDO = result.getString("PRIMER_APELLIDO");
                String SEGUNDO_APELLIDO = result.getString("SEGUNDO_APELLIDO");
                String PRIMER_NOMBRE = result.getString("PRIMER_NOMBRE");
                String SEGUNDO_NOMBRE = result.getString("SEGUNDO_NOMBRE");
                String AREA = result.getString("AREA");
                String DEPARTAMENTO = result.getString("DEPARTAMENTO");
                String SECCION_DEPARTAMENTAL = result.getString("SECCION_DEPARTAMENTAL");
                String MODALIDAD_CONTRAACTUAL = result.getString("MODALIDAD_CONTRAACTUAL");
                String TITULAR = result.getString("TITULAR");
                String TIPO_DOCENTE = result.getString("TIPO_DOCENTE");
                String STATUS = result.getString("STATUS");
                String GENERO = result.getString("GENERO");
                String ESTADO_CIVIL = result.getString("ESTADO_CIVIL");
                String NACIONALIDAD = result.getString("NACIONALIDAD");
                String LUGAR_NACIMIENTO = result.getString("LUGAR_NACIMIENTO");
                String FECHA_NACIMIENTO = result.getString("FECHA_NACIMIENTO");
                String PAIS_RESIDENCIA = result.getString("PAIS_RESIDENCIA");
                String PROVINCIA = result.getString("PROVINCIA");
                String CIUDAD = result.getString("CIUDAD");
                String CANTON = result.getString("CANTON");
                String PARROQUIA = result.getString("PARROQUIA");
                String DIRECCION = result.getString("DIRECCION");
                String TELEFONO_INSTITUCION = result.getString("TELEFONO_INSTITUCION");
                String EXTENSION = result.getString("EXTENSION");
                String EMAIL_INSTITUCIONAL = result.getString("EMAIL_INSTITUCIONAL");
                String FOTO_INSTITUCIONAL = result.getString("FOTO");
                String line = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s",
                        ID_DOCENTE, CEDULA,PRIMER_APELLIDO,SEGUNDO_APELLIDO,PRIMER_NOMBRE,SEGUNDO_NOMBRE,AREA,DEPARTAMENTO,SECCION_DEPARTAMENTAL,
                        MODALIDAD_CONTRAACTUAL,TITULAR,TIPO_DOCENTE,STATUS,GENERO,ESTADO_CIVIL,NACIONALIDAD,LUGAR_NACIMIENTO,FECHA_NACIMIENTO,
                        PAIS_RESIDENCIA,PROVINCIA,CIUDAD,CANTON,PARROQUIA,DIRECCION,TELEFONO_INSTITUCION,EXTENSION,EMAIL_INSTITUCIONAL,FOTO_INSTITUCIONAL);
                fileWriter.write(line);
            }
            statement.close();
            fileWriter.close();
            sql = "SELECT * FROM _proyectos";
            statement = connection.createStatement();
            result = statement.executeQuery(sql);
            BufferedWriter fileWriter1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFileProyecto), "ISO-8859-15"));
            int contadorProyectos = 0;
            while (result.next()) {
                if(contadorProyectos != 0){
                    fileWriter1.newLine();
                }
                contadorProyectos++;
                String ID = result.getString("ID");
                String CEDULA = result.getString("CEDULA");
                String ROL = result.getString("ROL");
                String TIPO_PARTICIPANTE = result.getString("TIPO_PARTICIPANTE");
                String FECHA_INICIO_PARTICIPANTE = result.getString("FECHA_INICIO_PARTICIPANTE");
                String FECHA_FIN_PARTICIPANTE = result.getString("FECHA_FIN_PARTICIPANTE");
                String ID_PROYECTO = result.getString("ID_PROYECTO");
                String FECHA_INICIO = result.getString("FECHA_INICIO");
                String FECHA_CIERRE = result.getString("FECHA_CIERRE");
                String CODIGO_PROYECTO = result.getString("CODIGO_PROYECTO");
                String NOMBRE_PROYECTO = result.getString("NOMBRE_PROYECTO");
                String AREA_CONOCIMIENTO = result.getString("AREA_CONOCIMIENTO");
                String SUB_AREA_CONOCIMIENTO = result.getString("SUB_AREA_CONOCIMIENTO");
                String AREA_CONOCIMIENTO_ESPECIFICA = result.getString("AREA_CONOCIMIENTO_ESPECIFICA");
                String DESCRIPCION = result.getString("DESCRIPCION");
                String TIPO_PROYECTO = result.getString("TIPO_PROYECTO");
                String TIPO_CONVOCATORIA = result.getString("TIPO_CONVOCATORIA");
                String INCLUYE_ESTUDIANTES = result.getString("INCLUYE_ESTUDIANTES");
                String INCLUYE_FINANCIAMIENTO_EXTERNO = result.getString("INCLUYE_FINANCIAMIENTO_EXTERNO");
                String COBERTURA = result.getString("COBERTURA");
                String SMARTLAND = result.getString("SMARTLAND");
                String REPROGRAMADO = result.getString("REPROGRAMADO");
                String PORCENTAJE_AVANCE = result.getString("PORCENTAJE_AVANCE");
                String FONDO_UTPL = result.getString("FONDO_UTPL");
                String FOND_EXTERNO = result.getString("FOND_EXTERNO");
                String TOTAL_GENERAL = result.getString("TOTAL_GENERAL");
                String ESTADO = result.getString("ESTADO");
                String PROGRAMA = result.getString("PROGRAMA");
                String LINEA_INVESTIGACION = result.getString("LINEA_INVESTIGACION");
                String OBJETIVOS = result.getString("OBJETIVOS");
                String MONEDA = result.getString("MONEDA");
                String PRESUPUESTO = result.getString("PRESUPUESTO");
                String ESTADO_VALIDACION = result.getString("ESTADO_VALIDACION");
                String line = String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s",
                        ID, CEDULA,ROL,TIPO_PARTICIPANTE,FECHA_INICIO_PARTICIPANTE,FECHA_FIN_PARTICIPANTE,ID_PROYECTO,FECHA_INICIO,
                        FECHA_CIERRE,CODIGO_PROYECTO,NOMBRE_PROYECTO,AREA_CONOCIMIENTO,SUB_AREA_CONOCIMIENTO,AREA_CONOCIMIENTO_ESPECIFICA,DESCRIPCION,TIPO_PROYECTO,TIPO_CONVOCATORIA,
                        INCLUYE_ESTUDIANTES,INCLUYE_FINANCIAMIENTO_EXTERNO,COBERTURA,SMARTLAND,REPROGRAMADO,PORCENTAJE_AVANCE,FONDO_UTPL,FOND_EXTERNO,TOTAL_GENERAL,
                        ESTADO,PROGRAMA,LINEA_INVESTIGACION,OBJETIVOS,MONEDA,PRESUPUESTO,ESTADO_VALIDACION);
                fileWriter1.write(line);
            }
            statement.close();
            fileWriter1.close();
            System.out.println("CONTADOR CODENTES:"+contadorDocente);
            System.out.println("CONTADOR PRO:"+contadorProyectos);
            String [][] docentes = leerDocentes(contadorDocente,27);
            String [][] proyectos = leerProyectos(contadorProyectos,32);
            crearOntologia(docentes,proyectos,contadorDocente,contadorProyectos);
        } catch (SQLException e) {
            System.out.println("Datababse error:");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("File IO error:");
            e.printStackTrace();
        }

    }

    private String[][] leerDocentesCSV(String nombre, String codificacion){
        String[][] atributos = new String[2017][27];
        int limite = 0;
        BufferedReader readPerson = null;
        try {
            // Abrir el .csv en buffer de lectura
            String archivo = "/Users/eriiv/OneDrive/Escritorio/TT/V2/Implementacion/v1/Trabajo_Titulacion/TrabajoTitulacion/src/data-docente-export.csv";
            readPerson = new BufferedReader(new FileReader(archivo));
            readPerson = new BufferedReader (new InputStreamReader (new FileInputStream(archivo), codificacion));
            // Leer una linea del archivo
            String linea = readPerson.readLine();
            while (linea != null) {
                // Sepapar la linea leída con el separador definido previamente
                atributos[limite] = linea.split(Pattern.quote("|"),-1);
                // Volver a leer otra línea del fichero
                linea = readPerson.readLine();
                limite = limite + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cierro el buffer de lectura
            if (readPerson  != null) {
                try {
                    readPerson.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return atributos;
    }

    private String[][] leerProyectosCSV(String proyecto, String codificacion){
        String[][] atributos = new String[5857][32];
        int limite = 0;
        BufferedReader readPerson = null;
        try {
            // Abrir el .csv en buffer de lectura
            String archivo = "/Users/eriiv/OneDrive/Escritorio/TT/V2/Implementacion/v1/Trabajo_Titulacion/TrabajoTitulacion/src/data-proyecto-export.csv";
            readPerson = new BufferedReader(new FileReader(archivo));
            readPerson = new BufferedReader (new InputStreamReader (new FileInputStream(archivo), codificacion));
            // Leer una linea del archivo
            String linea = readPerson.readLine();
            while (linea != null) {
                // Sepapar la linea leída con el separador definido previamente
                atributos[limite] = linea.split(Pattern.quote("|"),-1);
                // Volver a leer otra línea del fichero
                linea = readPerson.readLine();
                limite = limite + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cierro el buffer de lectura
            if (readPerson  != null) {
                try {
                    readPerson.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return atributos;
    }

    private String[][] leerDocentes(int contadorFilas, int contadorColumnas){
        String[][] atributos = new String[contadorFilas][contadorColumnas];
        int limite = 0;
        BufferedReader readPerson = null;
        try {
            // Abrir el .csv en buffer de lectura
            String archivo = "/Users/eriiv/OneDrive/Escritorio/TT/V2/Implementacion/v1/Trabajo_Titulacion/TrabajoTitulacion/data-docente-export.csv";
            readPerson = new BufferedReader(new FileReader(archivo));
            readPerson = new BufferedReader (new InputStreamReader (new FileInputStream(archivo), "ISO-8859-15"));
            // Leer una linea del archivo
            String linea = readPerson.readLine();
            while (linea != null) {
                // Sepapar la linea leída con el separador definido previamente
                atributos[limite] = linea.split(Pattern.quote("|"),-1);
                // Volver a leer otra línea del fichero
                linea = readPerson.readLine();
                limite = limite + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cierro el buffer de lectura
            if (readPerson  != null) {
                try {
                    readPerson.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return atributos;
    }

    private String[][] leerProyectos(int contadorFilas, int contadorColumnas){
       String[][] atributos_proyectos = new String[contadorFilas][contadorColumnas];
        int limite_proyectos = 0;
        BufferedReader readProjects = null;
        try {
            // Abrir el .csv en buffer de lectura
            //readProjects = new BufferedReader(new FileReader("/Users/eriiv/OneDrive/Escritorio/TT/V2/Implementacion/v1/Trabajo_Titulacion/TrabajoTitulacion/data-proyecto-export.csv"));
            readProjects = new BufferedReader (new InputStreamReader (new FileInputStream("/Users/eriiv/OneDrive/Escritorio/TT/V2/Implementacion/v1/Trabajo_Titulacion/TrabajoTitulacion/data-proyecto-export.csv"), "ISO-8859-15"));
            // Leer una linea del archivo
            String linea = readProjects.readLine();
            while (linea != null) {
                // Sepapar la linea leída con el separador definido previamente
                //atributos_proyectos[limite_proyectos] = linea.split(Pattern.quote("\t"),-1);
                atributos_proyectos[limite_proyectos] = linea.split(Pattern.quote("|"),-1);
                // Volver a leer otra línea del fichero
                linea = readProjects.readLine();
                limite_proyectos = limite_proyectos + 1;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // Cierro el buffer de lectura
            if (readProjects  != null) {
                try {
                    readProjects.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return atributos_proyectos;
    }

    private void crearOntologia(String[][] atributos, String[][] atributos_proyectos, int limite, int limite_proyectos)
            throws FileNotFoundException {
        //definición del fichero donde insertaremos los datos RDF
        File f = new File("/Users/eriiv/OneDrive/Escritorio/TT/V2/Implementacion/v1/repositorio-rdf.rdf");
        FileOutputStream os = new FileOutputStream(f);
        Model model = ModelFactory.createDefaultModel();
        //Fijar prefijo de la ontología
        String dataPrefix = "http://utpl.edu.ec/data/";
        model.setNsPrefix("data", dataPrefix);
        String prefixProject = "http://utpl.edu.ec/data/project/";
        model.setNsPrefix("project", prefixProject);
        String prefixIDProject = "http://utpl.edu.ec/data/idproject/";
        model.setNsPrefix("project", prefixIDProject);
        String prefixPeople = "http://utpl.edu.ec/data/people/";
        model.setNsPrefix("people", prefixPeople);
        String schema = "http://schema.org/";
        model.setNsPrefix("schema", schema);
        String skos = "http://www.w3.org/2004/02/skos/core/";
        model.setNsPrefix("skos", skos);
        String prefixPais = "http://utpl.edu.ec/data/country/";
        model.setNsPrefix("skos", prefixPais);
        String prefixProvincia = "http://utpl.edu.ec/data/province/";
        model.setNsPrefix("skos", prefixProvincia);
        String prefixCiudad = "http://utpl.edu.ec/data/city/";
        model.setNsPrefix("skos", prefixCiudad);
        String prefixCanton = "http://utpl.edu.ec/data/canton/";
        model.setNsPrefix("skos", prefixCanton);
        Model dboModel = ModelFactory.createDefaultModel();
        Resource person  = model.createResource("");
        Resource project  = model.createResource("");
        //Crear tripletas personas
        for (int i = 0; i < limite; i++) {
            //Obtener datos
            String personURI = prefixPeople + atributos[i][1];
            String id_person = atributos[i][1];
            String nombres = atributos[i][2]+" "+atributos[i][3];
            String apellidos = atributos[i][4]+" "+atributos[i][5];
            String area = atributos[i][6];
            String depertamento = atributos[i][7];
            String seccion = atributos[i][8];
            String modalidad = atributos[i][9];
            String titular = atributos[i][10];
            String tipo = atributos[i][11];
            String status = atributos[i][12];
            String genero = atributos[i][13];
            String estadocivil = atributos[i][14];
            String nacionalidad = atributos[i][15];
            String pais = prefixPais + atributos[i][18];
            String paislabel = atributos[i][18];
            String provincia = prefixProvincia + atributos[i][19];
            String provincialabel = atributos[i][19];
            String ciudad = prefixCiudad + atributos[i][20];
            String ciudadlabel = atributos[i][20];
            String canton = prefixCanton + atributos[i][21];
            String cantonlabel = atributos[i][21];
            String parroquia = atributos[i][22];
            String direccion = atributos[i][23];
            String telefono = atributos[i][24];
            String extension = atributos[i][25];
            String email = atributos[i][26];
            String foto = atributos[i][27];
            //Recurso area persona
            Resource areaPerson = model.createResource(area)
            .addProperty(RDF.type, dboModel.getResource(schema + "areaPerson"))
            .addProperty(RDFS.label, area);

            //Recurso departamento persona
            Resource depertamentPerson = model.createResource(depertamento)
            .addProperty(RDF.type, dboModel.getResource(schema + "depertamentPerson"))
            .addProperty(RDFS.label, depertamento);

            //Recurso seccion departamental persona
            Resource seccionPerson = model.createResource(seccion)
            .addProperty(RDF.type, dboModel.getResource(schema + "seccionPerson"))
            .addProperty(RDFS.label, seccion);

            //Recurso modalidad contractual persona
            Resource modalidadPerson = model.createResource(modalidad)
            .addProperty(RDF.type, dboModel.getResource(schema + "modalidadPerson"))
            .addProperty(RDFS.label, modalidad);

            //Recurso tipo docente persona
            Resource tipoOcupationPerson = model.createResource(tipo)
            .addProperty(RDF.type, dboModel.getResource(schema + "tipoOcupationPerson"))
            .addProperty(RDFS.label, tipo);

            //Recurso status persona
            Resource statusPerson = model.createResource(status)
            .addProperty(RDF.type, dboModel.getResource(schema + "statusPerson"))
            .addProperty(RDFS.label, status);

            //Recurso nacionalidad persona
            Resource national = model.createResource(nacionalidad)
                    .addProperty(RDF.type, dboModel.getResource(schema + "national"))
                    .addProperty(RDFS.label, nacionalidad);

            //Recurso canton residencia
            Resource cantones = model.createResource(canton);
            cantones.addProperty(RDFS.label, cantonlabel);
            cantones.addProperty(RDF.type, SKOS.Concept);

            //Recurso ciudad residencia
            Resource cuidades = model.createResource(ciudad);
            cuidades.addProperty(RDFS.label, ciudadlabel);
            cuidades.addProperty(RDF.type, SKOS.Concept);
            cuidades.addProperty(dboModel.getProperty(skos+"canton"),cantones);

            //Recurso provincia residencia
            Resource provincias = model.createResource(provincialabel);
            provincias.addProperty(RDFS.label, provincia);
            provincias.addProperty(RDF.type, SKOS.Concept);
            provincias.addProperty(dboModel.getProperty(SKOS.broader + "city"), cuidades);

            //Recurso pais residencia
            Resource paises = model.createResource(pais);
            paises.addProperty(RDFS.label,paislabel);
            paises.addProperty(RDF.type, SKOS.Concept);
            paises.addProperty(dboModel.getProperty(SKOS.broader + "province"), provincias);
            //Recurso global persona
            person = model.createResource(personURI)
                    .addProperty(RDF.type, FOAF.Person)
                    .addProperty(FOAF.firstName, nombres)
                    .addProperty(FOAF.lastName, apellidos)
                    .addProperty(FOAF.status, status)
                    .addProperty(FOAF.gender,genero)
                    .addProperty(FOAF.mbox, email)
                    .addProperty(FOAF.phone, telefono)
                    .addProperty(FOAF.member,titular)
                    .addProperty(FOAF.img, foto)
                    .addProperty(dboModel.getProperty(schema + "id_person"), id_person)
                    .addProperty(dboModel.getProperty(schema + "estadocivil"), estadocivil)
                    .addProperty(dboModel.getProperty(schema + "parroquia"), parroquia)
                    .addProperty(dboModel.getProperty(schema + "direccion"), direccion)
                    .addProperty(dboModel.getProperty(schema + "extension"), extension)
                    .addProperty(dboModel.getProperty(schema + "areaPerson"), areaPerson)
                    .addProperty(dboModel.getProperty(schema + "depertamentPerson"), depertamentPerson)
                    .addProperty(dboModel.getProperty(schema + "seccionPerson"), seccionPerson)
                    .addProperty(dboModel.getProperty(schema + "modalidadPerson"), modalidadPerson)
                    .addProperty(dboModel.getProperty(schema + "tipoOcupationPerson"), tipoOcupationPerson)
                    .addProperty(dboModel.getProperty(schema + "statusPerson"), statusPerson)
                    .addProperty(dboModel.getProperty(schema + "national"), national)
                    .addProperty(dboModel.getProperty(skos + "cantones"), cantones);

        }
       //Crear tripletas proyectos
        for (int i = 0; i < limite_proyectos; i++) {
            //Obtener datos projectos
            String ide_Project = atributos_proyectos[i][6];
            String projectURI = prefixProject + atributos_proyectos[i][6];
            String id_person = prefixPeople + atributos_proyectos[i][1];
            String id_project = prefixIDProject + atributos_proyectos[i][0];
            String rol = atributos_proyectos[i][2];
            String tipo_participante = atributos_proyectos[i][3];
            String fecha_inicio_participante = atributos_proyectos[i][4];
            String fecha_fin_participante = atributos_proyectos[i][5];
            String fecha_inicio = atributos_proyectos[i][7];
            String fecha_fin = atributos_proyectos[i][8];
            String codigo_proyecto = atributos_proyectos[i][9];
            String nombre_proyecto = atributos_proyectos[i][10];
            String area_conocimiento = atributos_proyectos[i][11];
            String sub_area_conocimiento = atributos_proyectos[i][12];
            String area_conocimiento_especifica = atributos_proyectos[i][13];
            String descripcion = atributos_proyectos[i][14];
            String tipo_proyecto = atributos_proyectos[i][15];
            String tipo_convocatoria = atributos_proyectos[i][16];
            String incluye_estudiantes = atributos_proyectos[i][17];
            String incluye_financiamiento_externo = atributos_proyectos[i][18];
            String cobertura = atributos_proyectos[i][19];
            String smartland = atributos_proyectos[i][20];
            String reprogramado = atributos_proyectos[i][21];
            String avance = atributos_proyectos[i][22];
            String fondo_utpl = atributos_proyectos[i][23];
            String fondo_externo = atributos_proyectos[i][24];
            String total_general = atributos_proyectos[i][25];
            String estado = atributos_proyectos[i][26];
            String programa = atributos_proyectos[i][27];
            String linea_investigacion = atributos_proyectos[i][28];
            String objetivos = atributos_proyectos[i][29];
            String moneda = atributos_proyectos[i][30];
            String presupuesto = atributos_proyectos[i][31];
            String estado_validacion = atributos_proyectos[i][32];
            //Recurso rol proyecto
            Resource rolProyecto = model.createResource(rol)
                    .addProperty(RDFS.label, rol);

            //Recurso tipo participante
            Resource tipoparticipante = model.createResource(tipo_participante)
                    .addProperty(RDF.type, dboModel.getResource(schema + "tipoparticipante"))
                    .addProperty(RDFS.label, tipo_participante);

            //Recurso tipo proyecto
            Resource tipoproyecto = model.createResource(tipo_proyecto)
                    .addProperty(RDF.type, dboModel.getResource(schema + "tipo_proyecto"))
                    .addProperty(RDFS.label, tipo_proyecto);

            //Recurso estado proyecto
            Resource estadoproyecto = model.createResource(estado)
                    .addProperty(RDF.type, dboModel.getResource(schema + "estadoproyecto"))
                    .addProperty(RDFS.label, estado);

            //Recurso area proyecto
            Resource areaproyecto = model.createResource(area_conocimiento)
                    .addProperty(RDF.type, dboModel.getResource(schema + "area_conocimiento"))
                    .addProperty(RDFS.label, area_conocimiento);

            //Recurso proyecto
            project = model.createResource(projectURI)
                    .addProperty(RDF.type, FOAF.Project)
                    .addProperty(FOAF.title, nombre_proyecto)
                    .addProperty(dboModel.getProperty(schema + "ide_project"), ide_Project)
                    .addProperty(dboModel.getProperty(schema + "fecha_inicio"), fecha_inicio)
                    .addProperty(dboModel.getProperty(schema + "fecha_fin"), fecha_fin)
                    .addProperty(dboModel.getProperty(schema + "codigo_proyecto"), codigo_proyecto)
                    .addProperty(dboModel.getProperty(schema + "areaproyecto"), areaproyecto)
                    .addProperty(dboModel.getProperty(schema + "sub_area_conocimiento"), sub_area_conocimiento)
                    .addProperty(dboModel.getProperty(schema + "area_conocimiento_especifica"), area_conocimiento_especifica)
                    .addProperty(dboModel.getProperty(schema + "descripcion"), descripcion)
                    .addProperty(dboModel.getProperty(schema + "tipoproyecto"), tipoproyecto)
                    .addProperty(dboModel.getProperty(schema + "tipo_convocatoria"), tipo_convocatoria)
                    .addProperty(dboModel.getProperty(schema + "incluye_estudiantes"), incluye_estudiantes)
                    .addProperty(dboModel.getProperty(schema + "incluye_financiamiento_externo"), incluye_financiamiento_externo)
                    .addProperty(dboModel.getProperty(schema + "cobertura"), cobertura)
                    .addProperty(dboModel.getProperty(schema + "smartland"), smartland)
                    .addProperty(dboModel.getProperty(schema + "reprogramado"), reprogramado)
                    .addProperty(dboModel.getProperty(schema + "avance"), avance)
                    .addProperty(dboModel.getProperty(schema + "fondo_utpl"), fondo_utpl)
                    .addProperty(dboModel.getProperty(schema + "fondo_externo"), fondo_externo)
                    .addProperty(dboModel.getProperty(schema + "total_general"), total_general)
                    .addProperty(dboModel.getProperty(schema + "estadoproyecto"), estadoproyecto)
                    .addProperty(dboModel.getProperty(schema + "programa"), programa)
                    .addProperty(dboModel.getProperty(schema + "linea_investigacion"), linea_investigacion)
                    .addProperty(dboModel.getProperty(schema + "objetivos"), objetivos)
                    .addProperty(dboModel.getProperty(schema + "moneda"), moneda)
                    .addProperty(dboModel.getProperty(schema + "presupuesto"), presupuesto)
                    .addProperty(dboModel.getProperty(schema + "estado_validacion"), estado_validacion)
                    .addProperty(dboModel.getProperty(schema + "tipoparticipante"), tipoparticipante);

            //Recurso id proyecto
            Resource idProyecto = model.createResource(id_project)
                    .addProperty(dboModel.getProperty(schema + "rolProyecto"), rolProyecto)
                    .addProperty(dboModel.getProperty(schema + "fecha_inicio_participante"), fecha_inicio_participante)
                    .addProperty(dboModel.getProperty(schema + "fecha_fin_participante"), fecha_fin_participante)
                    .addProperty(dboModel.getProperty(schema + "idProject"), project)
                    .addProperty(RDFS.label, id_project);

            Resource person1 = model.createResource(id_person)
                    .addProperty(FOAF.currentProject, idProyecto)
                    .addProperty(dboModel.getProperty(FOAF.currentProject + "person"), id_person);

        }
        // Save to a fileturtle
        RDFWriter writer = model.getWriter("RDF/XML");
        writer.write(model, os, dataPrefix);
    }

}
