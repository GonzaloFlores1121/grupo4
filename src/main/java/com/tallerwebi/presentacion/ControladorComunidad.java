package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
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
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Controller
public class ControladorComunidad {

    private ServicioComunidad servicioComunidad;
    private ServicioFollow servicioFollow;
    private ServicioNotificacion servicioNotificacion;
    private ServicioDesafio servicioDesafio;
    private RepositorioDesafioUsuario repositorioDesafioUsuario;

    @Autowired
    public ControladorComunidad(ServicioComunidad servicioComunidad, ServicioFollow servicioFollow, ServicioNotificacion servicioNotificacion, ServicioDesafio servicioDesafio, RepositorioDesafioUsuario repositorioDesafioUsuario) {
        this.servicioComunidad = servicioComunidad;
        this.servicioFollow = servicioFollow;
        this.servicioNotificacion = servicioNotificacion;
        this.servicioDesafio = servicioDesafio;
        this.repositorioDesafioUsuario = repositorioDesafioUsuario;
    }

    @Transactional
    @RequestMapping(value = "/comunidad", method = RequestMethod.GET)
    public ModelAndView irAComunidad(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        Usuario usuario = this.setUsuario(request, model);
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        model.addAttribute("nuevaPublicacion", new Publicacion());
        model.addAttribute("nuevaRespuesta", new Respuesta());
        List<Publicacion> publicaciones = servicioComunidad.obtenerPublicacionesSubidas();
        model.addAttribute("publicaciones", publicaciones);
        Map<Long, List<Respuesta>> respuestas = servicioComunidad.obtenRespuestasPorPublicacionSubida(publicaciones);
        model.addAttribute("respuestas", respuestas);
        Map<Long, Boolean> likes = servicioComunidad.obtenerLikesPorUsuario(usuario.getId());
        model.addAttribute("likes", likes);
        Map<Long, String> likesLista = servicioComunidad.obtenerLikesPorPublicacion(publicaciones);
        model.addAttribute("likesLista", likesLista);
        List<Desafio> desafios = servicioDesafio.obtenerTodosDesafios();
        model.addAttribute("usuario", usuario);
        model.addAttribute("desafios", desafios);
        return new ModelAndView("comunidad", model);
    }

    @Transactional
    @RequestMapping(value = "/buscarPublicacion", method = RequestMethod.GET)
    public ModelAndView buscarPubliacion(HttpServletRequest request, @RequestParam(value = "busqueda", required = false) String tituloBuscado) {
        ModelMap model = new ModelMap();
        Usuario usuario = this.setUsuario(request, model);
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        model.addAttribute("nuevaPublicacion", new Publicacion());
        model.addAttribute("nuevaRespuesta", new Respuesta());
        List<Publicacion> publicaciones = servicioComunidad.obtenerPublicacionesSubidasPorBusqueda(tituloBuscado);
        if (publicaciones.isEmpty()) {
            publicaciones = servicioComunidad.obtenerPublicacionesSubidas();
            model.addAttribute("noEncontrado", "No se encontraron publicaciones con los datos ingresados.");
        }
        model.addAttribute("publicaciones", publicaciones);
        Map<Long, List<Respuesta>> respuestas = servicioComunidad.obtenRespuestasPorPublicacionSubida(publicaciones);
        model.addAttribute("respuestas", respuestas);
        Map<Long, Boolean> likes = servicioComunidad.obtenerLikesPorUsuario(usuario.getId());
        model.addAttribute("likes", likes);
        Map<Long, String> likesLista = servicioComunidad.obtenerLikesPorPublicacion(publicaciones);
        model.addAttribute("likesLista", likesLista);
        return new ModelAndView("comunidad", model);        
    }

