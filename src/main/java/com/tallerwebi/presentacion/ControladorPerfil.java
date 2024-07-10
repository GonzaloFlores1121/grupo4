package com.tallerwebi.presentacion;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioNotificacion;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.AlturaIncorrectaException;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.dominio.excepcion.EdadInvalidaException;
import com.tallerwebi.dominio.excepcion.PesoIncorrectoException;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;

@Controller
public class ControladorPerfil {

    ServicioLogin servicioLogin;
    ServicioNotificacion servicioNotificacion;

    @Autowired
    public ControladorPerfil(ServicioLogin servicioLogin, ServicioNotificacion servicioNotificacion) {
        this.servicioLogin = servicioLogin;
        this.servicioNotificacion = servicioNotificacion;
    }

    @RequestMapping(path = "/perfilUsuario", method = RequestMethod.GET)
    public ModelAndView perfilUsuario(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Map<String, String> imagenes = Map.of(
            "avatar 1", "icono-perfil-1.png",
            "avatar 2", "icono-perfil-2.png",
            "avatar 3", "icono-perfil-3.png",
            "avatar 4", "icono-perfil-4.png",
            "avatar 5", "icono-perfil-5.png",
            "avatar 6", "icono-perfil-6.png",
            "avatar 7", "icono-perfil-7.png",
            "avatar 8", "icono-perfil-8.png"       
        );
        if(usuario != null) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("imagenes", imagenes);
            return new ModelAndView("perfilUsuario", model);
        }
        return new ModelAndView("redirect:/inicio");
    }

    @RequestMapping(path = "/procesarImagen", method = RequestMethod.POST)
    public ModelAndView procesarImagen(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) throws UsuarioExistente, DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException, UsuarioNoExistente {
        HttpSession session = request.getSession();
        Usuario usuarioLogin = (Usuario) session.getAttribute("usuario");        
        if(usuario != null && usuarioLogin != null) {
            Usuario usuarioBD = servicioLogin.buscarUsuario(usuarioLogin.getEmail());
            servicioLogin.modificarImagen(usuarioBD, usuario.getImagen());
            session.setAttribute("usuario", usuarioBD);
            return new ModelAndView("redirect:/perfilUsuario");
        }
        return new ModelAndView("redirect:/inicio");
    }

    @RequestMapping(path = "/formularioEditarPerfil", method = RequestMethod.GET)
    public ModelAndView formularioEditarPerfil(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario != null) {
            model.addAttribute("usuario", usuario);
            return new ModelAndView("formularioEditarPerfil", model);
        }
        return new ModelAndView("redirect:/inicio");
    }

    @Transactional
    @RequestMapping(path = "/procesarDatos", method = RequestMethod.POST)
    public ModelAndView procesarDatos(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        String error = null;
        HttpSession session = request.getSession();
        Usuario usuarioLogin = (Usuario) session.getAttribute("usuario");        
        try {
            if(usuario!=null && usuarioLogin!=null) {            
                Usuario usuarioBD = servicioLogin.buscarUsuario(usuarioLogin.getEmail());
                servicioLogin.modificarUsuario(usuarioBD, usuario);
                session.setAttribute("usuario", usuarioBD);
                return new ModelAndView("redirect:/perfilUsuario");
            }         
        } catch (UsuarioExistente e) {
            error = "El usuario ya existe.";
        } catch (AlturaIncorrectaException e) {
            error = "La altura debe ser mayor a 0 y menor a 3 metros.";
        } catch (EdadInvalidaException e) {
            error = "La edad debe ser entre 12 y 100 años.";
        } catch (PesoIncorrectoException e) {
            error = "El peso debe ser mayor a 0 y menor a 500kg.";
        } catch (Exception e) {
            error = "Fallo desconocida al registrar usuario.";
        }
        if (error != null) {
            model.addAttribute("error", error);
            return new ModelAndView("formularioEditarPerfil", model);
        }
        return new ModelAndView("redirect:/inicio");
    }

    @RequestMapping(path = "/notificaciones", method = RequestMethod.GET) 
    public ModelAndView notificaciones(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario != null) {

            model.addAttribute("usuario", usuario);
            List<Notificacion> notificaciones = servicioNotificacion.obtenerNotificaciones(usuario.getId());
            model.addAttribute("notificaciones", notificaciones);
            return new ModelAndView("notificaciones", model);
        }

        return new ModelAndView("redirect:/inicio");
    }

    @RequestMapping(path = "/eliminarNotificacion", method = RequestMethod.POST)
    public ModelAndView eliminarNotificacion(@RequestParam("idNotificacion") Long idNotificacion, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario != null) {
            servicioNotificacion.eliminarNotificacion(idNotificacion, usuario.getId());
            return new ModelAndView("redirect:/notificaciones");
        }
        return new ModelAndView("redirect:/inicio");
    }

}
