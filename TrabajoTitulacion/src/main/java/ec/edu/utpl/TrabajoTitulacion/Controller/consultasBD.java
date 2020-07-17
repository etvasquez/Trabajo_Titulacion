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

    public String getGrapg() {
        ArrayList<Nodo> listNodos = new ArrayList<>();
        ArrayList<Relacion> listRelacion = new ArrayList<>();
        ArrayList<NodoRelacion> nodoRelacion = new ArrayList<>();
        String json="";
        boolean contiene = false;
        // Create ObjectMapper object.
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        /*
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                "SELECT ?idpersona ?nombre ?apellido ?email ?extension ?genero ?miembro ?telefono " +
                "?estado ?area ?departamento ?seccion ?ocupacion ?nacionalidad ?id_project ?rol ?descripcion " +
                "?inicio_participacion ?fin_participacion ?area_conocimiento ?avance ?cobertura ?codigo_proyecto " +
                "?estado_validacion ?fecha_inicio ?fecha_fin ?incluye_estudiantes ?incluye_financiamiento_externo " +
                "?linea_investigacion ?moneda ?objetivos ?programa ?smartland ?tipo_convocatoria ?titulo ?tipo_proyecto ?tipo_participante"+
                "WHERE {"+
                "?s j.2:title '"+var+"' . "+
                "?id schema:idProject ?s ."+
                "?id schema:rolProyecto ?rollabel . ?s schema:ide_project ?id_project ."+
                "?rollabel rdfs:label ?rol . ?project j.2:currentProject ?id."+
                "?project j.2:lastName ?nombre. ?project j.2:firstName ?apellido."+
                "?project j.2:mbox ?email. ?project schema:extension ?extension."+
                "?project j.2:gender ?genero. ?project j.2:member ?miembro."+
                "?project j.2:phone ?telefono . ?project j.2:status ?estado ."+
                "?project schema:areaPerson ?arealabel. ?arealabel rdfs:label ?area ."+
                "?project schema:depertamentPerson ?departamentolabel . ?departamentolabel rdfs:label ?departamento ."+
                "?project schema:seccionPerson ?seccionlabel . ?seccionlabel rdfs:label ?seccion ."+
                "?project schema:tipoOcupationPerson ?ocupacaionlabel. ?ocupacaionlabel rdfs:label ?ocupacion ."+
                "?project schema:national ?nacionalidadlabel . ?nacionalidadlabel rdfs:label ?nacionalidad ."+
                "?project schema:id_person ?idpersona . ?s schema:descripcion ?descripcion."+
                "?id schema:fecha_inicio_participante ?inicio_participacion . ?id schema:fecha_fin_participante ?fin_participacion . "+
                "?s schema:area_conocimiento ?area_conocimiento . ?s schema:avance ?avance . ?s schema:cobertura ?cobertura ."+
                "?s schema:codigo_proyecto ?codigo_proyecto . ?s schema:estado_validacion ?estado_validacion .  ?s schema:fecha_inicio ?fecha_inicio ."+
                "?s schema:fecha_fin ?fecha_fin . ?s schema:incluye_estudiantes ?incluye_estudiantes . ?s schema:incluye_financiamiento_externo ?incluye_financiamiento_externo ."+
                "?s schema:linea_investigacion ?linea_investigacion . ?s schema:moneda ?moneda . ?s schema:objetivos ?objetivos ."+
                "?s schema:programa ?programa . ?s schema:smartland ?smartland . ?s schema:tipo_convocatoria ?tipo_convocatoria ."+
                "?s j.2:title ?titulo . ?s schema:tipoproyecto ?tipo_proyecto_label. ?tipo_proyecto_label rdfs:label ?tipo_proyecto ."+
                "?s schema:tipoparticipante ?tipo_participante_label. ?tipo_participante_label rdfs:label ?tipo_participante ."+
                "} ";*/
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                "SELECT ?idpersona ?nombre ?apellido ?id_project ?titulo ?rol ?correo ?area ?tipo " +
                "WHERE {"+
                "?s j.2:title 'IDEL 2008: Primer Taller Presencial de Implementación y desarrollo E-Learning' . "+
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
                String titlePersona = nombre.stringValue().concat(" ").concat(apellido.stringValue()).concat("\n")
                        .concat(correo.stringValue()).concat("\n").concat(area.stringValue());
                String titleProyecto = titulo.stringValue().concat("\n").concat(tipo.stringValue());
                Nodo nodoPersona = new Nodo(idPersona.stringValue(),nombre.stringValue().concat(" ").concat(apellido.stringValue()),rolP,titlePersona);
                Nodo nodoProyecto = new Nodo(idProyecto.stringValue(),titulo.stringValue(),"projects",titleProyecto);
                Relacion relacion = new Relacion(idPersona.stringValue(),idProyecto.stringValue());
                listNodos.add(nodoPersona);
                listNodos.set(0,nodoProyecto);
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

}
