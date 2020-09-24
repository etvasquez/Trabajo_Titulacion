package ec.edu.utpl.TrabajoTitulacion.View;

import ec.edu.utpl.TrabajoTitulacion.Controller.consultasBD;
import ec.edu.utpl.TrabajoTitulacion.Model.Proyecto;
import org.eclipse.jetty.client.api.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.ArrayList;

@Controller
public class PrivateController {
    consultasBD consultas = new consultasBD();

    @GetMapping("/proyectos")
    public String proyectos(HttpServletRequest request, Model model){
        String user = request.getRemoteUser().concat("@utpl.edu.ec");
        ArrayList<Proyecto> listaProyectos = new ArrayList<>();
        listaProyectos = consultas.getProject(user);
        model.addAttribute("listaproyectos",listaProyectos);
        return("/proyectos");
    }
    @GetMapping("/editar_proyecto/{id}")
    public String comentario(Model model, @PathVariable(value="id") String id) {
        return "editar_proyecto";
    }

}
