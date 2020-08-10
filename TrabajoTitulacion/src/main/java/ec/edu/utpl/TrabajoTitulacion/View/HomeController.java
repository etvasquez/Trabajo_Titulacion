package ec.edu.utpl.TrabajoTitulacion.View;

import com.fasterxml.jackson.core.JsonProcessingException;
import ec.edu.utpl.TrabajoTitulacion.Controller.consultasBD;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class HomeController {

    String appName;
    String appName1;
    consultasBD consultas = new consultasBD();
    @GetMapping("/")
    public String homePage(Model model) {
        return "home";
    }

    @GetMapping("/person/{id}")
    public ResponseEntity<String> busquedaPersona(@PathVariable(value="id") String id) throws JsonProcessingException {
        appName = consultas.getGrapPerson(id);
        return new ResponseEntity<String>(appName, HttpStatus.OK);
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<String> busquedaProyecto(@PathVariable(value="id") String id) throws JsonProcessingException {
        appName = consultas.getGrapProject(id);
        return new ResponseEntity<String>(appName, HttpStatus.OK);
    }

    @GetMapping("/projectID/{id}")
    public ResponseEntity<String> busquedaProyectoID(@PathVariable(value="id") String id) throws JsonProcessingException {
        appName1 = consultas.getGrapProjectID(id);
        return new ResponseEntity<String>(appName1, HttpStatus.OK);
    }
}
