package ec.edu.utpl.TrabajoTitulacion.View;

import com.fasterxml.jackson.core.JsonProcessingException;
import ec.edu.utpl.TrabajoTitulacion.Controller.consultasBD;
import ec.edu.utpl.TrabajoTitulacion.Controller.trasformacionRDF;
import ec.edu.utpl.TrabajoTitulacion.Model.*;
import ec.edu.utpl.TrabajoTitulacion.TrabajoTitulacionApplication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

@Controller
public class HomeController {
    String appName;
    String personas;
    String estado;
    String idProyecto;
    String tamanioLista;
    ArrayList<Comentario> listacomentarios = new ArrayList<>();
    ArrayList<ComentarioGlobal> listacomentarioGlobal = new ArrayList<>();
    consultasBD consultas = new consultasBD();

    @Autowired
    TrabajoTitulacionApplication trabajoTitulacionApplication = new TrabajoTitulacionApplication();

    @GetMapping("/")
    public String homePage(HttpServletRequest request) {
        /*if(request.getRemoteUser()!=null){
            return "redirect:/proyectos";
        }else{*/
            return "home";
        //}
    }

    @GetMapping("/estadisticas")
    public String estadisticas(Model model) {
        String listaproyectos = consultas.ObtenerTotalTiposProyecto(0);
        String listaareas = consultas.ObtenerTotalAreaProyecto();
        String listaestado = consultas.ObtenerProyectosEstado();
        String listacobertura = consultas.ObtenerProyectosCobertura();
        String listaDocentes = consultas.ObtenerTopDocentes();
        model.addAttribute("listaproyectos",listaproyectos);
        model.addAttribute("listaareas",listaareas);
        model.addAttribute("listaestado",listaestado);
        model.addAttribute("listacobertura",listacobertura);
        model.addAttribute("listaDocentes",listaDocentes);
        return "estadisticas";
    }

    @GetMapping("/repositorio")
    public String repositorio(Model model) {
        ArrayList<ListaProyecto> proyectos = consultas.getProyectos();
        model.addAttribute("proyectos", proyectos);
        return "repositorio";
    }