    @Transactional
    @RequestMapping(value = "/guardarPublicacion", method = RequestMethod.POST)
    public ModelAndView guardarPublicacion(HttpServletRequest request, @ModelAttribute("publicacion") Publicacion publicacion, @RequestParam("imagen") MultipartFile imagenFile) throws IOException {
        ModelMap model = new ModelMap();
        Usuario usuario = this.setUsuario(request, model);
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        if (!imagenFile.isEmpty()) {
            servicioComunidad.subirPublicacion(usuario, publicacion.getTitulo(), publicacion.getContenido(), imagenFile);
            int publicacionesContador = servicioComunidad.obtenerPublicacionesSubidasPorUsuario(usuario.getId()).size();
            if (publicacionesContador == 3) {
                List<Desafio> desafios = servicioDesafio.obtenerDesafiosEnCurso(usuario.getId());
                for (Desafio desafio : desafios) {
                    if(desafio.getNombre() == "Consigue 3 seguidores");
                    servicioDesafio.completarDesafio(desafio.getId(), usuario.getId());
                }
            }
            return new ModelAndView("redirect:/comunidad");
        } else {
            model.addAttribute("error", "No se proporciono una imagen valida");
            return new ModelAndView("subirPublicacion", model);
        }       
    }

    @Transactional
    @RequestMapping(value = "/guardarRespuesta", method = RequestMethod.POST)
    public ModelAndView guardarRespuesta(HttpServletRequest request, @ModelAttribute("respuesta") Respuesta respuesta, @RequestParam("idPublicacion") Long idPublicacion, @RequestParam("busquedaRespuesta") String tituloBuscado) throws PublicacionNoExistente {
        Usuario usuario = this.setUsuario(request);
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        Publicacion publicacion = servicioComunidad.obtenerPublicacionPorId(idPublicacion);
        servicioComunidad.responderPublicacion(publicacion, usuario, respuesta.getContenido());
        if (tituloBuscado != null && !tituloBuscado.isEmpty()) {return new ModelAndView("redirect:/buscarPublicacion?busqueda=" +  tituloBuscado); }
        return new ModelAndView("redirect:/comunidad");
    }

    @Transactional
    @RequestMapping(value = "/darMeGusta", method = RequestMethod.POST)
    public ModelAndView darMeGusta(HttpServletRequest request, @RequestParam Long idPublicacion) throws PublicacionNoExistente, UsuarioNoExistente {
        Usuario usuario = this.setUsuario(request);
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        Publicacion publicacion = servicioComunidad.obtenerPublicacionPorId(idPublicacion);
        servicioComunidad.darLike(publicacion, usuario);
        String titulo = "Publicacion";
        String contenido = usuario.getNombre() +  " le dio me gusta a tu publicacion " + publicacion.getTitulo() + ".";         
        servicioNotificacion.enviarNotificacion(titulo, contenido, LocalDateTime.now(), publicacion.getUsuario().getId());
        return new ModelAndView("redirect:/comunidad");
    }

    @Transactional
    @RequestMapping(value = "/quitarMeGusta", method = RequestMethod.POST)
    public ModelAndView quitarMeGusta(HttpServletRequest request, @RequestParam Long idPublicacion) throws PublicacionNoExistente, UsuarioNoExistente {
        Usuario usuario = this.setUsuario(request);
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        PublicacionLike like = servicioComunidad.obtenerLikePorIdPublicacionYUsuario(idPublicacion, usuario.getId());
        servicioComunidad.quitarLike(like);
        Publicacion publicacion = servicioComunidad.obtenerPublicacionPorId(idPublicacion);
        String titulo = "Publicacion";
        String contenido = usuario.getNombre() +  " le quito el me gusta a tu publicacion ." + publicacion.getTitulo() + ".";         
        servicioNotificacion.enviarNotificacion(titulo, contenido, LocalDateTime.now(), publicacion.getUsuario().getId());
        return new ModelAndView("redirect:/comunidad");
    }

    @Transactional
    @RequestMapping(value = "/perfilComunidad/{id}",method = RequestMethod.GET)
    public ModelAndView mostrarPerfilComunidad(HttpServletRequest request, @PathVariable Long id) throws UsuarioNoExistente {
        ModelMap model = new ModelMap();
        Usuario usuario = this.setUsuario(request, model);
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        Usuario usuarioComunidad = servicioComunidad.obtenerUsuarioPorId(id);
        model.addAttribute("usuarioComunidad", usuarioComunidad);
        if (usuarioComunidad.getId().equals(usuario.getId())) {return new ModelAndView("redirect:/perfilUsuario");}
        UsuarioFollow follow = servicioFollow.obtenerFollow(usuarioComunidad.getId(), usuario.getId());
        model.addAttribute("follow", follow);
        return new ModelAndView("perfilComunidad", model);
    }

