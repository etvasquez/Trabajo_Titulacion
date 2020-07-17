package ec.edu.utpl.TrabajoTitulacion.View;

import ec.edu.utpl.TrabajoTitulacion.Controller.consultasBD;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String homePage(Model model) {
        return "home";
    }
}
