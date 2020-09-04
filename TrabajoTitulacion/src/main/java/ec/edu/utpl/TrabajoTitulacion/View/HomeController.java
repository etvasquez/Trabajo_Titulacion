package ec.edu.utpl.TrabajoTitulacion.View;

import com.fasterxml.jackson.core.JsonProcessingException;
import ec.edu.utpl.TrabajoTitulacion.Controller.consultasBD;
import ec.edu.utpl.TrabajoTitulacion.Model.Comentario;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
public class HomeController {
    String appName;
    String personas;
    String estado;
    ArrayList<Comentario> listacomentarios = new ArrayList<>();
    consultasBD consultas = new consultasBD();
    @GetMapping("/")
    public String homePage() {
        return "home";
    }

    @GetMapping("/comentario/{id}")
    public String comentario(Model model, @PathVariable(value="id") String id) {
        appName = consultas.InformacionProyecto(id);
        personas = consultas.InformacionInvolucrados(id);
        model.addAttribute("appName",appName);
        model.addAttribute("personas",personas);
        Comentario coment = new Comentario(id,"","","","");
        model.addAttribute("coment", coment);
        listacomentarios = consultas.getComment(id);
        model.addAttribute("comentarios", listacomentarios);
        return "comentario";
    }
    @PostMapping("/comentario")
    public String submitForm(@ModelAttribute("coment") Comentario comentario, Model model) {
        estado = consultas.insertComent(comentario);
        model.addAttribute("estado",estado);
        appName = consultas.InformacionProyecto(comentario.getIdCom());
        personas = consultas.InformacionInvolucrados(comentario.getIdCom());
        Comentario coment = new Comentario(comentario.getIdCom(),"","","","");
        model.addAttribute("coment", coment);
        model.addAttribute("appName",appName);
        model.addAttribute("personas",personas);
        listacomentarios = consultas.getComment(comentario.getIdCom());
        model.addAttribute("comentarios", listacomentarios);
        return "comentario";
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
