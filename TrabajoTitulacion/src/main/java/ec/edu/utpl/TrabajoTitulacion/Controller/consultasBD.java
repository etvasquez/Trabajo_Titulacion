package ec.edu.utpl.TrabajoTitulacion.Controller;

import com.google.gson.*;
import com.owlike.genson.Genson;
import ec.edu.utpl.TrabajoTitulacion.ConnectionDB.conectingGraphDB;
import ec.edu.utpl.TrabajoTitulacion.Model.*;
import org.eclipse.rdf4j.model.impl.SimpleLiteral;
import org.eclipse.rdf4j.query.*;
import org.eclipse.rdf4j.repository.Repository;
import org.eclipse.rdf4j.repository.RepositoryConnection;
import org.eclipse.rdf4j.repository.RepositoryException;
import org.eclipse.rdf4j.rio.RDFParseException;
import org.eclipse.rdf4j.rio.Rio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.*;

@SuppressWarnings("EqualsBetweenInconvertibleTypes")
public class consultasBD{

    private static final String urlBase = "<http://utpl.edu.ec/data/";
    private static final Logger logger = LoggerFactory.getLogger(conectingGraphDB.class);
    private static final Marker WTF_MARKER = MarkerFactory.getMarker("WTF");
    private final conectingGraphDB con = new conectingGraphDB();