    @Transactional
    @RequestMapping(value = "/seguir", method = RequestMethod.POST)
    public ModelAndView seguir(HttpServletRequest request, @RequestParam Long idUsuario) throws UsuarioNoExistente {
        Usuario usuario = this.setUsuario(request);      
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        Usuario usuarioComunidad = servicioComunidad.obtenerUsuarioPorId(idUsuario);
        servicioFollow.follow(usuarioComunidad, usuario);
        String titulo = "Seguidores";
        String contenido = usuario.getNombre() + " te esta siguiendo.";         
        servicioNotificacion.enviarNotificacion(titulo, contenido, LocalDateTime.now(), usuarioComunidad.getId());
        return new ModelAndView("redirect:/perfilComunidad/" + idUsuario);    
    }

    @Transactional
    @RequestMapping(value = "/dejarDeSeguir", method = RequestMethod.POST)
    public ModelAndView dejarDeSeguir(HttpServletRequest request, @RequestParam Long idUsuario) throws UsuarioNoExistente {
        Usuario usuario = this.setUsuario(request);        
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        UsuarioFollow follow = servicioFollow.obtenerFollow(idUsuario, usuario.getId());
        servicioFollow.unfollow(follow);
        Usuario usuarioComunidad = servicioComunidad.obtenerUsuarioPorId(idUsuario);
        String titulo = "Seguidores";
        String contenido = usuario.getNombre() + " dejo de seguirte.";         
        servicioNotificacion.enviarNotificacion(titulo, contenido, LocalDateTime.now(), usuarioComunidad.getId());
        return new ModelAndView("redirect:/perfilComunidad/" + idUsuario);  
    }
   
    @Transactional
    @RequestMapping(value = "/publicacionesUsuario/{id}", method = RequestMethod.GET)
    public ModelAndView irAPublicaciones(@PathVariable Long id, HttpServletRequest request) throws UsuarioNoExistente {       
        ModelMap model = new ModelMap();
        Usuario usuarioSession = this.setUsuario(request, model);
        if (usuarioSession == null) {return new ModelAndView("redirect:/inicio");}
        Usuario usuario = servicioComunidad.obtenerUsuarioPorId(id);
        model.addAttribute("usuarioComunidad", usuario);
        model.addAttribute("nuevaRespuesta", new Respuesta());
        List<Publicacion> publicaciones = servicioComunidad.obtenerPublicacionesSubidasPorUsuario(id);
        model.addAttribute("publicaciones", publicaciones);
        Map<Long, List<Respuesta>> respuestas = servicioComunidad.obtenRespuestasPorPublicacionSubida(publicaciones);
        model.addAttribute("respuestas", respuestas);
        Map<Long, Boolean> likes = servicioComunidad.obtenerLikesPorUsuario(usuario.getId());
        model.addAttribute("likes", likes);
        Map<Long, String> likesLista = servicioComunidad.obtenerLikesPorPublicacion(publicaciones);
        model.addAttribute("likesLista", likesLista);
        return new ModelAndView("publicacionesUsuario", model);
    }

    @Transactional
    @RequestMapping(value = "/guardarRespuestaDesdePublicacion", method = RequestMethod.POST)
    public ModelAndView guardarRespuestaDesdePublicacion(HttpServletRequest request, @ModelAttribute("respuesta") Respuesta respuesta, @RequestParam("idPublicacion") Long idPublicacion, @RequestParam("busquedaRespuesta") String tituloBuscado) throws PublicacionNoExistente {
        Usuario usuario = this.setUsuario(request);
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        Publicacion publicacion = servicioComunidad.obtenerPublicacionPorId(idPublicacion);
        servicioComunidad.responderPublicacion(publicacion, usuario, respuesta.getContenido());
        if (tituloBuscado != null && !tituloBuscado.isEmpty()) {return new ModelAndView("redirect:/buscarPublicacion?busqueda=" +  tituloBuscado); }
        return new ModelAndView("redirect:/publicacionUsuario/" + publicacion.getUsuario().getId());
    }

