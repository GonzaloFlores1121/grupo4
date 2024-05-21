package com.tallerwebi.presentacion;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ConfiguracionUsuario;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.AlturaIncorrectaException;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.dominio.excepcion.EdadInvalidaException;
import com.tallerwebi.dominio.excepcion.PesoIncorrectoException;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;

@Controller
public class ControladorPerfil {

    ServicioLogin servicioLogin;

    @Autowired
    public ControladorPerfil(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
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
    public ModelAndView procesarImagen(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) throws UsuarioExistente, DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException {
        if(usuario != null) {
            HttpSession session = request.getSession();
            Usuario usuarioLogin = (Usuario) session.getAttribute("usuario");
            Usuario usuarioBuscado = servicioLogin.buscar(usuarioLogin.getEmail());
            usuarioBuscado.setImagen(usuario.getImagen());
            servicioLogin.modificarPerfil(usuarioBuscado, usuarioBuscado.getEmail());
            session.setAttribute("usuario", usuarioBuscado);
            return new ModelAndView("redirect:/perfilUsuario");
        }
        return new ModelAndView("redirect:/inicio");
    }

    @RequestMapping(path = "formularioEditarPerfil", method = RequestMethod.GET)
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

    @RequestMapping(path = "/procesarDatos", method = RequestMethod.POST)
    public ModelAndView procesarDatos(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        List<String> errores = new ArrayList<String>();
        try {
            if(usuario != null) {            
                HttpSession session = request.getSession();
                Usuario usuarioLogin = (Usuario) session.getAttribute("usuario");
                Usuario usuarioBuscado = servicioLogin.buscar(usuarioLogin.getEmail());
                usuarioBuscado.setNombre(usuario.getNombre());
                usuarioBuscado.setEmail(usuario.getEmail());
                usuarioBuscado.setPassword(usuario.getPassword());
                usuarioBuscado.setEdad(usuario.getEdad());
                usuarioBuscado.setGenero(usuario.getGenero());
                usuarioBuscado.setAltura(usuario.getAltura());
                usuarioBuscado.setPeso(usuario.getPeso());
                usuarioBuscado.setMetaAlcanzarPeso(usuario.getMetaAlcanzarPeso());
                usuarioBuscado.setNivelDeActividad(usuario.getNivelDeActividad());
                servicioLogin.modificarPerfil(usuarioBuscado, usuario.getEmail());
                session.setAttribute("usuario", usuarioBuscado);
                return new ModelAndView("redirect:/perfilUsuario");
            }
            
        } catch (UsuarioExistente e) {
            errores.add("El usuario ya existe.");
        } catch (AlturaIncorrectaException e) {
            errores.add("La altura debe ser mayor a 0 y menor a 3 metros.");
        } catch (EdadInvalidaException e) {
            errores.add("La edad debe ser entre 12 y 100 años.");
        } catch (PesoIncorrectoException e) {
            errores.add("El peso debe ser mayor a 0 y menor a 500kg");
        } catch (Exception e) {
            errores.add("Error al registrar el nuevo usuario");
        }

        if (!errores.isEmpty()) {
            model.put("errores", errores);
            return new ModelAndView("formularioEditarPerfil", model);
        }

        return new ModelAndView("redirect:/inicio");
    }

    @RequestMapping(path = "/configuracion", method = RequestMethod.GET)
    public ModelAndView configuracion(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario!=null && usuario.getConfiguracionUsuario() != null) {
            model.addAttribute("usuario", usuario);
            model.addAttribute("configuracionUsuario", usuario.getConfiguracionUsuario());
            return new ModelAndView("configuracion", model);
        }
        return new ModelAndView("redirect:/inicio");
    }

    @RequestMapping(path = "/guardarConfiguracion", method = RequestMethod.POST)
    public ModelAndView guardarConfiguracion(@ModelAttribute("usuario") Usuario usuario, @ModelAttribute("configuracionUsuario") ConfiguracionUsuario configuracionUsuario, 
    HttpServletRequest request) throws UsuarioExistente, DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException {
        if(usuario != null && configuracionUsuario !=null) {
            HttpSession session = request.getSession();
            Usuario usuarioLogin = (Usuario) session.getAttribute("usuario");
            Usuario usuarioBuscado = servicioLogin.buscar(usuarioLogin.getEmail());
            usuarioBuscado.getConfiguracionUsuario().setRecibirNotificaciones(configuracionUsuario.getRecibirNotificaciones());
            usuarioBuscado.getConfiguracionUsuario().setUnidadEnergia(configuracionUsuario.getUnidadEnergia());
            usuarioBuscado.getConfiguracionUsuario().setUnidadMasa(configuracionUsuario.getUnidadMasa());
            servicioLogin.modificarPerfil(usuarioBuscado, usuarioBuscado.getEmail());
            session.setAttribute("usuario", usuarioBuscado);
            return new ModelAndView("redirect:/perfilUsuario");
        }
        return new ModelAndView("redirect:/inicio");
    }

    //en un rato lo cambio
    @RequestMapping(path = "/notificaciones", method = RequestMethod.GET) 
    public ModelAndView notificaciones(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        ModelMap modelo = new ModelMap();
        Map<String, String> notificaciones = new TreeMap<>();
        notificaciones.put("Control Peso", "A las 8 a.m. debes controlar tu peso.");
        notificaciones.put("Alimentacion", "No olvides que a las 12 a.m. debe almorzar de manera saludable.");
        notificaciones.put("Mantenimiento", "Desde las 8 p.m. a las 10 p.m. la aplicacion se hallara en mantenimiento gracias por su comprension.");
        notificaciones.put("Novedades", "No olvides leer el parche con las nuevas actualizaciones.");
        modelo.addAttribute("notificaciones", notificaciones);
        modelo.addAttribute("usuario", usuario);
        return new ModelAndView("notificaciones", modelo);
    }

}
