package ec.edu.utpl.TrabajoTitulacion.View;

import com.fasterxml.jackson.core.JsonProcessingException;
import ec.edu.utpl.TrabajoTitulacion.Controller.consultasBD;
import ec.edu.utpl.TrabajoTitulacion.Model.Comentario;
import ec.edu.utpl.TrabajoTitulacion.Model.ComentarioGlobal;
import ec.edu.utpl.TrabajoTitulacion.Model.Usuario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

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

    @GetMapping("/")
    public String homePage(HttpServletRequest request) {
        if(request.getRemoteUser()!=null){
            return "redirect:/proyectos";
        }else{
            return "home";
        }
    }

    @GetMapping("/estadisticas")
    public String estadisticas() {
        return "estadisticas";
    }

    @GetMapping("/repositorio")
    public String repositorio() {
        return "repositorio";
    }

    @GetMapping("/nosotros")
    public String nosotros() {
        return "nosotros";
    }

    @GetMapping("/comentario/{id}")
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
        httpResponse.sendRedirect("/comentario/"+idProyecto);
    }

    @PostMapping("/comentario")
    public void submitForm(@ModelAttribute("coment") Comentario comentario, HttpServletResponse httpResponse) throws IOException {
        estado = consultas.insertComent(comentario);
        httpResponse.sendRedirect("/comentario/"+comentario.getIdCom());
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
