package ec.edu.utpl.TrabajoTitulacion.ConnectionDB;

import org.eclipse.rdf4j.model.impl.SimpleLiteral;
import org.eclipse.rdf4j.query.*;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.http.HTTPRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class conectingGraphDB {
    private static Logger logger =
            LoggerFactory.getLogger(conectingGraphDB.class);
    // Why This Failure marker
    private static final Marker WTF_MARKER =
            MarkerFactory.getMarker("WTF");
    // GraphDB
    private static final String GRAPHDB_SERVER =
            "http://localhost:7200/";

    private static final String REPOSITORY_ID = "OntologyProjects";
    //private static final String REPOSITORY_ID = "Test";

    public RepositoryConnection getRepositoryConnection() {
        Repository repository = new HTTPRepository(
                GRAPHDB_SERVER, REPOSITORY_ID);
        repository.initialize();
        RepositoryConnection repositoryConnection =
                repository.getConnection();
        return repositoryConnection;
    }
}