    @Transactional
    @RequestMapping(value = "/darMeGustaDesdeUsuario", method = RequestMethod.POST)
    public ModelAndView darMeGustaDesdeUsuario(HttpServletRequest request, @RequestParam Long idPublicacion) throws PublicacionNoExistente, UsuarioNoExistente {
        Usuario usuario = this.setUsuario(request);
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        Publicacion publicacion = servicioComunidad.obtenerPublicacionPorId(idPublicacion);
        servicioComunidad.darLike(publicacion, usuario);
        String titulo = "Publicacion";
        String contenido = usuario.getNombre() + " le dio me gusta a tu publicacion " + publicacion.getTitulo() + ".";         
        servicioNotificacion.enviarNotificacion(titulo, contenido, LocalDateTime.now(), publicacion.getUsuario().getId());
        return new ModelAndView("redirect:/publicacionesUsuario/" + publicacion.getUsuario().getId());
    }

    @Transactional
    @RequestMapping(value = "/quitarMeGustaDesdeUsuario", method = RequestMethod.POST)
    public ModelAndView quitarMeGustaDesdeUsuario(HttpServletRequest request, @RequestParam Long idPublicacion) throws PublicacionNoExistente, UsuarioNoExistente {
        Usuario usuario = this.setUsuario(request);
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        PublicacionLike like = servicioComunidad.obtenerLikePorIdPublicacionYUsuario(idPublicacion, usuario.getId());
        servicioComunidad.quitarLike(like);
        Publicacion publicacion = servicioComunidad.obtenerPublicacionPorId(idPublicacion);
        String titulo = "Publicacion";
        String contenido = usuario.getNombre() + " le quito el me gusta a tu publicacion" + publicacion.getTitulo() + ".";         
        servicioNotificacion.enviarNotificacion(titulo, contenido, LocalDateTime.now(), publicacion.getUsuario().getId());
        return new ModelAndView("redirect:/publicacionesUsuario/" + publicacion.getUsuario().getId());
    }

    @Transactional
    @RequestMapping(value = "/seguidores", method = RequestMethod.GET)
    public ModelAndView seguidores(HttpServletRequest request) throws UsuarioNoExistente {
        ModelMap model = new ModelMap();
        Usuario usuario = this.setUsuario(request, model);
        if (usuario == null) {return new ModelAndView("redirect:/inicio");}
        List<Usuario> seguidores = servicioFollow.obtenerTodosLosFollows(usuario.getId());
        model.addAttribute("seguidores", seguidores);
        List<Usuario> seguidos = servicioFollow.obtenerTodosMisFollows(usuario.getId());
        model.addAttribute("seguidos", seguidos);
        return new ModelAndView("seguidores", model);
    }

    @Transactional
    @RequestMapping(value = "/unirseDesafio", method = RequestMethod.POST)
    public ModelAndView unirseDesafio(HttpServletRequest request, @RequestParam Long desafioId) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/inicio");
        }
        Desafio desafio = servicioDesafio.obtenerDesafioPorId(desafioId);
        if (desafio == null) {
            return new ModelAndView("redirect:/inicio"); // O manejar el error de otra manera
        }
        servicioDesafio.unirseADesafio(desafio.getId(), usuario.getId());
        return new ModelAndView("redirect:/comunidad");
    }

    @Transactional
    @RequestMapping(value = "/completarDesafio/{id}", method = RequestMethod.GET)
    public ModelAndView completarDesafio(HttpServletRequest request, @PathVariable Long id) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/inicio");
        }
        servicioDesafio.completarDesafio(id, usuario.getId());
        return new ModelAndView("redirect:/desafiosUsuario/" + usuario.getId());
    }

    @Transactional
    @RequestMapping(value = "/desafiosUsuario/{id}", method = RequestMethod.GET)
    public ModelAndView mostrarDesafiosUsuario(@PathVariable Long id, HttpServletRequest request) throws UsuarioNoExistente{
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/inicio");
        }
        ModelMap model = new ModelMap();
        model.addAttribute("usuario", usuario);
        String nombre = usuario.getNombre();
        model.put("nombre", nombre);
        List<Desafio> desafiosCompletos = servicioDesafio.obtenerDesafiosCompletados(id);
        List<Desafio> desafiosEnCurso = servicioDesafio.obtenerDesafiosEnCurso(id);
        model.addAttribute("desafiosCompletados", desafiosCompletos);
        model.addAttribute("desafiosEnProgreso", desafiosEnCurso);
        return new ModelAndView("desafiosUsuario", model);
    }


    private Usuario setUsuario(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        return usuario;
    }

    private Usuario setUsuario(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
        return usuario;
    }

}