    @GetMapping("/repositorio/{id}/{busqueda}")
    public ResponseEntity<ArrayList<ListaProyecto>> repositoriofiltro(@PathVariable(value="id") ArrayList<String> id, @PathVariable(value="busqueda") String busqueda) {
        ArrayList<ListaProyecto> proyectos = consultas.getProyectosFiltrados(id,busqueda);
        return new ResponseEntity<ArrayList<ListaProyecto>>(proyectos, HttpStatus.OK);
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    @PostMapping("/admin")
    public String admin(@ModelAttribute("admin") Administracion administracion) throws IOException {
        String uuid = UUID.randomUUID().toString();
        System.out.println(administracion.getDominio());
        trasformacionRDF t = new trasformacionRDF();
        t.obtenerDatosBase(administracion);
        consultas.loadDataSet();
        Date objDate = new Date();
        String strDateFormat = "hh: mm: ss a dd-MMM-yyyy";
        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
        System.out.println(objSDF.format(objDate));
        Administracion administracion1 = new Administracion(administracion.getDominio(),administracion.getPuerto(),administracion.getNombreBD(),administracion.getUser(),objSDF.format(objDate));
        consultas.insertActualizacion(administracion1,uuid);
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String admin(Model model){
        Administracion administracion = new Administracion();
        model.addAttribute("admin",administracion);
        ArrayList<Administracion> list = consultas.getAdministracion();
        model.addAttribute("administracion",list);
        if(list.size()==0){
            model.addAttribute("existe","No existen actualizaciones");
        }
        return "admin";
    }

    @GetMapping("/usuario/{id}")
    public String usuario(@PathVariable(value="id") String id, Model model, HttpServletRequest request) throws IOException {
        ArrayList<Colaboradores> listaAux = new ArrayList<>();
        boolean bandera;
        if(request.getRemoteUser()!=null){
            bandera=true;
        }else{
             bandera=false;
        }
        Usuario usuario = consultas.getUsuario(id, bandera);
        model.addAttribute("usuario",usuario);
        ArrayList<Proyecto> listaProyectos = consultas.getProject(usuario.getMbox());
        model.addAttribute("listaproyectos",listaProyectos);
        String listaTipo = consultas.ObtenerTotalTiposProyecto(Integer.parseInt(usuario.getId()));
        model.addAttribute("listaTipo",listaTipo);
        String listaCola = consultas.ObtenerTopColaboradores(usuario.getId());
        model.addAttribute("listaCola",listaCola);
        ArrayList<Colaboradores> listaColaboradores = consultas.getColaboraciones(usuario.getId());
        if(listaColaboradores.size()==0){
            model.addAttribute("mensaje","No existen colaboradores");
        }else if(listaColaboradores.size()<=10){
            model.addAttribute("colaboraciones",listaColaboradores);
        }else if(listaColaboradores.size()>10){
            for(Colaboradores colaboradores:listaColaboradores) {
                if(Integer.parseInt(colaboradores.getRelaciones())>=3){
                    listaAux.add(colaboradores);
                }
            }
            model.addAttribute("colaboraciones",listaAux);
        }

        return "usuario";
    }

    @GetMapping("/proyecto/{id}")
    public String comentario(HttpServletRequest request, Model model, HttpSession session, @PathVariable(value="id") String id) {
        if(request.getRemoteUser()!=null) {
            String user = request.getRemoteUser().concat("@utpl.edu.ec");
            Usuario usuario = consultas.getUser(user);
            session.setAttribute("usuario",usuario);
            usuario = (Usuario) session.getAttribute("usuario");
            Comentario coment = new Comentario(id,usuario.getNombre(),"","",usuario.getMbox());
            Comentario comentario1 = new Comentario(id,usuario.getNombre(),"","",usuario.getMbox());
            model.addAttribute("coment", coment);
            model.addAttribute("comentario", comentario1);
        }else{
            Comentario coment = new Comentario(id,"","","","");
            Comentario comentario1 = new Comentario("","","","","");
            model.addAttribute("coment", coment);
            model.addAttribute("comentario", comentario1);
        }
        ArrayList<Recurso> list = consultas.getResouceProject(id);
        model.addAttribute("files",list);
        if(list.size()==0){
            model.addAttribute("existe","No existen recursos");
        }
        appName = consultas.InformacionProyecto(id);
        personas = consultas.InformacionInvolucrados(id);
        model.addAttribute("appName",appName);
        model.addAttribute("personas",personas);
        listacomentarios = consultas.getComment(id);
        listacomentarioGlobal = consultas.getCommentGloblal(listacomentarios);
        int tamanio =listacomentarios.size();
        for(int i=0;i<listacomentarioGlobal.size();i++){
            tamanio=tamanio+listacomentarioGlobal.get(i).getListaComentario().size();
        }//
        if(tamanio==0){
            tamanioLista="display: none";
        }else if(tamanio==1){
            tamanioLista="overflow-y: scroll; height: 150px";
        }else if(tamanio==2){
            tamanioLista="overflow-y: scroll; height: 300px";
        }else if(tamanio==3){
            tamanioLista="overflow-y: scroll; height: 350px";
        }else if(tamanio>=4){
            tamanioLista="overflow-y: scroll; height: 400px";
        }
        model.addAttribute("comentarios", listacomentarioGlobal);
        model.addAttribute("tamanio", tamanioLista);
        return "comentario";
    }

    @GetMapping("/getNameByEmail/{email}")
    public ResponseEntity<String> getNameByEmail(@PathVariable(value="email") String email) {
        appName = consultas.getNameByEmail(email);
        return new ResponseEntity<String>(appName, HttpStatus.OK);
    }

    @PostMapping("/comentarioresponse")
    public void submitFormComment(@ModelAttribute("comentario") Comentario comentario, HttpServletResponse httpResponse) throws IOException{
        estado = consultas.insertComentComment(comentario);
        idProyecto = consultas.getIDProject(comentario.getIdCom());
        httpResponse.sendRedirect("/proyecto/"+idProyecto);
        /*String url="http://localhost:8888/proyecto/"+comentario.getIdCom();
        String contenido = "Estimad@, \nLe notificamos que su proyecto ha sido comentado por \n"+comentario.getNombre()+" con la siguiente " +
                "descripción:\n\t \""+comentario.getComentarios()+"\"\nIr a proyecto: "+url;
        Mail mail = new Mail("eriivasquez.ev.ev@gmail.com","Comentario Proyecto",contenido);
        trabajoTitulacionApplication.sendEmail(mail);*/
    }

    @PostMapping("/comentario")
    public void submitForm(@ModelAttribute("coment") Comentario comentario, HttpServletResponse httpResponse) throws IOException {
        String url="http://localhost:8888/proyecto/"+comentario.getIdCom();
        estado = consultas.insertComent(comentario);
        httpResponse.sendRedirect("/proyecto/"+comentario.getIdCom());
        String contenido = "Estimad@, \nLe notificamos que su proyecto fue comentado por \n"+comentario.getNombre()+" con la siguiente " +
                "descripción:\n\t \""+comentario.getComentarios()+"\"\nVer comentario: "+url;
        Mail mail = new Mail("eriivasquez.ev.ev@gmail.com","Comentario Proyecto",contenido);
        trabajoTitulacionApplication.sendEmail(mail);

    }

    @GetMapping("/person/{id}")
    public ResponseEntity<String> busquedaPersona(@PathVariable(value="id") String id) throws JsonProcessingException {
        appName = consultas.getGrapPerson(id);
        return new ResponseEntity<String>(appName, HttpStatus.OK);
    }

    @GetMapping("/personperson/{id}")
    public ResponseEntity<String> busquedaPersonaPersona(@PathVariable(value="id") String id) throws JsonProcessingException {
        appName = consultas.getGrapPersonPerson(id);
        return new ResponseEntity<String>(appName, HttpStatus.OK);
    }

    @GetMapping("/listaBusquedaPersona/{id}")
    public ResponseEntity<String> ObtenerPersonas(@PathVariable(value="id") String id) throws JsonProcessingException {
        appName = consultas.BusquedaPorNombre(id);
        return new ResponseEntity<String> (appName, HttpStatus.OK);
    }

    @GetMapping("/listaBusquedaProyecto/{id}")
    public ResponseEntity<String> ObtenerProyectos(@PathVariable(value="id") String id) throws JsonProcessingException {
        appName = consultas.BusquedaPorProyecto(id);
        return new ResponseEntity<String> (appName, HttpStatus.OK);
    }

    @GetMapping("/listaBusquedaArea")
    public ResponseEntity<String> ObtenerAreas() throws JsonProcessingException {
        appName = consultas.BusquedaPorArea();
        return new ResponseEntity<String> (appName, HttpStatus.OK);
    }

    @GetMapping("/listaBusquedaTipoProyecto")
    public ResponseEntity<String> ObtenerTipoProyecto() throws JsonProcessingException {
        appName = consultas.BusquedaPorTipoProyecto();
        return new ResponseEntity<String> (appName, HttpStatus.OK);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<String> busquedaProyecto(@PathVariable(value="id") String id) throws JsonProcessingException {
        appName = consultas.getGrapProject(id);
        return new ResponseEntity<String>(appName, HttpStatus.OK);
    }

    @GetMapping("/projectID/{id}")
    public ResponseEntity<String> busquedaProyectoID(@PathVariable(value="id") String id) throws JsonProcessingException {
        appName = consultas.getGrapProjectID(id);
        return new ResponseEntity<String>(appName, HttpStatus.OK);
    }

    @GetMapping("/projectArea/{id}")
    public ResponseEntity<String> busquedaProyectoArea(@PathVariable(value="id") String id) throws JsonProcessingException {
        appName = consultas.getGrapAreaProject(id);
        return new ResponseEntity<String>(appName, HttpStatus.OK);
    }

    @GetMapping("/projectTipo/{id}")
    public ResponseEntity<String> busquedaProyectoTipo(@PathVariable(value="id") String id) throws JsonProcessingException {
        appName = consultas.getGrapTipoProject(id);
        return new ResponseEntity<String>(appName, HttpStatus.OK);
    }

    @GetMapping("/personID/{id}")
    public ResponseEntity<String> busquedaPersonaID(@PathVariable(value="id") String id) throws JsonProcessingException {
        String idPerson = consultas.getIDPerson(id);
        return new ResponseEntity<String>(idPerson, HttpStatus.OK);
    }
}