    public void loadDataSet()
            throws RDFParseException, RepositoryException, IOException {
        RepositoryConnection repositoryConnection;
        repositoryConnection = con.getRepositoryConnection();
        Repository repository = repositoryConnection.getRepository();
        logger.debug("loading dataset...");
        File dataset = new File("/Users/eriiv/OneDrive/Escritorio/TT/V2/Implementacion/v1/repositorio-rdf.rdf");
        String datasetFile = "/Users/eriiv/OneDrive/Escritorio/TT/V2/Implementacion/v1/repositorio-rdf.rdf";
        try (RepositoryConnection con = repository.getConnection()) {
            con.add(dataset, "http://utpl.edu.ec/data/", Rio.getParserFormatForFileName(datasetFile).
                    orElseThrow(Rio.unsupportedFormat(datasetFile)));
        }
        logger.debug("dataset loaded.");
    }
/*
    public String insertNewLike(String idProject){
        int numeroActualLike = getCountLike(idProject) + 1;
        String strInsert =
                "INSERT DATA { " +
                        urlBase + "commentProject/" + idProject + "> schema:likeCount " + numeroActualLike + " . }";

        RepositoryConnection repositoryConnection = con.getRepositoryConnection();
        repositoryConnection.begin();
        Update updateOperation = repositoryConnection.prepareUpdate(QueryLanguage.SPARQL, strInsert);
        updateOperation.execute();
        try {
            repositoryConnection.commit();
        } catch (Exception e) {
            if (repositoryConnection.isActive())
                repositoryConnection.rollback();
        }
        return "";
    }
*/
    public Usuario getUsuario(String identificador, boolean bandera){
        String strQuery;
        Usuario usuario = new Usuario();
        if(bandera){
            strQuery =
                    "PREFIX schema: <http://schema.org/> "+
                            "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                            "SELECT ?id ?nombre ?apellido ?area ?departamento ?seccion ?modalidad ?tipo ?status ?nacionalidad ?extension ?telefono ?email ?image "+
                            "WHERE { "+
                            "?s schema:id_person ?id . "+
                            "?s j.2:lastName ?nombre . "+
                            "?s j.2:firstName ?apellido . "+
                            "?s j.2:img ?image . "+
                            "?s schema:areaPerson ?arealabel . "+
                            "?arealabel rdfs:label ?area . "+
                            "?s schema:depertamentPerson ?deplabel . "+
                            "?deplabel rdfs:label ?departamento . "+
                            "?s schema:seccionPerson ?seccionlabel . "+
                            "?seccionlabel rdfs:label ?seccion . "+
                            "?s schema:modalidadPerson ?modalabel . "+
                            "?modalabel rdfs:label ?modalidad . "+
                            "?s schema:tipoOcupationPerson ?oculabel . "+
                            "?oculabel rdfs:label ?tipo . "+
                            "?s schema:statusPerson ?statuslabel . "+
                            "?statuslabel rdfs:label ?status . "+
                            "?s schema:national ?nationalabel . "+
                            "?nationalabel rdfs:label ?nacionalidad . "+
                            "?s schema:extension ?extension . "+
                            "?s j.2:phone ?telefono . ?s j.2:mbox ?email . "+
                            "filter(regex(?email, '"+identificador+"','i')) }";
            //identificador = identificador.concat("@utpl.edu.ec");
         }else{
            strQuery =
                    "PREFIX schema: <http://schema.org/> "+
                            "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                            "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                            "SELECT ?id ?nombre ?apellido ?area ?departamento ?seccion ?modalidad ?tipo ?status ?nacionalidad ?extension ?telefono ?email ?image "+
                            "WHERE { "+
                            "?s schema:id_person ?id . "+
                            "?s j.2:lastName ?nombre . "+
                            "?s j.2:firstName ?apellido . "+
                            "?s j.2:img ?image . "+
                            "?s schema:areaPerson ?arealabel . "+
                            "?arealabel rdfs:label ?area . "+
                            "?s schema:depertamentPerson ?deplabel . "+
                            "?deplabel rdfs:label ?departamento . "+
                            "?s schema:seccionPerson ?seccionlabel . "+
                            "?seccionlabel rdfs:label ?seccion . "+
                            "?s schema:modalidadPerson ?modalabel . "+
                            "?modalabel rdfs:label ?modalidad . "+
                            "?s schema:tipoOcupationPerson ?oculabel . "+
                            "?oculabel rdfs:label ?tipo . "+
                            "?s schema:statusPerson ?statuslabel . "+
                            "?statuslabel rdfs:label ?status . "+
                            "?s schema:national ?nationalabel . "+
                            "?nationalabel rdfs:label ?nacionalidad . "+
                            "?s schema:extension ?extension . "+
                            "?s j.2:phone ?telefono . ?s j.2:mbox ?email . "+
                            "filter(regex(?id, '"+identificador+"','i')) }";
        }
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral id =
                        (SimpleLiteral) bindingSet.getValue("id");
                SimpleLiteral nombre =
                        (SimpleLiteral) bindingSet.getValue("nombre");
                SimpleLiteral apellido =
                        (SimpleLiteral) bindingSet.getValue("apellido");
                SimpleLiteral area =
                        (SimpleLiteral) bindingSet.getValue("area");
                SimpleLiteral departamento =
                        (SimpleLiteral) bindingSet.getValue("departamento");
                SimpleLiteral seccion =
                        (SimpleLiteral) bindingSet.getValue("seccion");
                SimpleLiteral modalidad =
                        (SimpleLiteral) bindingSet.getValue("modalidad");
                SimpleLiteral tipo =
                        (SimpleLiteral) bindingSet.getValue("tipo");
                SimpleLiteral status =
                        (SimpleLiteral) bindingSet.getValue("status");
                SimpleLiteral nacionalidad =
                        (SimpleLiteral) bindingSet.getValue("nacionalidad");
                SimpleLiteral extension =
                        (SimpleLiteral) bindingSet.getValue("extension");
                SimpleLiteral telefono =
                        (SimpleLiteral) bindingSet.getValue("telefono");
                SimpleLiteral email =
                        (SimpleLiteral) bindingSet.getValue("email");
                SimpleLiteral image =
                        (SimpleLiteral) bindingSet.getValue("image");
                String imagen = image.stringValue();
                if (imagen.equals("n") || image.equals("")) {
                    imagen = "https://image.ibb.co/jw55Ex/def_face.jpg";
                }
                usuario = new Usuario(id.stringValue(), nombre.stringValue().concat(" ").concat(apellido.stringValue()), area.stringValue(),
                        departamento.stringValue(), seccion.stringValue(), modalidad.stringValue(), tipo.stringValue(), status.stringValue(),
                        nacionalidad.stringValue(), extension.stringValue(), telefono.stringValue(), email.stringValue(), imagen);
            }
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return usuario;
    }

    public Usuario getUser(String xemail){
        Usuario usuario = new Usuario();
        String strQuery =
                "PREFIX schema: <http://schema.org/> "+
                        "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                        "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                        "SELECT ?id ?nombre ?apellido ?area ?departamento ?seccion ?modalidad ?tipo ?status ?nacionalidad ?extension ?telefono ?email ?image "+
                        "WHERE { "+
                        "?s schema:id_person ?id . "+
                        "?s j.2:lastName ?nombre . "+
                        "?s j.2:firstName ?apellido . "+
                        "?s j.2:img ?image . "+
                        "?s schema:areaPerson ?arealabel . "+
                        "?arealabel rdfs:label ?area . "+
                        "?s schema:depertamentPerson ?deplabel . "+
                        "?deplabel rdfs:label ?departamento . "+
                        "?s schema:seccionPerson ?seccionlabel . "+
                        "?seccionlabel rdfs:label ?seccion . "+
                        "?s schema:modalidadPerson ?modalabel . "+
                        "?modalabel rdfs:label ?modalidad . "+
                        "?s schema:tipoOcupationPerson ?oculabel . "+
                        "?oculabel rdfs:label ?tipo . "+
                        "?s schema:statusPerson ?statuslabel . "+
                        "?statuslabel rdfs:label ?status . "+
                        "?s schema:national ?nationalabel . "+
                        "?nationalabel rdfs:label ?nacionalidad . "+
                        "?s schema:extension ?extension . "+
                        "?s j.2:phone ?telefono . ?s j.2:mbox ?email . "+
                        "filter(regex(?email, '"+xemail+"','i')) }";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral id =
                        (SimpleLiteral) bindingSet.getValue("id");
                SimpleLiteral nombre =
                        (SimpleLiteral) bindingSet.getValue("nombre");
                SimpleLiteral apellido =
                        (SimpleLiteral) bindingSet.getValue("apellido");
                SimpleLiteral area =
                        (SimpleLiteral) bindingSet.getValue("area");
                SimpleLiteral departamento =
                        (SimpleLiteral) bindingSet.getValue("departamento");
                SimpleLiteral seccion =
                        (SimpleLiteral) bindingSet.getValue("seccion");
                SimpleLiteral modalidad =
                        (SimpleLiteral) bindingSet.getValue("modalidad");
                SimpleLiteral tipo =
                        (SimpleLiteral) bindingSet.getValue("tipo");
                SimpleLiteral status =
                        (SimpleLiteral) bindingSet.getValue("status");
                SimpleLiteral nacionalidad =
                        (SimpleLiteral) bindingSet.getValue("nacionalidad");
                SimpleLiteral extension =
                        (SimpleLiteral) bindingSet.getValue("extension");
                SimpleLiteral telefono =
                        (SimpleLiteral) bindingSet.getValue("telefono");
                SimpleLiteral email =
                        (SimpleLiteral) bindingSet.getValue("email");
                SimpleLiteral image =
                        (SimpleLiteral) bindingSet.getValue("image");
                String imagen = image.stringValue();
                if (imagen.equals("n") || image.equals("")) {
                    imagen = "https://image.ibb.co/jw55Ex/def_face.jpg";
                }
                usuario = new Usuario(id.stringValue(), nombre.stringValue().concat(" ").concat(apellido.stringValue()), area.stringValue(),
                        departamento.stringValue(), seccion.stringValue(), modalidad.stringValue(), tipo.stringValue(), status.stringValue(),
                        nacionalidad.stringValue(), extension.stringValue(), telefono.stringValue(), email.stringValue(), imagen);
            }
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return usuario;
    }

    private int getCountLike(String idProject){
        int contador=0;
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "SELECT ?total " +
                "WHERE {"+
                "?s schema:ide_project '"+idProject+"' . "+
                "?s schema:Comment ?tipo . "+
                "?tipo schema:likeCount ?total } ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral total =
                        (SimpleLiteral) bindingSet.getValue("total");
                contador = total.intValue();

            }
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return contador;
    }

    public Recurso getResouce(String id){
        Recurso recurso = null;
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "SELECT ?id ?descripcion ?licencia ?nombrecompleto ?nombrereal " +
                "WHERE { "+
                "?idpro schema:Resource "+urlBase+"resourceProject/"+id+"> . "+
                urlBase+"resourceProject/"+id+"> schema:descripcion ?descripcion . "+
                urlBase+"resourceProject/"+id+"> schema:licencia ?licencia . "+
                urlBase+"resourceProject/"+id+"> schema:nombrecompleto ?nombrecompleto . "+
                urlBase+"resourceProject/"+id+"> schema:nombrereal ?nombrereal . "+
                "?idpro schema:ide_project ?id . } ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idrecurso =
                        (SimpleLiteral) bindingSet.getValue("id");
                SimpleLiteral descripcion =
                        (SimpleLiteral) bindingSet.getValue("descripcion");
                SimpleLiteral licencia =
                        (SimpleLiteral) bindingSet.getValue("licencia");
                SimpleLiteral nombrecompleto =
                        (SimpleLiteral) bindingSet.getValue("nombrecompleto");
                SimpleLiteral nombrereal =
                        (SimpleLiteral) bindingSet.getValue("nombrereal");
                recurso = new Recurso(idrecurso.stringValue(), nombrecompleto.stringValue(), nombrereal.stringValue(), descripcion.stringValue(), licencia.stringValue());
            }
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return recurso;
    }

    public ArrayList<Recurso> getResouceProject(String idProject){
        ArrayList<Recurso> listRecource = new ArrayList<>();
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "SELECT ?idrecurso ?descripcion ?licencia ?nombrecompleto ?nombrereal " +
                "WHERE { "+
                urlBase+"project/"+idProject+"> schema:Resource ?id . "+
                "?id schema:idRecurso ?idrecurso . "+
                "?id schema:descripcion ?descripcion . "+
                "?id schema:licencia ?licencia . "+
                "?id schema:nombrecompleto ?nombrecompleto . "+
                "?id schema:nombrereal ?nombrereal . } ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idrecurso =
                        (SimpleLiteral) bindingSet.getValue("idrecurso");
                SimpleLiteral descripcion =
                        (SimpleLiteral) bindingSet.getValue("descripcion");
                SimpleLiteral licencia =
                        (SimpleLiteral) bindingSet.getValue("licencia");
                SimpleLiteral nombrecompleto =
                        (SimpleLiteral) bindingSet.getValue("nombrecompleto");
                SimpleLiteral nombrereal =
                        (SimpleLiteral) bindingSet.getValue("nombrereal");
                Recurso recurso = new Recurso(idrecurso.stringValue(), nombrecompleto.stringValue(), nombrereal.stringValue(), descripcion.stringValue(), licencia.stringValue());
                listRecource.add(recurso);
            }
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return listRecource;
    }

    public String getNameByEmail(String email){
        String name="";
        String strQuery ="PREFIX j.2: <http://xmlns.com/foaf/0.1/> " +
                "SELECT DISTINCT ?name " +
                "WHERE { "+
                urlBase+"people/"+email +"> j.2:lastName ?name }";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral nombre =
                        (SimpleLiteral) bindingSet.getValue("name");
                name = nombre.stringValue();

            }
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return name;
    }
    public ArrayList<Administracion> getAdministracion(){
        ArrayList<Administracion> listAdminsitracion = new ArrayList<>();
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "SELECT ?dominio ?puerto ?nombreDB ?user ?date " +
                "WHERE { "+
                urlBase+"upgrade> schema:Upgrade ?id ." +
                "?id schema:dominio ?dominio . "+
                "?id schema:puerto ?puerto . "+
                "?id schema:nombreDB ?nombreDB . "+
                "?id schema:user ?user . "+
                "?id schema:date ?date . } ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral dominio =
                        (SimpleLiteral) bindingSet.getValue("dominio");
                SimpleLiteral puerto =
                        (SimpleLiteral) bindingSet.getValue("puerto");
                SimpleLiteral nombreDB =
                        (SimpleLiteral) bindingSet.getValue("nombreDB");
                SimpleLiteral user =
                        (SimpleLiteral) bindingSet.getValue("user");
                SimpleLiteral date =
                        (SimpleLiteral) bindingSet.getValue("date");
                Administracion administracion = new Administracion(dominio.stringValue(), puerto.stringValue(), nombreDB.stringValue(), user.stringValue(), date.stringValue());
                listAdminsitracion.add(administracion);
            }
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return listAdminsitracion;
    }
    public ArrayList<Proyecto> getProject(String correo){
        ArrayList<Proyecto> listaProyectos = new ArrayList<>();
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                "SELECT ?id ?titulo ?rol ?tipo " +
                "WHERE { "+
                "?s j.2:currentProject ?p . "+
                "?p schema:idProject ?idp . "+
                "?p schema:rolProyecto ?rolabel . "+
                "?rolabel rdfs:label ?rol . "+
                "?idp j.2:title ?titulo . "+
                "?idp schema:ide_project ?id . "+
                "?idp schema:tipoproyecto ?tipolabel . " +
                "?tipolabel rdfs:label ?tipo ."+
                "?s j.2:mbox ?m ."+
                "filter(regex(?m, '"+correo+"','i')) } ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral id =
                        (SimpleLiteral) bindingSet.getValue("id");
                SimpleLiteral titulo =
                        (SimpleLiteral) bindingSet.getValue("titulo");
                SimpleLiteral rol =
                        (SimpleLiteral) bindingSet.getValue("rol");
                SimpleLiteral tipo =
                        (SimpleLiteral) bindingSet.getValue("tipo");
                Proyecto proyecto = new Proyecto(id.stringValue(), titulo.stringValue(), tipo.stringValue(), rol.stringValue());
                listaProyectos.add(proyecto);
            }
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }

        return listaProyectos;
    }

    public ArrayList<ComentarioGlobal> getCommentGloblal(ArrayList<Comentario> listaComentarios){
        ArrayList<ComentarioGlobal> listaComentarioGlobal = new ArrayList<>();
        for (Comentario comentario: listaComentarios){
            ArrayList<Comentario> listaComentario = new ArrayList<>();
            String strQuery ="PREFIX schema: <http://schema.org/> " +
                    "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                    "SELECT ?com ?date ?nombre ?correo ?uuid " +
                    "WHERE { "+
                    urlBase+"commentProject/"+comentario.getIdCom()+"> schema:Comment ?tipo ."+
                    "?tipo schema:uuid ?uuid . "+
                    "?tipo schema:comment ?com . "+
                    "?tipo schema:dateCreated ?date ."+
                    "?tipo schema:character ?autor . "+
                    "?autor j.2:lastName ?nombre . "+
                    "?autor j.2:mbox ?correo . } ";
            TupleQuery tupleQuery = con.getRepositoryConnection()
                    .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
            try (TupleQueryResult result = tupleQuery.evaluate()) {
                while (result.hasNext()) {
                    BindingSet bindingSet = result.next();
                    SimpleLiteral com =
                            (SimpleLiteral) bindingSet.getValue("com");
                    SimpleLiteral date =
                            (SimpleLiteral) bindingSet.getValue("date");
                    SimpleLiteral nombre =
                            (SimpleLiteral) bindingSet.getValue("nombre");
                    SimpleLiteral correo =
                            (SimpleLiteral) bindingSet.getValue("correo");
                    SimpleLiteral uuid =
                            (SimpleLiteral) bindingSet.getValue("uuid");
                    Comentario comentario1 = new Comentario(uuid.stringValue(), nombre.stringValue(), date.stringValue(), com.stringValue(), correo.stringValue());
                    listaComentario.add(comentario1);
                }
                ComentarioGlobal comentarioGlobal = new ComentarioGlobal(comentario, listaComentario);
                listaComentarioGlobal.add(comentarioGlobal);
            } catch (QueryEvaluationException qee) {
                logger.error(WTF_MARKER,
                        Arrays.toString(qee.getStackTrace()), qee);
            }
        }
        return listaComentarioGlobal;
    }

    public ArrayList<Comentario> getComment(String idProject){
        ArrayList<Comentario> listComentarios = new ArrayList<>();
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "SELECT ?com ?date ?nombre ?correo ?uuid " +
                "WHERE { "+
                "?s schema:ide_project '"+idProject+"' . "+
                "?s schema:Comment ?tipo . "+
                "?tipo schema:uuid ?uuid . "+
                "?tipo schema:comment ?com . "+
                "?tipo schema:dateCreated ?date ."+
                "?tipo schema:character ?autor . "+
                "?autor j.2:lastName ?nombre . "+
                "?autor j.2:mbox ?correo . } ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral com =
                        (SimpleLiteral) bindingSet.getValue("com");
                SimpleLiteral date =
                        (SimpleLiteral) bindingSet.getValue("date");
                SimpleLiteral nombre =
                        (SimpleLiteral) bindingSet.getValue("nombre");
                SimpleLiteral correo =
                        (SimpleLiteral) bindingSet.getValue("correo");
                SimpleLiteral uuid =
                        (SimpleLiteral) bindingSet.getValue("uuid");
                Comentario comentario = new Comentario(uuid.stringValue(), nombre.stringValue(), date.stringValue(), com.stringValue(), correo.stringValue());
                listComentarios.add(comentario);
            }
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return listComentarios;
    }

    private String getDate(){
        SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        return formatter.format(date);
    }

    public String insertResource(Recurso recurso, String id, String fileName) {
        String strInsert;
        String estado="1";
        strInsert =
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                        "PREFIX schema: <http://schema.org/> "+
                        "INSERT DATA { " +
                        urlBase + "project/" + id + "> schema:Resource " + urlBase + "resourceProject/" + fileName + "> . " +
                        urlBase + "resourceProject/" + fileName + "> schema:idRecurso \"" + fileName + "\" . " +
                        urlBase + "resourceProject/" + fileName + "> schema:nombrereal \"" + recurso.getNombrereal() + "\" . " +
                        urlBase + "resourceProject/" + fileName + "> schema:nombrecompleto \"" + recurso.getNombrecompleto()+ "\" . " +
                        urlBase + "resourceProject/" + fileName + "> schema:descripcion \"" + recurso.getDescripcion()+ "\" . " +
                        urlBase + "resourceProject/" + fileName + "> schema:licencia \"" + recurso.getTipo()+ "\" . } ";
        RepositoryConnection repositoryConnection = con.getRepositoryConnection();
        repositoryConnection.begin();
        Update updateOperation = repositoryConnection.prepareUpdate(QueryLanguage.SPARQL, strInsert);
        updateOperation.execute();
        try {
            repositoryConnection.commit();
        } catch (Exception e) {
            estado="error";
            if (repositoryConnection.isActive())
                repositoryConnection.rollback();
        }
        return estado;
    }

    public void insertActualizacion(Administracion administracion, String id) {
        String estado="1";
        String strInsert = "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "PREFIX schema: <http://schema.org/> "+
                "INSERT DATA { " +
                urlBase + "upgrade> schema:Upgrade " + urlBase + "upgradeRepository/" + id + "> . " +
                urlBase + "upgradeRepository/" + id + "> schema:dominio \"" + administracion.getDominio() + "\" . " +
                urlBase + "upgradeRepository/" + id + "> schema:puerto \"" + administracion.getPuerto() + "\" . " +
                urlBase + "upgradeRepository/" + id + "> schema:nombreDB \"" + administracion.getNombreBD()+ "\" . " +
                urlBase + "upgradeRepository/" + id + "> schema:user \"" + administracion.getUser()+ "\" . " +
                urlBase + "upgradeRepository/" + id + "> schema:date \"" + administracion.getPassword()+ "\" . } ";
        RepositoryConnection repositoryConnection = con.getRepositoryConnection();
        repositoryConnection.begin();
        Update updateOperation = repositoryConnection.prepareUpdate(QueryLanguage.SPARQL, strInsert);
        updateOperation.execute();
        try {
            repositoryConnection.commit();
        } catch (Exception e) {
            estado="error";
            if (repositoryConnection.isActive())
                repositoryConnection.rollback();
        }
    }

    public String eliminarResource(Recurso recurso, String id) {
        String strInsert;
        String estado="1";
        strInsert =
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                        "PREFIX schema: <http://schema.org/> "+
                        "DELETE DATA { " +
                        urlBase + "project/" + recurso.getId() + "> schema:Resource " + urlBase + "resourceProject/" + id + "> . " +
                        urlBase + "resourceProject/" + id + "> schema:idRecurso \"" + id + "\" . " +
                        urlBase + "resourceProject/" + id + "> schema:nombrereal \"" + recurso.getNombrereal() + "\" . " +
                        urlBase + "resourceProject/" + id + "> schema:nombrecompleto \"" + recurso.getNombrecompleto()+ "\" . " +
                        urlBase + "resourceProject/" + id + "> schema:descripcion \"" + recurso.getDescripcion()+ "\" . " +
                        urlBase + "resourceProject/" + id + "> schema:licencia \"" + recurso.getTipo()+ "\" . } ";
        RepositoryConnection repositoryConnection = con.getRepositoryConnection();
        repositoryConnection.begin();
        Update updateOperation = repositoryConnection.prepareUpdate(QueryLanguage.SPARQL, strInsert);
        updateOperation.execute();
        try {
            repositoryConnection.commit();
        } catch (Exception e) {
            if (repositoryConnection.isActive())
                repositoryConnection.rollback();
        }
        return recurso.getId();
    }

    public String insertComent(Comentario comentario) {
        String uuid = UUID.randomUUID().toString();
        String strInsert;
        String estado="Exito";
        if(getPersonByEmail(comentario.getCorreo()).equals(comentario.getCorreo())){
                strInsert =
                        "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                                "PREFIX schema: <http://schema.org/> "+
                                "DELETE { "+
                                urlBase+"people/"+comentario.getCorreo()+"> j.2:lastName ?oldname . }"+
                        "INSERT { " +
                                urlBase + "project/" + comentario.getIdCom() + "> schema:Comment " + urlBase + "commentProject/" + uuid + "> . " +
                                urlBase + "commentProject/" + uuid + "> schema:uuid \"" + uuid + "\" . " +
                                urlBase + "commentProject/" + uuid + "> schema:comment \"" + comentario.getComentarios() + "\" . " +
                                urlBase + "commentProject/" + uuid + "> schema:dateCreated \"" + getDate() + "\" . " +
                                urlBase + "commentProject/" + uuid+ "> schema:character " + urlBase + "people/" + comentario.getCorreo() + "> . " +
                                urlBase + "people/" + comentario.getCorreo() + "> j.2:lastName \"" + comentario.getNombre() + "\" . " +
                                "} WHERE { " +
                                    urlBase+"people/"+comentario.getCorreo()+"> j.2:lastName ?oldname . }";
        }else{
                strInsert =
                        "INSERT DATA { " +
                                urlBase + "project/" + comentario.getIdCom() + "> schema:Comment " + urlBase + "commentProject/" + uuid + "> . " +
                                urlBase + "commentProject/" + uuid + "> schema:uuid \"" + uuid + "\" . " +
                                urlBase + "commentProject/" + uuid + "> schema:comment \"" + comentario.getComentarios() + "\" . " +
                                urlBase + "commentProject/" + uuid + "> schema:dateCreated \"" + getDate() + "\" . " +
                                urlBase + "commentProject/" + uuid+ "> schema:character " + urlBase + "people/" + comentario.getCorreo() + "> . " +
                                urlBase + "people/" + comentario.getCorreo() + "> foaf:lastName \"" + comentario.getNombre() + "\" . " +
                                urlBase + "people/" + comentario.getCorreo() + "> foaf:mbox \"" + comentario.getCorreo() + "\" . " +
                                "}";

        }
        RepositoryConnection repositoryConnection = con.getRepositoryConnection();
        repositoryConnection.begin();
        Update updateOperation = repositoryConnection.prepareUpdate(QueryLanguage.SPARQL, strInsert);
        updateOperation.execute();
        try {
            repositoryConnection.commit();
        } catch (Exception e) {
            estado="error";
            if (repositoryConnection.isActive())
                repositoryConnection.rollback();
        }
        return estado;
    }

    public String updateProject(Proyecto proyecto) {
        String strInsert;
        String estado="0";
        String consulta = urlBase+"project/"+proyecto.getId()+"> j.2:title ?titleold . "+
                urlBase+"project/"+proyecto.getId()+"> schema:descripcion ?desold . "+
                urlBase+"project/"+proyecto.getId()+"> schema:objetivos ?objold . "+
                urlBase+"project/"+proyecto.getId()+"> schema:tipoproyecto ?tipo . "+
                urlBase+"project/"+proyecto.getId()+"> schema:estadoproyecto ?estado . "+
                urlBase+"project/"+proyecto.getId()+"> schema:programa ?programa . "+
                urlBase+"project/"+proyecto.getId()+"> schema:cobertura ?cobertura . "+
                urlBase+"project/"+proyecto.getId()+"> schema:smartland ?smartland . "+
                urlBase+"project/"+proyecto.getId()+"> schema:fecha_inicio ?fechainicio . "+
                urlBase+"project/"+proyecto.getId()+"> schema:fecha_fin ?fechafin . "+
                urlBase+"project/"+proyecto.getId()+"> schema:reprogramado ?reprogramado . "+
                urlBase+"project/"+proyecto.getId()+"> schema:avance ?avance . "+
                urlBase+"project/"+proyecto.getId()+"> schema:presupuesto ?presupuesto . "+
                urlBase+"project/"+proyecto.getId()+"> schema:fondo_utpl ?fondoutpl . "+
                urlBase+"project/"+proyecto.getId()+"> schema:fondo_externo ?fondoexterno . "+
                urlBase+"project/"+proyecto.getId()+"> schema:total_general ?totalgeneral . ";
        strInsert =
            "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                    "PREFIX schema: <http://schema.org/> "+
                    "DELETE { "+
                    consulta+
                    "} INSERT { " +
                    urlBase+"project/"+proyecto.getId()+"> j.2:title '"+proyecto.getTitulo()+"' . "+
                    urlBase+"project/"+proyecto.getId()+"> schema:descripcion '"+proyecto.getDescripcion()+"' . "+
                    urlBase+"project/"+proyecto.getId()+"> schema:objetivos '"+proyecto.getObjetivo()+"' . "+
                    urlBase+"project/"+proyecto.getId()+"> schema:tipoproyecto "+urlBase+proyecto.getTipo()+"> . "+
                    urlBase+"project/"+proyecto.getId()+"> schema:estadoproyecto "+urlBase+proyecto.getEstado()+"> . "+
                    urlBase+"project/"+proyecto.getId()+"> schema:programa '"+proyecto.getPrograma()+"' . "+
                    urlBase+"project/"+proyecto.getId()+"> schema:cobertura '"+proyecto.getCobertura()+"' . "+
                    urlBase+"project/"+proyecto.getId()+"> schema:smartland '"+proyecto.getSmartland()+"' . "+
                    urlBase+"project/"+proyecto.getId()+"> schema:fecha_inicio '"+proyecto.getFechainicio()+"' . "+
                    urlBase+"project/"+proyecto.getId()+"> schema:fecha_fin '"+proyecto.getFechafin()+"' . "+
                    urlBase+"project/"+proyecto.getId()+"> schema:reprogramado '"+proyecto.getReprogramado()+"' . "+
                    urlBase+"project/"+proyecto.getId()+"> schema:avance '"+proyecto.getAvance()+"' . "+
                    urlBase+"project/"+proyecto.getId()+"> schema:presupuesto '"+proyecto.getPresupuesto()+"' . "+
                    urlBase+"project/"+proyecto.getId()+"> schema:fondo_utpl '"+proyecto.getFinanciamiento_utpl()+"' . "+
                    urlBase+"project/"+proyecto.getId()+"> schema:fondo_externo '"+proyecto.getFinanciamiento_externo()+"' . "+
                    urlBase+"project/"+proyecto.getId()+"> schema:total_general '"+proyecto.getFinanciamiento_general()+"' . "+
                    "} WHERE { " +
                    consulta+
                     "}";

        RepositoryConnection repositoryConnection = con.getRepositoryConnection();
        repositoryConnection.begin();
        Update updateOperation = repositoryConnection.prepareUpdate(QueryLanguage.SPARQL, strInsert);
        updateOperation.execute();
        try {
            repositoryConnection.commit();
        } catch (Exception e) {
            estado="error";
            if (repositoryConnection.isActive())
                repositoryConnection.rollback();
        }
        return estado;
    }

    public String insertComentComment(Comentario comentario) {
        String uuid = UUID.randomUUID().toString();
        String strInsert;
        String estado="correcto";
        if(getPersonByEmail(comentario.getCorreo()).equals(comentario.getCorreo())){
            strInsert =
                    "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                            "PREFIX schema: <http://schema.org/> "+
                            "DELETE { "+
                            urlBase+"people/"+comentario.getCorreo()+"> j.2:lastName ?oldname . }"+
                            "INSERT { " +
                            urlBase + "commentProject/" + comentario.getIdCom() + "> schema:Comment " + urlBase + "comment/" + uuid + "> . " +
                            urlBase + "comment/" + uuid + "> schema:uuid \"" + uuid + "\" . " +
                            urlBase + "comment/" + uuid + "> schema:comment \"" + comentario.getComentarios() + "\" . " +
                            urlBase + "comment/" + uuid + "> schema:dateCreated \"" + getDate() + "\" . " +
                            urlBase + "comment/" + uuid+ "> schema:character " + urlBase + "people/" + comentario.getCorreo() + "> . " +
                            urlBase + "people/" + comentario.getCorreo() + "> j.2:lastName \"" + comentario.getNombre() + "\" . " +
                            "} WHERE { " +
                            urlBase+"people/"+comentario.getCorreo()+"> j.2:lastName ?oldname . }";
        }else{
            strInsert =
                    "INSERT DATA { " +
                            urlBase + "commentProject/" + comentario.getIdCom() + "> schema:Comment " + urlBase + "comment/" + uuid + "> . " +
                            urlBase + "comment/" + uuid + "> schema:uuid \"" + uuid + "\" . " +
                            urlBase + "comment/" + uuid + "> schema:comment \"" + comentario.getComentarios() + "\" . " +
                            urlBase + "comment/" + uuid + "> schema:dateCreated \"" + getDate() + "\" . " +
                            urlBase + "comment/" + uuid+ "> schema:character " + urlBase + "people/" + comentario.getCorreo() + "> . " +
                            urlBase + "people/" + comentario.getCorreo() + "> foaf:lastName \"" + comentario.getNombre() + "\" . " +
                            urlBase + "people/" + comentario.getCorreo() + "> foaf:mbox \"" + comentario.getCorreo() + "\" . " +
                            "}";

        }
        RepositoryConnection repositoryConnection = con.getRepositoryConnection();
        repositoryConnection.begin();
        Update updateOperation = repositoryConnection.prepareUpdate(QueryLanguage.SPARQL, strInsert);
        updateOperation.execute();
        try {
            repositoryConnection.commit();
        } catch (Exception e) {
            estado="error";
            if (repositoryConnection.isActive())
                repositoryConnection.rollback();
        }
        return estado;
    }

    private String getPersonByEmail(String busqueda) {
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "SELECT ?correo " +
                "WHERE { "+
                urlBase+"people/"+busqueda+"> j.2:mbox ?correo }";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral correo =
                        (SimpleLiteral) bindingSet.getValue("correo");
                json = correo.stringValue();
            }
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    public String getGrapProjectID (String idProject) {
        ArrayList<Nodo> listNodos = new ArrayList<>();
        ArrayList<Relacion> listRelacion = new ArrayList<>();
        ArrayList<NodoRelacion> nodoRelacion = new ArrayList<>();
        Gson genson = new GsonBuilder().registerTypeHierarchyAdapter(
                Collection.class, new CollectionAdapter()).create();
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                "SELECT ?idpersona ?nombre ?apellido ?id_project ?titulo ?area ?correo ?image " +
                "WHERE {"+
                "?s schema:ide_project '"+idProject+"' . "+
                "?id schema:idProject ?s . "+
                "?s schema:ide_project ?id_project ."+
                "?project j.2:currentProject ?id. "+
                "?project j.2:lastName ?nombre. "+
                "?project j.2:firstName ?apellido. "+
                "?project j.2:img ?image. "+
                "?project j.2:mbox ?correo . "+
                "?project schema:areaPerson ?labelarea . "+
                "?labelarea rdfs:label ?area ."+
                "?project schema:id_person ?idpersona ."+
                " ?s j.2:title ?titulo . " +
                "} ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            int contador = 0;
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idPersona =
                        (SimpleLiteral) bindingSet.getValue("idpersona");
                SimpleLiteral nombre =
                        (SimpleLiteral) bindingSet.getValue("nombre");
                SimpleLiteral apellido =
                        (SimpleLiteral) bindingSet.getValue("apellido");
                SimpleLiteral image =
                        (SimpleLiteral) bindingSet.getValue("image");
                SimpleLiteral area =
                        (SimpleLiteral) bindingSet.getValue("area");
                SimpleLiteral correo =
                        (SimpleLiteral) bindingSet.getValue("correo");
                SimpleLiteral idProyecto =
                        (SimpleLiteral) bindingSet.getValue("id_project");
                SimpleLiteral titulo =
                        (SimpleLiteral) bindingSet.getValue("titulo");
                String titlePersona = nombre.stringValue().concat(" ").concat(apellido.stringValue().concat("<br>").concat("Área: ").
                        concat(area.stringValue()).concat("<br>").concat("Correo: ").concat(correo.stringValue()));
                Nodo nodoPersona = new Nodo(idPersona.stringValue(), nombre.stringValue().concat("\n").concat(apellido.stringValue()), "participante", titlePersona, image.stringValue(), "circularImage");
                Nodo nodoProyecto = new Nodo(idProyecto.stringValue(), titulo.stringValue(), "projects", titulo.stringValue());
                Relacion relacion = new Relacion(idPersona.stringValue(), idProyecto.stringValue());
                if (contador == 0) {
                    listNodos.add(nodoProyecto);
                }
                contador++;
                listNodos.add(nodoPersona);
                listRelacion.add(relacion);
            }
            NodoRelacion nr = new NodoRelacion(listNodos, listRelacion);
            nodoRelacion.add(nr);
            json = genson.toJson(nodoRelacion.get(0));
            System.out.println(json);
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    public String InformacionInvolucrados(String idProject) {
        ArrayList<Persona> listapersona = new ArrayList<>();
        Persona persona;
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                "SELECT ?rol ?nombre ?apellido ?area ?idpersona " +
                "WHERE {"+
                "?s schema:ide_project '"+idProject+"' . "+
                "?id schema:idProject ?s . "+
                "?id schema:rolProyecto ?labelrol . "+
                "?labelrol rdfs:label ?rol . "+
                "?project j.2:currentProject ?id . "+
                "?project schema:id_person ?idpersona . "+
                "?project j.2:lastName ?nombre . "+
                "?project j.2:firstName ?apellido . "+
                "?project schema:areaPerson ?labelarea . "+
                "?labelarea rdfs:label ?area . "+
                "} ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral id =
                        (SimpleLiteral) bindingSet.getValue("idpersona");
                SimpleLiteral rol =
                        (SimpleLiteral) bindingSet.getValue("rol");
                SimpleLiteral nombre =
                        (SimpleLiteral) bindingSet.getValue("nombre");
                SimpleLiteral apellido =
                        (SimpleLiteral) bindingSet.getValue("apellido");
                SimpleLiteral area =
                        (SimpleLiteral) bindingSet.getValue("area");
                persona = new Persona(id.stringValue(), nombre.stringValue(), apellido.stringValue(), rol.stringValue(), area.stringValue());
                listapersona.add(persona);
            }
            json = mapper.writeValueAsString(listapersona);
            System.out.println(json);
        } catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    public String InformacionProyecto(String idProject) {
        Proyecto proyecto;
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                "SELECT ?titulo ?descripcion ?tipo ?incluye_estudiantes ?cobertura ?fechainicio ?fechafin ?ife " +
                "?smartland ?reprogramado ?avance ?fu ?fe ?tg ?estado ?programa ?objetivo ?presupuesto " +
                "WHERE {"+
                "?s schema:ide_project '"+idProject+"' . "+
                "?s j.2:title ?titulo . "+
                "?s schema:descripcion ?descripcion . "+
                "?s schema:tipoproyecto ?labeltipo . "+
                "?labeltipo rdfs:label ?tipo . "+
                "?s schema:incluye_estudiantes ?incluye_estudiantes . "+
                "?s schema:cobertura ?cobertura . "+
                "?s schema:fecha_inicio ?fechainicio . "+
                "?s schema:fecha_fin ?fechafin . "+
                "?s schema:incluye_financiamiento_externo ?ife . " +
                "?s schema:smartland ?smartland . "+
                "?s schema:reprogramado ?reprogramado . "+
                "?s schema:avance ?avance . "+
                "?s schema:fondo_utpl ?fu . "+
                "?s schema:fondo_externo ?fe . "+
                "?s schema:total_general ?tg . "+
                "?s schema:estadoproyecto ?ep . "+
                "?ep rdfs:label ?estado . "+
                "?s schema:programa ?programa . "+
                "?s schema:objetivos ?objetivo . "+
                "?s schema:presupuesto ?presupuesto . "+
                "} ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral titulo =
                        (SimpleLiteral) bindingSet.getValue("titulo");
                SimpleLiteral descripcion =
                        (SimpleLiteral) bindingSet.getValue("descripcion");
                SimpleLiteral tipo =
                        (SimpleLiteral) bindingSet.getValue("tipo");
                SimpleLiteral incluye_estudiantes =
                        (SimpleLiteral) bindingSet.getValue("incluye_estudiantes");
                SimpleLiteral cobertura =
                        (SimpleLiteral) bindingSet.getValue("cobertura");
                SimpleLiteral fechainicio =
                        (SimpleLiteral) bindingSet.getValue("fechainicio");
                SimpleLiteral fechafin =
                        (SimpleLiteral) bindingSet.getValue("fechafin");
                SimpleLiteral ife =
                        (SimpleLiteral) bindingSet.getValue("ife");
                SimpleLiteral smartland =
                        (SimpleLiteral) bindingSet.getValue("smartland");
                SimpleLiteral reprogramado =
                        (SimpleLiteral) bindingSet.getValue("reprogramado");
                SimpleLiteral avance =
                        (SimpleLiteral) bindingSet.getValue("avance");
                SimpleLiteral fu =
                        (SimpleLiteral) bindingSet.getValue("fu");
                SimpleLiteral fe =
                        (SimpleLiteral) bindingSet.getValue("fe");
                SimpleLiteral tg =
                        (SimpleLiteral) bindingSet.getValue("tg");
                SimpleLiteral estado =
                        (SimpleLiteral) bindingSet.getValue("estado");
                SimpleLiteral programa =
                        (SimpleLiteral) bindingSet.getValue("programa");
                SimpleLiteral objetivo =
                        (SimpleLiteral) bindingSet.getValue("objetivo");
                SimpleLiteral presupuesto =
                        (SimpleLiteral) bindingSet.getValue("presupuesto");
                proyecto = new Proyecto(idProject, titulo.stringValue(), descripcion.stringValue(), tipo.stringValue(),
                        incluye_estudiantes.stringValue(), cobertura.stringValue(), fechainicio.stringValue(), fechafin.stringValue(),
                        ife.stringValue(), smartland.stringValue(), reprogramado.stringValue(), avance.stringValue(), fu.stringValue(),
                        fe.stringValue(), tg.stringValue(), estado.stringValue(), programa.stringValue(), objetivo.stringValue(), presupuesto.stringValue());
                json = mapper.writeValueAsString(proyecto);
                System.out.println(json);
            }
        } catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    public String getGrapTipoProject(String xtipo) {
        ArrayList<Nodo> listNodos = new ArrayList<>();
        ArrayList<Relacion> listRelacion = new ArrayList<>();
        ArrayList<NodoRelacion> nodoRelacion = new ArrayList<>();
        Genson genson = new Genson.Builder().exclude("value", Relacion.class).exclude("title",Relacion.class).create();
        String json="";
        // Create ObjectMapper object.
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
                "select ?titulo ?id ?tipo " +
                "WHERE { "+
                "?s schema:tipoproyecto ?tipolabel ."+
                "?tipolabel rdfs:label '"+xtipo+"' . "+
                "?s j.2:title ?titulo . "+
                "?s schema:ide_project ?id . "+
                "?s schema:tipoproyecto ?labeltipo . " +
                "?labeltipo rdfs:label ?tipo . "+
                "} ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            int contador = 0;
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idProyecto =
                        (SimpleLiteral) bindingSet.getValue("id");
                SimpleLiteral titulo =
                        (SimpleLiteral) bindingSet.getValue("titulo");
                SimpleLiteral tipo =
                        (SimpleLiteral) bindingSet.getValue("tipo");
                String grupo = clasificacion(tipo.stringValue());
                Nodo nodoTipo = new Nodo("0", TraduccionTitulo(xtipo).toUpperCase(), "area", TraduccionEspacios(xtipo).toUpperCase());
                Nodo nodoProyecto = new Nodo(idProyecto.stringValue(), "", grupo, TraduccionEspacios(titulo.stringValue()).toUpperCase());
                Relacion relacion = new Relacion("0", idProyecto.stringValue());
                if (contador == 0) {
                    listNodos.add(nodoTipo);
                }
                contador++;
                listNodos.add(nodoProyecto);
                listRelacion.add(relacion);
            }
            NodoRelacion nr = new NodoRelacion(listNodos, listRelacion);
            nodoRelacion.add(nr);
            json = genson.serialize(nodoRelacion.get(0));
            System.out.println(json);
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    public String getGrapAreaProject(String xarea) {
        ArrayList<Nodo> listNodos = new ArrayList<>();
        ArrayList<Relacion> listRelacion = new ArrayList<>();
        ArrayList<NodoRelacion> nodoRelacion = new ArrayList<>();
        Genson genson = new Genson.Builder().exclude("value", Relacion.class).exclude("title",Relacion.class).create();
        String json="";
        // Create ObjectMapper object.
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
                "select ?titulo ?id ?tipo " +
                "WHERE { "+
                "?s schema:areaproyecto ?sa . "+
                "?sa rdfs:label '"+xarea+"' . "+
                "?s j.2:title ?titulo . "+
                "?s schema:ide_project ?id . "+
                "?s schema:tipoproyecto ?labeltipo . " +
                "?labeltipo rdfs:label ?tipo . "+
                "} ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            int contador = 0;
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idProyecto =
                        (SimpleLiteral) bindingSet.getValue("id");
                SimpleLiteral titulo =
                        (SimpleLiteral) bindingSet.getValue("titulo");
                SimpleLiteral tipo =
                        (SimpleLiteral) bindingSet.getValue("tipo");
                String grupo = clasificacion(tipo.stringValue());
                Nodo nodoArea = new Nodo("0", TraduccionTitulo(xarea).toUpperCase(), "area", TraduccionEspacios(xarea).toUpperCase());
                Nodo nodoProyecto = new Nodo(idProyecto.stringValue(), "", grupo, TraduccionEspacios(titulo.stringValue()).toUpperCase());
                Relacion relacion = new Relacion("0", idProyecto.stringValue());
                if (contador == 0) {
                    listNodos.add(nodoArea);
                }
                contador++;
                listNodos.add(nodoProyecto);
                listRelacion.add(relacion);
            }
            NodoRelacion nr = new NodoRelacion(listNodos, listRelacion);
            nodoRelacion.add(nr);
            json = genson.serialize(nodoRelacion.get(0));
            System.out.println(json);
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    public String getGrapProject(String xproyecto) {
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                "SELECT ?idpersona ?nombre ?apellido ?id_project ?titulo ?rol ?tipo ?correo ?area ?image " +
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
                "?project j.2:img ?image. "+
                "?project j.2:mbox ?correo . "+
                "?project schema:areaPerson ?labelarea . "+
                "?labelarea rdfs:label ?area ."+
                "?project schema:id_person ?idpersona ."+
                " ?s j.2:title ?titulo . " +
                "} ";
        ArrayList<Nodo> listNodos = new ArrayList<>();
        ArrayList<Relacion> listRelacion = new ArrayList<>();
        ArrayList<NodoRelacion> nodoRelacion = new ArrayList<>();
        Gson genson = new GsonBuilder().registerTypeHierarchyAdapter(
                Collection.class, new CollectionAdapter()).create();
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            int contador = 0;
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idPersona =
                        (SimpleLiteral) bindingSet.getValue("idpersona");
                SimpleLiteral nombre =
                        (SimpleLiteral) bindingSet.getValue("nombre");
                SimpleLiteral apellido =
                        (SimpleLiteral) bindingSet.getValue("apellido");
                SimpleLiteral rol =
                        (SimpleLiteral) bindingSet.getValue("rol");
                SimpleLiteral idProyecto =
                        (SimpleLiteral) bindingSet.getValue("id_project");
                SimpleLiteral titulo =
                        (SimpleLiteral) bindingSet.getValue("titulo");
                SimpleLiteral correo =
                        (SimpleLiteral) bindingSet.getValue("correo");
                SimpleLiteral area =
                        (SimpleLiteral) bindingSet.getValue("area");
                SimpleLiteral image =
                        (SimpleLiteral) bindingSet.getValue("image");
                SimpleLiteral tipo =
                        (SimpleLiteral) bindingSet.getValue("tipo");
                String rolP = (rol.stringValue().equalsIgnoreCase("Participación")) ? "participante" : "director";
                String titlePersona = nombre.stringValue().concat(" ").concat(apellido.stringValue().concat("<br>").concat("Área: ").
                        concat(area.stringValue()).concat("<br>").concat("Correo: ").concat(correo.stringValue()));
                String grupo = clasificacion(tipo.stringValue());
                String tituloreducido = TraduccionTitulo(titulo.stringValue());
                String tituloespacios = TraduccionEspacios(titulo.stringValue());
                Nodo nodoPersona = new Nodo(idPersona.stringValue(), nombre.stringValue().concat("\n").concat(apellido.stringValue()), rolP, titlePersona, image.stringValue(), "circularImage");
                Nodo nodoProyecto = new Nodo(idProyecto.stringValue(), tituloreducido.toUpperCase(), grupo, tituloespacios.toUpperCase());
                Relacion relacion = new Relacion(idPersona.stringValue(), idProyecto.stringValue());
                if (contador == 0) {
                    listNodos.add(nodoProyecto);
                }
                contador++;
                listNodos.add(nodoPersona);
                listRelacion.add(relacion);
            }
            NodoRelacion nr = new NodoRelacion(listNodos, listRelacion);
            nodoRelacion.add(nr);
            json = genson.toJson(nodoRelacion.get(0));
            System.out.println(json);
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    private String clasificacion(String tipo){
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
            grupo = "vinculacion";
        }
        return grupo;
    }

    private String TraduccionTitulo(String titulo) {
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

    private String TraduccionEspacios(String titulo) {
        String tituloreducido = "";
        int contador1 = 0;
        for (int i=0;i<titulo.length();i++){
            contador1++;
            tituloreducido = tituloreducido.concat(titulo.substring(i,i+1));
            if(contador1>=25){
                if(titulo.substring(i,i+1).equals(" ")){
                    tituloreducido = tituloreducido.concat("<br>");
                    contador1=0;
                }

            }
        }
        return tituloreducido;
    }

    public ArrayList<Colaboradores> getColaboraciones(String id) {
        ArrayList<Colaboradores> list = new ArrayList<>();
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                    "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                    "SELECT ?idpersona (SAMPLE(?nombre) AS ?nombre) (SAMPLE(?apellido) AS ?apellido) (SAMPLE(?correo) AS ?correo) (SAMPLE(?image) AS ?image) (SAMPLE(?area) AS ?area) (COUNT(?idpersona) as ?relaciones) " +
                    "WHERE { "+
                    "?s  schema:id_person '"+id+"' . "+
                    "?s j.2:currentProject ?id . "+
                    "?id schema:idProject ?idpro . "+
                    "?project schema:idProject ?idpro . "+
                    "?persona j.2:currentProject ?project . " +
                    "?persona j.2:lastName ?nombre . "+
                    "?persona j.2:firstName ?apellido . "+
                    "?persona j.2:img ?image . "+
                    "?persona j.2:mbox ?correo . "+
                    "?persona schema:areaPerson ?labelarea . "+
                    "?labelarea rdfs:label ?area ."+
                    "?persona schema:id_person ?idpersona ."+
                    "} GROUP BY ?idpersona ORDER BY DESC (?total)";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        Relacion relacion = null;
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idPersona =
                        (SimpleLiteral) bindingSet.getValue("idpersona");
                SimpleLiteral nombre =
                        (SimpleLiteral) bindingSet.getValue("nombre");
                SimpleLiteral apellido =
                        (SimpleLiteral) bindingSet.getValue("apellido");
                SimpleLiteral correo =
                        (SimpleLiteral) bindingSet.getValue("correo");
                SimpleLiteral area =
                        (SimpleLiteral) bindingSet.getValue("area");
                SimpleLiteral relaciones =
                        (SimpleLiteral) bindingSet.getValue("relaciones");
                SimpleLiteral image =
                        (SimpleLiteral) bindingSet.getValue("image");
                String imagen = image.stringValue();
                if (imagen.equals("n") || image.equals("")) {
                    imagen = "https://image.ibb.co/jw55Ex/def_face.jpg";
                }
                if (idPersona.stringValue().compareTo(id) != 0) {
                    Colaboradores colaboradores = new Colaboradores(idPersona.stringValue(), nombre.stringValue().concat("\n")
                            .concat(apellido.stringValue()), correo.stringValue(), area.stringValue(), relaciones.stringValue(), imagen);
                    list.add(colaboradores);
                }
            }
        } catch (QueryEvaluationException ignored) {
        }
        return list;
    }

    public String ObtenerTopColaboradores(String id) {
        ArrayList<Objeto> list = new ArrayList<>();
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                    "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                    "SELECT ?label (COUNT(?idpersona) as ?total) " +
                    "WHERE { "+
                    "?s  schema:id_person '"+id+"' . "+
                    "?s j.2:currentProject ?id . "+
                    "?id schema:idProject ?idpro . "+
                    "?project schema:idProject ?idpro . "+
                    "?persona j.2:currentProject ?project . " +
                    "?persona j.2:lastName ?nombre . "+
                    "?persona j.2:firstName ?apellido . "+
                    "?persona schema:id_person ?idpersona . "+
                    "BIND(CONCAT(?nombre,' ',?apellido) AS ?label) "+
                    "} GROUP BY ?label ORDER BY DESC (?total) LIMIT 4";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        TupleQueryResult result = null;
        Objeto objeto;
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        int aux = 0;
        try {
            result = tupleQuery.evaluate();
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral label =
                        (SimpleLiteral)bindingSet.getValue("label");
                SimpleLiteral total =
                        (SimpleLiteral)bindingSet.getValue("total");
                objeto = new Objeto(label.stringValue(),total.stringValue());
                if(aux==0){
                    aux++;
                }else{
                    list.add(objeto);
                }
            }
            json = mapper.writeValueAsString(list);
        }
        catch (QueryEvaluationException | JsonProcessingException ignored) {
        } finally {
            Objects.requireNonNull(result).close();
        }
        return json;
    }

    public String getGrapPersonPerson(String id) {
        ArrayList<Nodo> listNodos = new ArrayList<>();
        ArrayList<Relacion> listRelacion = new ArrayList<>();
        ArrayList<NodoRelacion> nodoRelacion = new ArrayList<>();
        Gson genson = new GsonBuilder().registerTypeHierarchyAdapter(
                Collection.class, new CollectionAdapter()).create();
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "SELECT ?idpersona (SAMPLE(?nombre) AS ?nombre) (SAMPLE(?apellido) AS ?apellido) (SAMPLE(?correo) AS ?correo) (SAMPLE(?area) AS ?area) (SAMPLE(?image) AS ?image) (COUNT(?idpersona) as ?relaciones) " +
                "WHERE { "+
                "?s  schema:id_person '"+id+"' . "+
                "?s j.2:currentProject ?id . "+
                "?id schema:idProject ?idpro . "+
                "?project schema:idProject ?idpro . "+
                "?persona j.2:currentProject ?project . " +
                "?persona j.2:lastName ?nombre . "+
                "?persona j.2:firstName ?apellido . "+
                "?persona j.2:img ?image . "+
                "?persona j.2:mbox ?correo . "+
                "?persona schema:areaPerson ?labelarea . "+
                "?labelarea rdfs:label ?area ."+
                "?persona schema:id_person ?idpersona ."+
                "} GROUP BY ?idpersona";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        Relacion relacion;
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idPersona =
                        (SimpleLiteral) bindingSet.getValue("idpersona");
                SimpleLiteral nombre =
                        (SimpleLiteral) bindingSet.getValue("nombre");
                SimpleLiteral apellido =
                        (SimpleLiteral) bindingSet.getValue("apellido");
                SimpleLiteral correo =
                        (SimpleLiteral) bindingSet.getValue("correo");
                SimpleLiteral area =
                        (SimpleLiteral) bindingSet.getValue("area");
                SimpleLiteral relaciones =
                        (SimpleLiteral) bindingSet.getValue("relaciones");
                SimpleLiteral image =
                        (SimpleLiteral) bindingSet.getValue("image");
                Nodo nodoPersona;
                String titlePersona = nombre.stringValue().concat(" ").concat(apellido.stringValue().concat("<br>").concat("Área: ").
                        concat(area.stringValue()).concat("<br>").concat("Correo: ").concat(correo.stringValue()));
                if (idPersona.stringValue().equals(id)) {
                    nodoPersona = new Nodo(idPersona.stringValue(), nombre.stringValue().concat("\n").concat(apellido.stringValue()), "buscado", titlePersona, image.stringValue(), "circularImage");
                } else {
                    nodoPersona = new Nodo(idPersona.stringValue(), nombre.stringValue().concat("\n").concat(apellido.stringValue()), "participante", titlePersona, image.stringValue(), "circularImage");
                }
                listNodos.add(nodoPersona);
                if (idPersona.stringValue().compareTo(id) != 0) {
                    relacion = new Relacion(id, idPersona.stringValue(), relaciones.stringValue(), relaciones.stringValue().concat(" proyectos realizados"));
                    listRelacion.add(relacion);
                }
            }
            NodoRelacion nr = new NodoRelacion(listNodos, listRelacion);
            nodoRelacion.add(nr);
            ArrayList<Integer> list = new ArrayList<>();
            for (Relacion value : listRelacion) {
                if (list.indexOf(value.getValue()) != 0) {
                    list.add(Integer.parseInt(value.getValue()));
                }
            }
            if (list.size() > 1) {
                json = mapper.writeValueAsString(nodoRelacion.get(0));
            } else {
                //iguales
                json = genson.toJson(nodoRelacion.get(0));
                //json = genson.serialize(nodoRelacion.get(0));
            }
        } catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }
    static class CollectionAdapter implements JsonSerializer<Collection<Nodo>> {

        @Override
        public JsonElement serialize(Collection<Nodo> src, Type type, JsonSerializationContext jsonSerializationContext) {
            if (src == null)
                return null;

            JsonArray array = new JsonArray();

            for (Object child : src) {
                JsonElement element = jsonSerializationContext.serialize(child);
                array.add(element);
            }

            return array;
        }
    }
    public String getGrapPerson(String id) {
        ArrayList<Nodo> listNodos = new ArrayList<>();
        ArrayList<Relacion> listRelacion = new ArrayList<>();
        ArrayList<NodoRelacion> nodoRelacion = new ArrayList<>();
        //Genson genson = new Genson.Builder().exclude("value", Relacion.class).exclude("title",Relacion.class).create();
        Gson builder = new GsonBuilder().registerTypeHierarchyAdapter(
                Collection.class, new CollectionAdapter()).create();
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "PREFIX people: <http://utpl.edu.ec/data/people/> "+
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#>"+
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "SELECT ?id ?idpersona ?nombre ?apellido ?title ?tipo ?correo ?area ?image " +
                "WHERE {"+
                "?s  schema:id_person '"+id+"' . "+
                "?s  j.2:currentProject ?current . "+
                "?s j.2:lastName ?nombre . "+
                "?s j.2:firstName ?apellido . "+
                "?s j.2:mbox ?correo . "+
                "?s j.2:img ?image . "+
                "?s schema:areaPerson ?labelarea . "+
                "?labelarea rdfs:label ?area ."+
                "?s schema:id_person ?idpersona . " +
                "?current schema:idProject ?idproject . "+
                "?idproject j.2:title ?title . "+
                "?idproject schema:ide_project ?id . "+
                "?idproject schema:tipoproyecto ?labeltipo . "+
                "?labeltipo rdfs:label ?tipo . " +
                "} ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            int contador = 0;
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idPersona =
                        (SimpleLiteral) bindingSet.getValue("idpersona");
                SimpleLiteral nombre =
                        (SimpleLiteral) bindingSet.getValue("nombre");
                SimpleLiteral apellido =
                        (SimpleLiteral) bindingSet.getValue("apellido");
                SimpleLiteral image =
                        (SimpleLiteral) bindingSet.getValue("image");
                SimpleLiteral idProyecto =
                        (SimpleLiteral) bindingSet.getValue("id");
                SimpleLiteral titulo =
                        (SimpleLiteral) bindingSet.getValue("title");
                SimpleLiteral tipo =
                        (SimpleLiteral) bindingSet.getValue("tipo");
                SimpleLiteral correo =
                        (SimpleLiteral) bindingSet.getValue("correo");
                SimpleLiteral area =
                        (SimpleLiteral) bindingSet.getValue("area");
                String titlePersona = nombre.stringValue().concat(" ").concat(apellido.stringValue().concat("<br>").concat("Área: ").
                        concat(area.stringValue()).concat("<br>").concat("Correo: ").concat(correo.stringValue()));
                Nodo nodoPersona = new Nodo(idPersona.stringValue(), nombre.stringValue().concat("\n").concat(apellido.stringValue()), "buscado", titlePersona, image.stringValue(), "circularImage");
                String grupo = clasificacion(tipo.stringValue());
                Nodo nodoProyecto = new Nodo(idProyecto.stringValue(), "", grupo, TraduccionEspacios(titulo.stringValue()).toUpperCase());
                Relacion relacion = new Relacion(idPersona.stringValue(), idProyecto.stringValue());
                if (contador == 0) {
                    listNodos.add(nodoPersona);
                }
                contador++;
                listNodos.add(nodoProyecto);
                listRelacion.add(relacion);
            }
            NodoRelacion nr = new NodoRelacion(listNodos, listRelacion);
            nodoRelacion.add(nr);
            json = builder.toJson(nodoRelacion.get(0));
            System.out.println(json);
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
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
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idPersona =
                        (SimpleLiteral) bindingSet.getValue("idpersona");
                json = idPersona.stringValue();
                System.out.println(json);
            }
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    public String getIDProject(String idComentario) {
        String json="";
        String strQuery ="PREFIX schema: <http://schema.org/> " +
                "SELECT ?idprojecto " +
                "WHERE {"+
                "?name schema:Comment "+urlBase+"commentProject/"+idComentario+"> . "+
                "?name schema:ide_project ?idprojecto . "+
                "} ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idprojecto =
                        (SimpleLiteral) bindingSet.getValue("idprojecto");
                json = idprojecto.stringValue();
            }
        } catch (QueryEvaluationException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
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
                "SELECT DISTINCT ?idpersona ?nombre ?apellido " +
                "WHERE { "+
                "?s  j.2:firstName ?nombre . "+
                "?s  j.2:lastName ?apellido . "+
                "?s schema:id_person ?idpersona . "+
                "?s j.2:currentProject ?project "+
                "FILTER (regex(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE((UCASE(str(?nombre))),'Á', 'A','i'),'É', 'E','i'),'Í', 'I','i'),'Ó','O','i'),'Ú','U','i'),'"+
                busqueda+"','i')||regex(REPLACE(REPLACE(REPLACE(REPLACE(REPLACE((UCASE(str(?apellido))),'Á', 'A','i'),'É', 'E','i'),'Í', 'I','i'),'Ó','O','i'),'Ú','U','i'),'"+
                busqueda+"','i')) } ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
       try (TupleQueryResult result = tupleQuery.evaluate()) {
           while (result.hasNext()) {
               BindingSet bindingSet = result.next();
               SimpleLiteral idpersona =
                       (SimpleLiteral) bindingSet.getValue("idpersona");
               SimpleLiteral nombre =
                       (SimpleLiteral) bindingSet.getValue("nombre");
               SimpleLiteral apellido =
                       (SimpleLiteral) bindingSet.getValue("apellido");
               Objeto persona = new Objeto(idpersona.stringValue(), nombre.stringValue().concat(" ").concat(apellido.stringValue()).toUpperCase());
               listaPersona.add(persona);
           }
           json = mapper.writeValueAsString(listaPersona);
       } catch (QueryEvaluationException | JsonProcessingException qee) {
           logger.error(WTF_MARKER,
                   Arrays.toString(qee.getStackTrace()), qee);
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
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idpersona =
                        (SimpleLiteral) bindingSet.getValue("idproyecto");
                SimpleLiteral titulo =
                        (SimpleLiteral) bindingSet.getValue("titulo");
                Objeto proyecto = new Objeto(idpersona.stringValue(), titulo.stringValue().toUpperCase());
                listaPersona.add(proyecto);
            }
            json = mapper.writeValueAsString(listaPersona);
        } catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    public String BusquedaPorTipoProyecto() {
        ArrayList<Objeto> listaTipo = new ArrayList<>();
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX onto:<http://www.ontotext.com/> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                "select ?tipo " +
                "from onto:disable-sameAs { "+
                "?s a <http://schema.org/tipo_proyecto> . "+
                "?s rdfs:label ?tipo . } ORDER BY ?tipo ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral tipo =
                        (SimpleLiteral) bindingSet.getValue("tipo");
                String tipopro = tipo.stringValue().substring(0, 5);
                if (tipopro.compareTo("http:") != 0) {
                    if (tipo.stringValue().compareTo("SIN ASIGNAR") != 0) {
                        Objeto otipo = new Objeto(tipo.stringValue(), tipo.stringValue().toUpperCase());
                        listaTipo.add(otipo);
                    }
                }
            }
            System.out.println(json);
            json = mapper.writeValueAsString(listaTipo);
        } catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    public String BusquedaPorArea() {
        ArrayList<Objeto> listaArea = new ArrayList<>();
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX onto:<http://www.ontotext.com/> " +
                "PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> "+
                "select ?area " +
                "from onto:disable-sameAs { "+
                "?s a <http://schema.org/area_conocimiento> . "+
                "?s rdfs:label ?area . } ORDER BY ?area ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            int contador = 0;
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral area =
                        (SimpleLiteral) bindingSet.getValue("area");
                String tipoarea = area.stringValue().substring(0, 5);
                if (tipoarea.equalsIgnoreCase("http:")) {
                    //comparar cadenas
                } else {
                    Objeto oarea = new Objeto(area.stringValue(), area.stringValue().toUpperCase());
                    listaArea.add(oarea);
                }
                contador++;
            }
            System.out.println(json);
            json = mapper.writeValueAsString(listaArea);
        } catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    public String ObtenerTotalTiposProyecto(int identificador) {
        System.out.println("ESTO ES"+identificador);
        String strQuery;
        ArrayList<Objeto> listaArea = new ArrayList<>();
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        if(identificador==0){
            strQuery ="PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                    "PREFIX schema: <http://schema.org/> "+
                    "select (SAMPLE(?label) AS ?label) (COUNT(?tipo) as ?total) " +
                    "where { "+
                    "?s schema:tipoproyecto ?tipo ."+
                    "?tipo rdfs:label ?label .  "+
                    "} GROUP BY ?tipo ORDER BY ?tipo ";
        }else{
            strQuery ="PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                    "PREFIX schema: <http://schema.org/> "+
                    "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                    "select (SAMPLE(?label) AS ?label) (COUNT(?tipo) as ?total) " +
                    "where { "+
                    "?idpro schema:idProject ?s . "+
                    "?id j.2:currentProject ?idpro . "+
                    "?id schema:id_person ?identificador . "+
                    "?s schema:tipoproyecto ?tipo ."+
                    "?tipo rdfs:label ?label .  "+
                    "filter(regex(?identificador, '"+identificador+"','i'))"+
                    "} GROUP BY ?tipo ORDER BY ?tipo ";
        }
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral label =
                        (SimpleLiteral) bindingSet.getValue("label");
                SimpleLiteral total =
                        (SimpleLiteral) bindingSet.getValue("total");
                Objeto oarea = new Objeto(label.stringValue(), total.stringValue());
                listaArea.add(oarea);

            }
            json = mapper.writeValueAsString(listaArea);
        } catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    public String ObtenerTotalAreaProyecto() {
        ArrayList<Objeto> listaArea = new ArrayList<>();
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX schema: <http://schema.org/> "+
                "select (SAMPLE(?label) AS ?label) (COUNT(?tipo) as ?total) " +
                "where { "+
                "?s schema:areaproyecto ?tipo ."+
                "?tipo rdfs:label ?label .  "+
                "} GROUP BY ?tipo ORDER BY ?tipo ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral label =
                        (SimpleLiteral) bindingSet.getValue("label");
                SimpleLiteral total =
                        (SimpleLiteral) bindingSet.getValue("total");
                String tipo = label.stringValue().substring(0, 5);
                Objeto oarea;
                if (tipo.compareTo("http:") == 0) {
                    oarea = new Objeto("SIN ASIGNAR", total.stringValue());
                } else {
                    oarea = new Objeto(label.stringValue(), total.stringValue());
                }
                listaArea.add(oarea);
            }
            System.out.println(json);
            json = mapper.writeValueAsString(listaArea);
        } catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    public String ObtenerProyectosEstado() {
        ArrayList<Objeto> listaEstado = new ArrayList<>();
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX schema: <http://schema.org/> "+
                "select (SAMPLE(?label) AS ?label) (COUNT(?tipo) as ?total) " +
                "where { "+
                "?s schema:estadoproyecto ?tipo . "+
                "?tipo rdfs:label ?label .  "+
                "} GROUP BY ?tipo ORDER BY ?tipo ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            int finalizado = 0;
            Objeto estado = null;
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral label =
                        (SimpleLiteral) bindingSet.getValue("label");
                SimpleLiteral total =
                        (SimpleLiteral) bindingSet.getValue("total");
                String labelEstado = label.stringValue();
                switch (labelEstado) {
                    case "anulado":
                        estado = new Objeto("ANULADO", total.stringValue());
                        break;
                    case "dado_de_baja":
                        estado = new Objeto("DADO DE BAJA", total.stringValue());
                        break;
                    case "ejecutandose":
                        estado = new Objeto("EJECUTÁNDOSE", total.stringValue());
                        break;
                    case "finalizado":
                    default:
                        finalizado = finalizado + total.intValue();
                        break;
                }
                if (estado != null) {
                    if (!listaEstado.contains(estado)) {
                        listaEstado.add(estado);
                    }
                }
            }
            estado = new Objeto("FINALIZADO", String.valueOf(finalizado));
            listaEstado.add(estado);
            System.out.println(json);
            json = mapper.writeValueAsString(listaEstado);
        } catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    public String ObtenerProyectosCobertura() {
        ArrayList<Objeto> listaEstado = new ArrayList<>();
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String strQuery ="PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX schema: <http://schema.org/> "+
                "select (SAMPLE(?tipo) AS ?label) (COUNT(?tipo) as ?total) " +
                "where { "+
                "?s schema:cobertura ?tipo . "+
                "} GROUP BY ?tipo ORDER BY ?tipo ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            Objeto estado = null;
            int estadoproyecto = 0;
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral label =
                        (SimpleLiteral) bindingSet.getValue("label");
                SimpleLiteral total =
                        (SimpleLiteral) bindingSet.getValue("total");
                String labelEstado = label.stringValue();
                if (labelEstado.equals("Internacional") || labelEstado.equals("Local") || labelEstado.equals("Nacional")) {
                    switch (labelEstado) {
                        case "Internacional":
                            estado = new Objeto("INTERNACIONAL", total.stringValue());
                            break;
                        case "Local":
                            estado = new Objeto("LOCAL", total.stringValue());
                            break;
                        case "Nacional":
                            estadoproyecto = estadoproyecto + total.intValue();
                            break;
                    }
                } else {
                    estadoproyecto = estadoproyecto + total.intValue();
                }
                if (estado != null) {
                    if (!listaEstado.contains(estado)) {
                        listaEstado.add(estado);
                    }
                }
            }
            estado = new Objeto("NACIONAL", String.valueOf(estadoproyecto));
            listaEstado.add(estado);
            System.out.println(json);
            json = mapper.writeValueAsString(listaEstado);
        } catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    public String ObtenerTopDocentes() {
        String strQuery;
        ArrayList<Objeto> listaDocentes = new ArrayList<>();
        String json="";
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        strQuery ="PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX schema: <http://schema.org/> "+
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "select  ?label (COUNT(?s) as ?total) " +
                "where { "+
                "?s j.2:currentProject ?tipo . "+
                "?s j.2:lastName ?nombre . "+
                "?s j.2:firstName ?apellido ."+
                "BIND(CONCAT(?nombre,' ',?apellido) AS ?label)"+
                "} GROUP BY ?label ORDER BY DESC (?total) LIMIT 5 ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            Objeto nodo;
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral label =
                        (SimpleLiteral) bindingSet.getValue("label");
                SimpleLiteral total =
                        (SimpleLiteral) bindingSet.getValue("total");
                nodo = new Objeto(label.stringValue(), total.stringValue());
                System.out.println(nodo.getNombre());
                listaDocentes.add(nodo);
            }
            json = mapper.writeValueAsString(listaDocentes);
        } catch (QueryEvaluationException | JsonProcessingException qee) {
            logger.error(WTF_MARKER,
                    Arrays.toString(qee.getStackTrace()), qee);
        }
        return json;
    }

    public ArrayList<ListaProyecto> getProyectos() {
        ArrayList<ListaProyecto> list = new ArrayList<>();
        ArrayList<Nodo> nodolista = new ArrayList<>();
        String strQuery ="PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX schema: <http://schema.org/> "+
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "select distinct ?idproyecto ?titulo ?nombres ?area " +
                "WHERE { "+
                "?idper j.2:currentProject ?idpro . ?idper j.2:lastName ?nombre . ?idper j.2:firstName ?apellido . "+
                "?idper schema:areaPerson ?arealabel . ?arealabel rdfs:label ?area . ?idpro schema:idProject ?id . "+
                "?id schema:ide_project ?idproyecto . ?id j.2:title ?titulo . ?idpro schema:rolProyecto ?rollabel . "+
                "?rollabel rdfs:label ?label . BIND(CONCAT(?nombre,' ',?apellido) AS ?nombres) "+
                "FILTER (!regex(?label, 'Participación','i')) . } ORDER BY DESC (?nombres) ";
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        Nodo nodo;
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idproyecto =
                        (SimpleLiteral) bindingSet.getValue("idproyecto");
                SimpleLiteral titulo =
                        (SimpleLiteral) bindingSet.getValue("titulo");
                SimpleLiteral nombres =
                        (SimpleLiteral) bindingSet.getValue("nombres");
                SimpleLiteral area =
                        (SimpleLiteral) bindingSet.getValue("area");
                String tipoarea = area.stringValue().substring(0, 5);
                if (tipoarea.compareTo("http:") == 0) {
                    nodo = new Nodo(idproyecto.stringValue(), titulo.stringValue(), nombres.stringValue(), "SIN ASIGNAR");
                    nodolista.add(nodo);
                } else {
                    nodo = new Nodo(idproyecto.stringValue(), titulo.stringValue(), nombres.stringValue(), area.stringValue());
                    nodolista.add(nodo);
                }
            }
            ListaProyecto listaCompleta;
            for (int i = 0; i < nodolista.size(); i += 3) {
                Nodo nodo1 = new Nodo("", "", "", "");
                Nodo nodo2 = new Nodo("", "", "", "");
                Nodo nodo3 = new Nodo("", "", "", "");
                if (i < nodolista.size()) {
                    nodo1 = new Nodo(nodolista.get(i).getId(), nodolista.get(i).getLabel(), nodolista.get(i).getGroup(), nodolista.get(i).getTitle());
                }
                if (i + 1 < nodolista.size()) {
                    nodo2 = new Nodo(nodolista.get(i + 1).getId(), nodolista.get(i + 1).getLabel(), nodolista.get(i + 1).getGroup(), nodolista.get(i + 1).getTitle());
                }
                if (i + 2 < nodolista.size()) {
                    nodo3 = new Nodo(nodolista.get(i + 2).getId(), nodolista.get(i + 2).getLabel(), nodolista.get(i + 2).getGroup(), nodolista.get(i + 2).getTitle());
                }
                listaCompleta = new ListaProyecto(nodo1, nodo2, nodo3);
                list.add(listaCompleta);
            }
        } catch (QueryEvaluationException ignored) {
        }
        return list;
    }

    public ArrayList<ListaProyecto> getProyectosFiltrados(ArrayList<String> lista, String busqueda) {
        System.out.println("Buscqueda:"+busqueda);
        System.out.println("Lista:"+lista);
        ArrayList<ListaProyecto> list = new ArrayList<>();
        ArrayList<Nodo> nodolista = new ArrayList<>();
        String strQuery ="PREFIX rdfs: <http://www.w3.org/2000/01/rdf-schema#> " +
                "PREFIX schema: <http://schema.org/> "+
                "PREFIX j.2: <http://xmlns.com/foaf/0.1/> "+
                "select distinct ?idproyecto ?titulo ?nombres ?area " +
                "WHERE { "+
                "?idper j.2:currentProject ?idpro . ?idper j.2:lastName ?nombre . ?idper j.2:firstName ?apellido . "+
                "?idper schema:areaPerson ?arealabel . ?arealabel rdfs:label ?area . ?idpro schema:idProject ?id . "+
                "?id schema:ide_project ?idproyecto . ?id j.2:title ?titulo . ?idpro schema:rolProyecto ?rollabel . "+
                "?rollabel rdfs:label ?label . BIND(CONCAT(?nombre,' ',?apellido) AS ?nombres) "+
                "FILTER (!regex(?label, 'Participación','i') ";
        String strQueryMedio=" && ( ?area IN ( ";
        String strQueryMedio1=" && (regex(?nombres, '"+busqueda+"','i') || regex(?titulo, '"+busqueda+"','i')) ";
        String strQueryFin=" } ORDER BY DESC (?area) ";
        if(!lista.get(0).equals("todo")){
            for (int i=0; i<lista.size();i++){
                if(lista.size()==i+1){
                    strQuery = strQuery.concat(strQueryMedio).concat("'").concat(lista.get(i)).concat("'))");
                }else{
                    strQuery = strQuery.concat(strQueryMedio).concat("'").concat(lista.get(i)).concat("',");
                }
            }
        }
        if(!busqueda.equals("vacio")){
            strQuery=strQuery.concat(strQueryMedio1);

        }
        strQuery=strQuery.concat(" )} ORDER BY DESC (?nombres)");
        System.out.println(strQuery);
        TupleQuery tupleQuery = con.getRepositoryConnection()
                .prepareTupleQuery(QueryLanguage.SPARQL, strQuery);
        Nodo nodo;
        try (TupleQueryResult result = tupleQuery.evaluate()) {
            while (result.hasNext()) {
                BindingSet bindingSet = result.next();
                SimpleLiteral idproyecto =
                        (SimpleLiteral) bindingSet.getValue("idproyecto");
                SimpleLiteral titulo =
                        (SimpleLiteral) bindingSet.getValue("titulo");
                SimpleLiteral nombres =
                        (SimpleLiteral) bindingSet.getValue("nombres");
                SimpleLiteral area =
                        (SimpleLiteral) bindingSet.getValue("area");
                String tipoarea = area.stringValue().substring(0, 5);
                if (tipoarea.compareTo("http:") == 0) {
                    nodo = new Nodo(idproyecto.stringValue(), titulo.stringValue(), nombres.stringValue(), "SIN ASIGNAR");
                    nodolista.add(nodo);
                } else {
                    nodo = new Nodo(idproyecto.stringValue(), titulo.stringValue(), nombres.stringValue(), area.stringValue());
                    nodolista.add(nodo);
                }
            }
            ListaProyecto listaCompleta;
            for (int i = 0; i < nodolista.size(); i += 3) {
                Nodo nodo1 = new Nodo("", "", "", "");
                Nodo nodo2 = new Nodo("", "", "", "");
                Nodo nodo3 = new Nodo("", "", "", "");
                nodo1 = new Nodo(nodolista.get(i).getId(), nodolista.get(i).getLabel(), nodolista.get(i).getGroup(), nodolista.get(i).getTitle());
                if (i + 1 < nodolista.size()) {
                    nodo2 = new Nodo(nodolista.get(i + 1).getId(), nodolista.get(i + 1).getLabel(), nodolista.get(i + 1).getGroup(), nodolista.get(i + 1).getTitle());
                }
                if (i + 2 < nodolista.size()) {
                    nodo3 = new Nodo(nodolista.get(i + 2).getId(), nodolista.get(i + 2).getLabel(), nodolista.get(i + 2).getGroup(), nodolista.get(i + 2).getTitle());
                }
                listaCompleta = new ListaProyecto(nodo1, nodo2, nodo3);
                list.add(listaCompleta);
            }

        } catch (QueryEvaluationException ignored) {
        }

        return list;
    }
}
