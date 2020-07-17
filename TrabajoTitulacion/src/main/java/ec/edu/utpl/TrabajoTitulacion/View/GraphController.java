package ec.edu.utpl.TrabajoTitulacion.View;

import ec.edu.utpl.TrabajoTitulacion.Controller.consultasBD;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class GraphController {

    String appName;

    @GetMapping("/grafo")
    public String homePage(Model model) {
        consultasBD consultas = new consultasBD();
        appName = consultas.getGrapg();
        model.addAttribute("appName", appName);
        return "grafo";
    }
}
