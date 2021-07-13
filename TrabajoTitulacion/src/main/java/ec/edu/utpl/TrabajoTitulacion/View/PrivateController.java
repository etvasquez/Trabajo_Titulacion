package ec.edu.utpl.TrabajoTitulacion.View;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import ec.edu.utpl.TrabajoTitulacion.Controller.consultasBD;
import ec.edu.utpl.TrabajoTitulacion.Controller.trasformacionRDF;
import ec.edu.utpl.TrabajoTitulacion.Model.Administracion;
import ec.edu.utpl.TrabajoTitulacion.Model.Objeto;
import ec.edu.utpl.TrabajoTitulacion.Model.Proyecto;
import ec.edu.utpl.TrabajoTitulacion.Model.Recurso;
import ec.edu.utpl.TrabajoTitulacion.storage.StorageFileNotFoundException;
import ec.edu.utpl.TrabajoTitulacion.storage.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Controller
public class PrivateController {
    private final StorageService storageService;
    private final consultasBD consultas = new consultasBD();
    private String localicacion="";

    @Autowired
    public PrivateController(StorageService storageService) {
        this.storageService = storageService;
    }

    @GetMapping("/proyectos")
    public String proyectos(HttpServletRequest request, Model model){
        String user = request.getRemoteUser().concat("@utpl.edu.ec");
        ArrayList<Proyecto> listaProyectos = consultas.getProject(user);
        model.addAttribute("listaproyectos",listaProyectos);
        return "proyectos";
    }

    @GetMapping("/eliminarRecurso/{id}")
    public String editarRecurso(@PathVariable(value="id") String id, Model model){
        Recurso recurso = consultas.getResouce(id);
        String estado = consultas.eliminarResource(recurso,id);
        model.addAttribute("tab",estado);
        return "redirect:/editar_proyecto/"+recurso.getId();
    }

    @GetMapping("/editar_proyecto/{id}" )
    public String comentario(Model model, @PathVariable(value="id") String id) {
        ArrayList<Recurso> list = consultas.getResouceProject(id);
        String appName = consultas.InformacionProyecto(id);
        String tipoProyecto = consultas.BusquedaPorTipoProyecto();
        Gson gson = new Gson();
        @SuppressWarnings("UnstableApiUsage") Type listType = new TypeToken<ArrayList<Objeto>>(){}.getType();
        ArrayList<Objeto> objeto = gson.fromJson(tipoProyecto, listType);
        Proyecto proyecto = gson.fromJson(appName, Proyecto.class);
        model.addAttribute("proyecto",proyecto);
        model.addAttribute("tipoProyecto",objeto);
        model.addAttribute("files",list);
        if(list.size()==0){
            model.addAttribute("existe","No existen recursos");
        }
        //model.addAttribute("tab",localicacion);
        return "editar_proyecto";
    }

    @PostMapping("/proyecto")
    public String submitForm(@ModelAttribute("proyecto") Proyecto proyecto, Model model) {
        localicacion= consultas.updateProject(proyecto);
        return "redirect:/editar_proyecto/"+proyecto.getId();
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
        Resource file = storageService.loadAsResource(filename);
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName,
                                   RedirectAttributes redirectAttributes, @RequestParam("id") String id,
                                   @RequestParam("descripcion") String descripcion, @RequestParam("licencia")
                                               String licencia, Model model) {
        String filename = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        int indexPunto = filename.indexOf( '.');
        String extension="";
        if(indexPunto!=-1){
            extension = filename.substring(indexPunto);
        }
        String baseUrl =
                ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString().concat("/files/").concat(fileName).concat(extension);
        Recurso recurso = new Recurso(fileName,baseUrl,filename,descripcion,licencia);
        storageService.store(file,fileName);
        String estado = consultas.insertResource(recurso,id,fileName);
        redirectAttributes.addFlashAttribute("message",
                "Archivo " + file.getOriginalFilename() + " subido correctamente!");
        localicacion=estado;
        return "redirect:/editar_proyecto/"+id;
    }

    @PostMapping("/uploadfile")
    public String FileUploadUpload(@RequestParam("file1") MultipartFile filedoc, @RequestParam("file2") MultipartFile filepro,
                                   RedirectAttributes redirectAttributes, @RequestParam("codificacion") String codificacion) throws IOException {
        String filename = StringUtils.cleanPath("data_docente-export");
        String filename1 = StringUtils.cleanPath("data-proyecto-export");
        String uuid = UUID.randomUUID().toString();
        System.out.println(filename);
        System.out.println(filename1);
        trasformacionRDF t = new trasformacionRDF();
        t.obtenerCSV(filename.concat(".csv"),filename1.concat(".csv"),codificacion);
        consultas.loadDataSet();
        Date objDate = new Date();
        String strDateFormat = "hh: mm: ss a dd-MMM-yyyy";
        SimpleDateFormat objSDF = new SimpleDateFormat(strDateFormat);
        Administracion administracion1 = new Administracion("archivo","-","-","admin",objSDF.format(objDate));
        consultas.insertActualizacion(administracion1,uuid);
        redirectAttributes.addFlashAttribute("message1",
                "Archivo " + filedoc.getOriginalFilename() + " subido correctamente!");
        redirectAttributes.addFlashAttribute("message2",
                "Archivo " + filepro.getOriginalFilename() + " subido correctamente!");
        return "redirect:/admin";
    }

    @ExceptionHandler(StorageFileNotFoundException.class)
    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
        return ResponseEntity.notFound().build();
    }

}
