package ec.edu.utpl.TrabajoTitulacion.View;

import org.eclipse.jetty.client.api.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class PrivateController {

    /*@GetMapping("/usuario")
    public String infoUser(){
        return("/informacion_usuario");
    }
*/
    @GetMapping("/proyectos")
    public String proyectos(){
        return("/proyectos");
    }

}
