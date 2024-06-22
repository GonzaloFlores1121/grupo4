package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Publicacion;
import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ControladorComunidad {

    ServicioComunidad servicioComunidad;

    public ControladorComunidad(ServicioComunidad servicioComunidad) {
        this.servicioComunidad = servicioComunidad;
    }

    @RequestMapping(value = "/comunidad", method = RequestMethod.GET)
    public ModelAndView irAComunidad(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        HttpSession session = request.getSession();

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        List<Publicacion> publicaciones = servicioComunidad.todasLasPublicacionesSubidas();


        Map<String, Object> model = new HashMap<>();
        modelo.put("publicaciones", publicaciones);

        // Devolver la vista "comunidad" con el modelo
        return new ModelAndView("comunidad", modelo);
    }


    @RequestMapping(value = "/guardarPublicacion", method = RequestMethod.POST)
    public ModelAndView guardarPublicacion(@ModelAttribute("publicacion") Publicacion publicacion,
                                           @RequestParam("imagen") MultipartFile imagenFile,
                                           HttpSession session) throws IOException {

        ModelMap modelo = new ModelMap();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (!imagenFile.isEmpty()) {
            String directorioImagenes = "src/main/webapp/resources/core/img/publicaciones";
            Files.createDirectories(Paths.get(directorioImagenes));

            String nombreImagen = LocalDateTime.now().toString().replaceAll("[:.]", "-") + "_" + imagenFile.getOriginalFilename();
            String rutaCompleta = Paths.get(directorioImagenes, nombreImagen).toString();
            imagenFile.transferTo(new File(rutaCompleta));



            servicioComunidad.subirPublicacion(usuario, nombreImagen, publicacion.getTexto());
        } else {
            modelo.addAttribute("error", "No se proporcionó una imagen válida");
            return new ModelAndView("subirPublicacion", modelo); // Retornar a la vista con mensaje de error si no hay imagen
        }



        return new ModelAndView("redirect:/comunidad", modelo);
    }

    @RequestMapping(value = "/subirPublicacion")
    public ModelAndView subirPublicacion() throws IOException {

        return new ModelAndView("subirPublicacion");
    }
}
