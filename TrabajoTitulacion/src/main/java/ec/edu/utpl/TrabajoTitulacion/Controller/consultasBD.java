package ec.edu.utpl.TrabajoTitulacion.Controller;

import ec.edu.utpl.TrabajoTitulacion.ConnectionDB.conectingGraphDB;
import org.eclipse.rdf4j.model.impl.SimpleLiteral;
import org.eclipse.rdf4j.query.*;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class consultasBD {

    private static Logger logger = LoggerFactory.getLogger(conectingGraphDB.class);
    private static final Marker WTF_MARKER = MarkerFactory.getMarker("WTF");

    conectingGraphDB con = new conectingGraphDB();

    public void query(String var) {
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
        try {
            result = tupleQuery.evaluate();
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral name =
                        (SimpleLiteral)bindingSet.getValue("idpersona");
                logger.trace("idpersona = " + name.stringValue());
                System.out.println(result);
            }
        }
        catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    qee.getStackTrace().toString(), qee);
        } finally {
            result.close();
        }
    }
}
