package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Publicacion;
import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.ServicioFollow;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.UsuarioFollow;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class ControladorComunidad {

    ServicioComunidad servicioComunidad;
    ServicioFollow servicioFollow;

    public ControladorComunidad(ServicioComunidad servicioComunidad, ServicioFollow servicioFollow) {
        this.servicioComunidad = servicioComunidad;
        this.servicioFollow = servicioFollow;
    }

    @RequestMapping(value = "/comunidad", method = RequestMethod.GET)
    public ModelAndView irAComunidad(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        HttpSession session = request.getSession();

        Usuario usuario = (Usuario) session.getAttribute("usuario");
        List<Publicacion> publicaciones = servicioComunidad.todasLasPublicacionesSubidas();

        modelo.put("publicaciones", publicaciones);

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

    @Transactional
    @RequestMapping(value = "/perfilComunidad/{id}",method = RequestMethod.GET)
    public ModelAndView mostrarPerfilComunidad(@PathVariable Long id, HttpServletRequest request) throws UsuarioNoExistente {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null) {return new ModelAndView("redirect:/inicio");}
        ModelMap model = new ModelMap();
        model.addAttribute("usuario", usuario);
        Usuario usuarioComunidad = servicioComunidad.obtenerUsuarioPorId(id);
        model.addAttribute("usuarioComunidad", usuarioComunidad);
        if(usuarioComunidad.getId().equals(usuario.getId())) {return new ModelAndView("redirect:/perfilUsuario");}
        UsuarioFollow follow = servicioFollow.obtenerFollow(usuarioComunidad.getId(), usuario.getId());
        model.put("follow", follow);
        return new ModelAndView("perfilComunidad", model);
    }

    @RequestMapping(value = "/seguir/{id}", method = RequestMethod.GET)
    public ModelAndView seguir(@PathVariable Long id, HttpServletRequest request) throws UsuarioNoExistente {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");        
        if(usuario == null) {return new ModelAndView("redirect:/inicio");}
        Usuario usuarioComunidad = servicioComunidad.obtenerUsuarioPorId(id);
        servicioFollow.follow(usuarioComunidad, usuario);
        return new ModelAndView("redirect:/perfilComunidad/"+id);    
    }

    @Transactional
    @RequestMapping(value = "/dejarDeSeguir/{id}", method = RequestMethod.GET)
    public ModelAndView dejarDeSeguir(@PathVariable Long id, HttpServletRequest request) throws UsuarioNoExistente {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");        
        if(usuario == null) {return new ModelAndView("redirect:/inicio");}
        UsuarioFollow follow = servicioFollow.obtenerFollow(id, usuario.getId());
        servicioFollow.unfollow(follow);
        return new ModelAndView("redirect:/perfilComunidad/"+id);  
    }
   
    @RequestMapping(value = "/publicacionesUsuario/{id}", method = RequestMethod.GET)
    public ModelAndView irAPublicaciones(@PathVariable Long id, HttpServletRequest request) throws UsuarioNoExistente {       
        HttpSession session = request.getSession();
        Usuario usuarioSession = (Usuario) session.getAttribute("usuario");
        if(usuarioSession == null) {return new ModelAndView("redirect:/inicio");}
        ModelMap model = new ModelMap();
        Usuario usuario = servicioComunidad.obtenerUsuarioPorId(id);
        String nombre = usuario.getNombre();
        model.put("nombre", nombre);
        List<Publicacion> publicaciones = servicioComunidad.todasLasPublicacionesSubidasPorUnUsuario(id);
        model.put("publicacionesUsuario", publicaciones);
        return new ModelAndView("publicacionesUsuario", model);
    }

    @Transactional
    @RequestMapping(value = "/listaSeguidores", method = RequestMethod.GET)
    public ModelAndView listaSeguidores(HttpServletRequest request) throws UsuarioNoExistente {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null) {return new ModelAndView("redirect:/inicio");}
        ModelMap model = new ModelMap();
        model.addAttribute("usuario", usuario);
        List<Usuario> seguidores = servicioFollow.obtenerTodosLosFollows(usuario.getId());
        model.addAttribute("seguidores", seguidores);
        List<Usuario> seguidos = servicioFollow.obtenerTodosMisFollows(usuario.getId());
        model.addAttribute("seguidos", seguidos);
        return new ModelAndView("listaSeguidores", model);
    }

}
