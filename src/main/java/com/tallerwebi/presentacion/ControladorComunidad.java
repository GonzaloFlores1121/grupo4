package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Publicacion;
import com.tallerwebi.dominio.PublicacionLike;
import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.ServicioFollow;
import com.tallerwebi.dominio.ServicioLike;
import com.tallerwebi.dominio.ServicioNotificacion;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.UsuarioFollow;
import com.tallerwebi.dominio.excepcion.PublicacionNoExistente;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;

import org.springframework.beans.factory.annotation.Autowired;
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
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ControladorComunidad {

    private ServicioComunidad servicioComunidad;
    private ServicioFollow servicioFollow;
    private ServicioLike servicioLike;
    private ServicioNotificacion servicioNotificacion;

    @Autowired
    public ControladorComunidad(ServicioComunidad servicioComunidad, ServicioFollow servicioFollow, ServicioLike servicioLike, ServicioNotificacion servicioNotificacion) {
        this.servicioComunidad = servicioComunidad;
        this.servicioFollow = servicioFollow;
        this.servicioLike = servicioLike;
        this.servicioNotificacion = servicioNotificacion;
    }

    @Transactional
    @RequestMapping(value = "/comunidad", method = RequestMethod.GET)
    public ModelAndView irAComunidad(HttpServletRequest request) throws UsuarioNoExistente, PublicacionNoExistente {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null) {return new ModelAndView("redirect:/inicio");}
        ModelMap model = new ModelMap();
        model.addAttribute("usuario", usuario);
        List<Publicacion> publicaciones = servicioComunidad.todasLasPublicacionesSubidas();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        for (Publicacion publicacion : publicaciones) {
            publicacion.setFechaFormateada(publicacion.getFechaHora().format(formatter));
        }
        model.put("publicaciones", publicaciones);
        Map<Long, Boolean> likes = this.obtenerMapaDeLikesPorUsuario(usuario.getId());
        model.addAttribute("likes", likes);
        Map<Long, String> likesList = obtenerListaDeLikesPorPublicacion(publicaciones);
        model.addAttribute("likesList", likesList);
        return new ModelAndView("comunidad", model);
    }

    private Map<Long, Boolean> obtenerMapaDeLikesPorUsuario(Long idUsuario) throws UsuarioNoExistente {
        Map<Long, Boolean> likes = new HashMap<>();
        List<Publicacion> likedPublicaciones = servicioLike.obtenerTodosLosLikePorUsuario(idUsuario);
        for (Publicacion publicacion : likedPublicaciones) {
            likes.put(publicacion.getId(), true);
        }
        return likes;
    }
    
    private Map<Long, String> obtenerListaDeLikesPorPublicacion(List<Publicacion> publicaciones) throws PublicacionNoExistente {
        Map<Long, String> likesList = new HashMap<>();
        for (Publicacion publicacion : publicaciones) {
            List<Usuario> likes = servicioLike.obtenerTodosLosLikesPorPublicacion(publicacion.getId());
            String usuarios = "";
            for (int i = 0; i < likes.size(); i++) {
                if (i > 0) {usuarios += "</br>";}
                usuarios += likes.get(i).getNombre();
            }
            likesList.put(publicacion.getId(), usuarios);
        }
        return likesList;
    }

    @Transactional
    @RequestMapping(value = "/darMeGusta", method = RequestMethod.POST)
    public ModelAndView darMeGusta(HttpServletRequest request, @RequestParam Long publicacionId) throws PublicacionNoExistente, UsuarioNoExistente {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        Publicacion publicacion = servicioComunidad.obtenerPublicacionPorId(publicacionId);
        servicioLike.like(publicacion, usuario);
        String titulo = "Publicacion";
        String contenido = usuario.getNombre() +  " le dio me gusta a tu publicacion.";         
        servicioNotificacion.enviarNotificacion(titulo, contenido, LocalDateTime.now(), publicacion.getUsuario().getId());
        return new ModelAndView("redirect:/comunidad");
    }

    @Transactional
    @RequestMapping(value = "/quitarMeGusta", method = RequestMethod.POST)
    public ModelAndView quitarMeGusta(HttpServletRequest request, @RequestParam Long publicacionId) throws PublicacionNoExistente, UsuarioNoExistente {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        PublicacionLike like = servicioLike.obtenerLike(publicacionId, usuario.getId());
        servicioLike.unlike(like);
        return new ModelAndView("redirect:/comunidad");
    }

    @RequestMapping(value = "/subirPublicacion")
    public ModelAndView subirPublicacion(HttpServletRequest request) throws IOException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null) {return new ModelAndView("redirect:/inicio");}
        ModelMap model = new ModelMap();
        model.addAttribute("usuario", usuario);
        return new ModelAndView("subirPublicacion", model);
    }

    @RequestMapping(value = "/guardarPublicacion", method = RequestMethod.POST)
    public ModelAndView guardarPublicacion(@ModelAttribute("publicacion") Publicacion publicacion,
                                           @RequestParam("imagen") MultipartFile imagenFile,
                                           HttpSession session) throws IOException {
        ModelMap modelo = new ModelMap();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        if (!imagenFile.isEmpty()) {
            String directorioImagenes = "src/main/webapp/resources/core/img/publicaciones";
            Files.createDirectories(Paths.get(directorioImagenes));
            String nombreImagen = LocalDateTime.now().toString().replaceAll("[:.]", "-") + "_" + imagenFile.getOriginalFilename();
            String rutaCompleta = Paths.get(directorioImagenes, nombreImagen).toString();
            imagenFile.transferTo(new File(rutaCompleta));
            servicioComunidad.subirPublicacion(usuario, nombreImagen, publicacion.getTexto());
            return new ModelAndView("redirect:/comunidad");
        } else {
            modelo.addAttribute("error", "No se proporcionó una imagen válida");
            return new ModelAndView("subirPublicacion", modelo);
        }       
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
   
    @Transactional
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
        Map<Long, Boolean> likes = this.obtenerMapaDeLikesPorUsuario(usuario.getId());
        model.addAttribute("likes", likes);
        return new ModelAndView("publicacionesUsuario", model);
    }

    @Transactional
    @RequestMapping(value = "/favoritos", method = RequestMethod.GET)
    public ModelAndView favoritos(HttpServletRequest request) throws UsuarioNoExistente {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario == null) {return new ModelAndView("redirect:/inicio");}
        ModelMap model = new ModelMap();
        model.addAttribute("usuario", usuario);
        List<Usuario> seguidores = servicioFollow.obtenerTodosLosFollows(usuario.getId());
        model.addAttribute("seguidores", seguidores);
        List<Usuario> seguidos = servicioFollow.obtenerTodosMisFollows(usuario.getId());
        model.addAttribute("seguidos", seguidos);
        return new ModelAndView("favoritos", model);
    }

}
