package ec.edu.utpl.TrabajoTitulacion.Controller;

import ec.edu.utpl.TrabajoTitulacion.ConnectionDB.conectingGraphDB;
import ec.edu.utpl.TrabajoTitulacion.Model.*;
import org.eclipse.rdf4j.model.impl.SimpleLiteral;
import org.eclipse.rdf4j.query.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.ArrayList;

public class consultasBD{

    private static Logger logger = LoggerFactory.getLogger(conectingGraphDB.class);
    private static final Marker WTF_MARKER = MarkerFactory.getMarker("WTF");

    conectingGraphDB con = new conectingGraphDB();

    public String getGrapProjectID(String idProject) {
        ArrayList<Nodo> listNodos = new ArrayList<>();
        ArrayList<Relacion> listRelacion = new ArrayList<>();
        ArrayList<NodoRelacion> nodoRelacion = new ArrayList<>();
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                "SELECT ?idpersona ?nombre ?apellido ?id_project ?titulo ?rol ?correo ?area ?tipo " +
                "WHERE {"+
                "?s schema:ide_project '"+idProject+"' . "+
                "?id schema:idProject ?s . "+
                "?s schema:ide_project ?id_project ."+
                "?id schema:rolProyecto ?labelrol . "+
                "?labelrol rdfs:label ?rol . "+
                "?s schema:tipoproyecto ?labeltipo ." +
                "?labeltipo rdfs:label ?tipo ."+
                "?project j.2:currentProject ?id. "+
                "?project j.2:lastName ?nombre. "+
                "?project j.2:firstName ?apellido. "+
                "?project j.2:mbox ?correo . "+
                "?project schema:areaPerson ?labelarea . "+
                "?labelarea rdfs:label ?area . "+
                "?project schema:id_person ?idpersona ."+
                " ?s j.2:title ?titulo . " +
                "} ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        TupleQueryResult result = null;
        try {
            result = tupleQuery.evaluate();
            int contador = 0;
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idPersona =
                        (SimpleLiteral)bindingSet.getValue("idpersona");
                SimpleLiteral nombre =
                        (SimpleLiteral)bindingSet.getValue("nombre");
                SimpleLiteral apellido =
                        (SimpleLiteral)bindingSet.getValue("apellido");
                SimpleLiteral rol =
                        (SimpleLiteral)bindingSet.getValue("rol");
                SimpleLiteral correo =
                        (SimpleLiteral)bindingSet.getValue("correo");
                SimpleLiteral area =
                        (SimpleLiteral)bindingSet.getValue("area");
                SimpleLiteral idProyecto =
                        (SimpleLiteral)bindingSet.getValue("id_project");
                SimpleLiteral titulo =
                        (SimpleLiteral)bindingSet.getValue("titulo");
                SimpleLiteral tipo =
                        (SimpleLiteral)bindingSet.getValue("tipo");
                String titlePersona = nombre.stringValue().concat(" ").concat(apellido.stringValue());
                Nodo nodoPersona = new Nodo(idPersona.stringValue(),nombre.stringValue().concat("\n").concat(apellido.stringValue()),"participante",titlePersona);
                Nodo nodoProyecto = new Nodo(idProyecto.stringValue(),titulo.stringValue(),"projects",titulo.stringValue());
                Relacion relacion = new Relacion(idPersona.stringValue(),idProyecto.stringValue());
                if(contador==0){
                    listNodos.add(nodoProyecto);
                }
                contador++;
                listNodos.add(nodoPersona);
                listRelacion.add(relacion);
            }
            NodoRelacion nr = new NodoRelacion(listNodos,listRelacion);
            nodoRelacion.add(nr);
            json = mapper.writeValueAsString(nodoRelacion.get(0));
            System.out.println(json);
        }
        catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    qee.getStackTrace().toString(), qee);
        } finally {
            result.close();
        }
        return json;
    }

    public String getGrapProject(String xproyecto) {
        ArrayList<Nodo> listNodos = new ArrayList<>();
        ArrayList<Relacion> listRelacion = new ArrayList<>();
        ArrayList<NodoRelacion> nodoRelacion = new ArrayList<>();
        String json="";
        // Create ObjectMapper object.
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                "SELECT ?idpersona ?nombre ?apellido ?id_project ?titulo ?rol ?correo ?area ?tipo " +
                "WHERE {"+
                "?s schema:ide_project '"+xproyecto+"' . "+
                "?id schema:idProject ?s . "+
                "?id schema:rolProyecto ?labelrol . "+
                "?labelrol rdfs:label ?rol . "+
                "?s schema:ide_project ?id_project . "+
                "?s schema:tipoproyecto ?labeltipo ." +
                "?labeltipo rdfs:label ?tipo ."+
                "?project j.2:currentProject ?id. "+
                "?project j.2:lastName ?nombre. "+
                "?project j.2:firstName ?apellido. "+
                "?project j.2:mbox ?correo . "+
                "?project schema:areaPerson ?labelarea . "+
                "?labelarea rdfs:label ?area . "+
                "?project schema:id_person ?idpersona ."+
                " ?s j.2:title ?titulo . " +
                "} ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        TupleQueryResult result = null;
        try {
            result = tupleQuery.evaluate();
            int contador = 0;
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idPersona =
                        (SimpleLiteral)bindingSet.getValue("idpersona");
                SimpleLiteral nombre =
                        (SimpleLiteral)bindingSet.getValue("nombre");
                SimpleLiteral apellido =
                        (SimpleLiteral)bindingSet.getValue("apellido");
                SimpleLiteral rol =
                        (SimpleLiteral)bindingSet.getValue("rol");
                SimpleLiteral correo =
                        (SimpleLiteral)bindingSet.getValue("correo");
                SimpleLiteral area =
                        (SimpleLiteral)bindingSet.getValue("area");
                SimpleLiteral idProyecto =
                        (SimpleLiteral)bindingSet.getValue("id_project");
                SimpleLiteral titulo =
                        (SimpleLiteral)bindingSet.getValue("titulo");
                SimpleLiteral tipo =
                        (SimpleLiteral)bindingSet.getValue("tipo");
                String rolP =(rol.stringValue().equalsIgnoreCase("Participación"))?"participante":"director";
                String titlePersona = nombre.stringValue().concat(" ").concat(apellido.stringValue());
                String grupo = clasificacion(tipo.stringValue());
                String tituloreducido = "";
                int contador1 = 0;
                for (int i=0;i<titulo.stringValue().length();i++){
                    contador1++;
                    tituloreducido = tituloreducido.concat(titulo.stringValue().substring(i,i+1));
                    if(contador1>=25){
                        if(titulo.stringValue().substring(i,i+1).equals(" ")){
                            tituloreducido = tituloreducido.concat("\n");
                            contador1=0;
                        }

                    }
                }
                Nodo nodoPersona = new Nodo(idPersona.stringValue(),nombre.stringValue().concat("\n").concat(apellido.stringValue()),rolP,titlePersona);
                Nodo nodoProyecto = new Nodo(idProyecto.stringValue(),tituloreducido,grupo,titulo.stringValue());
                Relacion relacion = new Relacion(idPersona.stringValue(),idProyecto.stringValue());
                if(contador==0){
                    listNodos.add(nodoProyecto);
                }
                contador++;
                listNodos.add(nodoPersona);
                listRelacion.add(relacion);
            }
            NodoRelacion nr = new NodoRelacion(listNodos,listRelacion);
            nodoRelacion.add(nr);
            json = mapper.writeValueAsString(nodoRelacion.get(0));
            System.out.println(json);
        }
        catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    qee.getStackTrace().toString(), qee);
        } finally {
            result.close();
        }
        return json;
    }

    public String clasificacion(String tipo){
            String grupo = "";
        if(tipo.equalsIgnoreCase("Investigación")){
            grupo = "investigacion";
        }else if(tipo.equalsIgnoreCase("Innovación docente")){
            grupo = "investigacion_docente";
        }else if(tipo.equalsIgnoreCase("Extensión")){
            grupo = "extension";
        }else if(tipo.equalsIgnoreCase("Consultoría")){
            grupo = "consultoria";
        }else if(tipo.equalsIgnoreCase("Implementación")){
            grupo = "implementacion";
        }else if(tipo.equalsIgnoreCase("Innovación")){
            grupo = "innovacion";
        }else if(tipo.equalsIgnoreCase("Propuesta Enviada")){
            grupo = "propuesta";
        }else if(tipo.equalsIgnoreCase("Vinculación")){
            grupo = "investigacion";
        }
        return grupo;
    }

    public String TraduccionTitulo(String titulo) {
        String tituloreducido = "";
        int contador1 = 0;
        for (int i=0;i<titulo.length();i++){
            contador1++;
            tituloreducido = tituloreducido.concat(titulo.substring(i,i+1));
            if(contador1>=25){
                if(titulo.substring(i,i+1).equals(" ")){
                    tituloreducido = tituloreducido.concat("\n");
                    contador1=0;
                }

            }
        }
        return tituloreducido;
    }

    public String getGrapPerson(String xnombre) {
        ArrayList<Nodo> listNodos = new ArrayList<>();
        ArrayList<Relacion> listRelacion = new ArrayList<>();
        ArrayList<NodoRelacion> nodoRelacion = new ArrayList<>();
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX people: <http://utpl.edu.ec/data/people/> "+
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "SELECT ?id ?idpersona ?nombre ?apellido ?title ?rol ?correo ?area ?tipo " +
                "WHERE {"+
                "?s  schema:id_person '"+xnombre+"' . "+
                "?s  j.2:currentProject ?current . "+
                "?s j.2:lastName ?nombre . "+
                "?s j.2:firstName ?apellido . "+
                "?s j.2:mbox ?correo . "+
                "?s schema:id_person ?idpersona . " +
                "?current schema:idProject ?idproject . "+
                "?current schema:rolProyecto ?rolp . "+
                "?rolp rdfs:label ?rol . "+
                "?idproject j.2:title ?title . "+
                "?idproject schema:ide_project ?id . "+
                "?s schema:areaPerson ?labelarea . "+
                "?labelarea rdfs:label ?area . "+
                "?idproject schema:tipoproyecto ?labeltipo . "+
                "?labeltipo rdfs:label ?tipo . " +
                "} ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        TupleQueryResult result = null;
        try {
            result = tupleQuery.evaluate();
            int contador=0;
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idPersona =
                        (SimpleLiteral)bindingSet.getValue("idpersona");
                SimpleLiteral nombre =
                        (SimpleLiteral)bindingSet.getValue("nombre");
                SimpleLiteral apellido =
                        (SimpleLiteral)bindingSet.getValue("apellido");
                SimpleLiteral rol =
                        (SimpleLiteral)bindingSet.getValue("rol");
                SimpleLiteral correo =
                        (SimpleLiteral)bindingSet.getValue("correo");
                SimpleLiteral area =
                        (SimpleLiteral)bindingSet.getValue("area");
                SimpleLiteral idProyecto =
                        (SimpleLiteral)bindingSet.getValue("id");
                SimpleLiteral titulo =
                        (SimpleLiteral)bindingSet.getValue("title");
                SimpleLiteral tipo =
                        (SimpleLiteral)bindingSet.getValue("tipo");
                String titlePersona = nombre.stringValue().concat(" ").concat(apellido.stringValue());
                Nodo nodoPersona = new Nodo(idPersona.stringValue(),nombre.stringValue().concat("\n").concat(apellido.stringValue()),"participante",titlePersona);
                String grupo=clasificacion(tipo.stringValue());
                Nodo nodoProyecto = new Nodo(idProyecto.stringValue(),"",grupo,titulo.stringValue());
                Relacion relacion = new Relacion(idPersona.stringValue(),idProyecto.stringValue());
                if(contador==0){
                    listNodos.add(nodoPersona);
                }
                contador++;
                listNodos.add(nodoProyecto);
                listRelacion.add(relacion);
            }
            NodoRelacion nr = new NodoRelacion(listNodos,listRelacion);
            nodoRelacion.add(nr);
            json = mapper.writeValueAsString(nodoRelacion.get(0));
            System.out.println(json);
        }
        catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    qee.getStackTrace().toString(), qee);
        } finally {
            result.close();
        }
        return json;
    }

    public String getIDPerson(String nombre) {
        String json="";
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "SELECT ?idpersona " +
                "WHERE {"+
                "?s  schema:id_person '"+nombre+"' . "+
                "?s schema:id_person ?idpersona ."+
                "} ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        TupleQueryResult result = null;
        try {
            result = tupleQuery.evaluate();
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idPersona =
                        (SimpleLiteral) bindingSet.getValue("idpersona");
                json = idPersona.stringValue();
                System.out.println(json);
            }
        }
        catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    qee.getStackTrace().toString(), qee);
        } finally {
                result.close();
        }
        return json;
    }

   public String BusquedaPorNombre(String busqueda) {
        ArrayList<Objeto> listaPersona = new ArrayList<>();
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX j.2: <http://xmlns.com/foaf/0.1/> " +
                "PREFIX schema: <http://schema.org/> "+
                "SELECT ?idpersona ?nombre ?apellido " +
                "WHERE { "+
                "?s  j.2:firstName ?nombre . "+
                "?s  j.2:lastName ?apellido . "+
                "?s schema:id_person ?idpersona . "+
                "FILTER (regex(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE((UCASE(str(?nombre))),'Á', 'A','i'),'É', 'E','i'),'Í', 'I','i'),'Ó','O','i'),'Ú','U','i'),'"+
                busqueda+"','i')||regex(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE((UCASE(str(?apellido))),'Á', 'A','i'),'É', 'E','i'),'Í', 'I','i'),'Ó','O','i'),'Ú','U','i'),'"+
                busqueda+"','i')) } ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        TupleQueryResult result = null;
        try {
            result = tupleQuery.evaluate();
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idpersona =
                        (SimpleLiteral) bindingSet.getValue("idpersona");
                SimpleLiteral nombre =
                        (SimpleLiteral) bindingSet.getValue("nombre");
                SimpleLiteral apellido =
                        (SimpleLiteral) bindingSet.getValue("apellido");
                Objeto persona = new Objeto(idpersona.stringValue(),nombre.stringValue().concat(" ").concat(apellido.stringValue()).toUpperCase());
                listaPersona.add(persona);
            }
            json = mapper.writeValueAsString(listaPersona);
        }

        catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    qee.getStackTrace().toString(), qee);
        } finally {
            result.close();
        }
        return json;
    }
    public String BusquedaPorProyecto(String busqueda) {
        ArrayList<Objeto> listaPersona = new ArrayList<>();
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX j.2: <http://xmlns.com/foaf/0.1/> " +
                "PREFIX schema: <http://schema.org/> "+
                "SELECT ?idproyecto ?titulo " +
                "WHERE { "+
                "?s schema:ide_project ?idproyecto . "+
                "?s j.2:title ?titulo . "+
                "FILTER (regex(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE((UCASE(str(?titulo))),'Á', 'A','i'),'É', 'E','i'),'Í', 'I','i'),'Ó','O','i'),'Ú','U','i'),'"+
                busqueda+"','i')) }";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        TupleQueryResult result = null;
        try {
            result = tupleQuery.evaluate();
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idpersona =
                        (SimpleLiteral) bindingSet.getValue("idproyecto");
                SimpleLiteral titulo =
                        (SimpleLiteral) bindingSet.getValue("titulo");
                Objeto proyecto = new Objeto(idpersona.stringValue(),titulo.stringValue().toUpperCase());
                listaPersona.add(proyecto);
            }
            json = mapper.writeValueAsString(listaPersona);
        }

        catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    qee.getStackTrace().toString(), qee);
        } finally {
            result.close();
        }
        return json;
    }
}
