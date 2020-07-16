package ec.edu.utpl.TrabajoTitulacion.Services;

import ec.edu.utpl.TrabajoTitulacion.Interfaces.IGraph;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.GET, RequestMethod.POST,RequestMethod.DELETE,RequestMethod.PUT})
@RequestMapping("/api/v1")
public class GraphServices {

    @Autowired
    private IGraph iGraph;

    @GetMapping("/GraphProjects")
    @ResponseStatus(HttpStatus.OK)
    public String getAll(){
        return iGraph.getGrapg();
    }

}
