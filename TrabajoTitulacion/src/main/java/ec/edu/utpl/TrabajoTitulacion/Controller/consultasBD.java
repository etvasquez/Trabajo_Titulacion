package ec.edu.utpl.TrabajoTitulacion.Controller;

import ec.edu.utpl.TrabajoTitulacion.ConnectionDB.conectingGraphDB;
import ec.edu.utpl.TrabajoTitulacion.Model.Person;
import ec.edu.utpl.TrabajoTitulacion.Model.Project;
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
import java.util.HashSet;
import java.util.Set;

public class consultasBD {

    private static Logger logger = LoggerFactory.getLogger(conectingGraphDB.class);
    private static final Marker WTF_MARKER = MarkerFactory.getMarker("WTF");

    conectingGraphDB con = new conectingGraphDB();

    public void query(String var) {
        ArrayList datos = new ArrayList();
        ArrayList datos1 = new ArrayList();
        ArrayList datos2 = new ArrayList();
        // Create ObjectMapper object.
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                "SELECT ?rol ?nombre ?descripcion ?idpersona ?id_project"+
                "WHERE {"+
                "?s j.2:title '"+var+"' . "+
                "?s schema:ide_project ?id_project ."+
                "?id schema:idProject ?s ."+
                "?id schema:rolProyecto ?rollabel ."+
                "?rollabel rdfs:label ?rol ."+
                "?project j.2:currentProject ?id."+
                "?project j.2:lastName ?nombre."+
                "?project j.2:firstName ?apellido."+
                "?project j.2:mbox ?email."+
                "?project schema:id_person ?idpersona ."+
                "?s schema:descripcion ?descripcion."+
                "} ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        TupleQueryResult result = null;
        Person p = new Person();
        Project projects = new Project();
        Set<Project> project = new HashSet<>();
        try {
            result = tupleQuery.evaluate();
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral id =
                        (SimpleLiteral)bindingSet.getValue("idpersona");
                SimpleLiteral nombre =
                        (SimpleLiteral)bindingSet.getValue("nombre");
                SimpleLiteral id_project =
                        (SimpleLiteral)bindingSet.getValue("id_project");
                SimpleLiteral rol =
                        (SimpleLiteral)bindingSet.getValue("rol");
                p.setIdentificador(id.stringValue());
                p.setNombres(nombre.stringValue());
                /*
                projects.setIdentificador(id_project.stringValue());
                projects.setRol_proyecto(rol.stringValue());
                project.add(projects);
                p.setProject(project);*/
                //logger.trace("idpersona = " + name.stringValue());
            }
            String json = mapper.writeValueAsString(p);
            System.out.println(json);
        }
        catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    qee.getStackTrace().toString(), qee);
        } finally {
            result.close();
        }
    }
}
