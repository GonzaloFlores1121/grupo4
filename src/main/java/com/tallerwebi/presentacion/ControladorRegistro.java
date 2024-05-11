package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.dominio.DatosLogin;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorRegistro {


    private ServicioDatosUsuario servicioDatosUsuario;
    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorRegistro(ServicioDatosUsuario servicioDatosUsuario,ServicioLogin servicioLogin) {
        this.servicioDatosUsuario = servicioDatosUsuario;
        this.servicioLogin=servicioLogin;
    }
    @RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public ModelAndView irAInicio() {
        return new ModelAndView("inicio");
    }

    @RequestMapping(value = "/formulario-registro", method = RequestMethod.GET)
    public ModelAndView irAFormulario() {
        ModelMap modelo = new ModelMap();
        modelo.put("usuario", new Usuario());
        return new ModelAndView("formulario-registro", modelo);
    }
    @RequestMapping(value = "/iniciar-sesion", method = RequestMethod.GET)
    public ModelAndView irAInicioSesion() {
        return new ModelAndView("iniciar-sesion");
    }

    @RequestMapping(path = "/validarLogin", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(usuario.getEmail(), usuario.getPassword());
        if (usuarioBuscado != null) {
            return new ModelAndView("redirect:/menuprincipal");
        }
            model.put("error", "Usuario o clave incorrecta");

        return new ModelAndView("iniciar-sesion", model);
    }


    @RequestMapping(value = "/menuprincipal", method = RequestMethod.GET)
    public ModelAndView irAlMenuPrincipal(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            modelo.put("nombre",usuario.getNombre() );
        } else {
            modelo.put("nombre", "Usuario");
        }
        return new ModelAndView("menuprincipal",modelo);
    }

    @RequestMapping(value = "/enviarFormulario", method = RequestMethod.POST)
    public ModelAndView enviarFormulario(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        try {
            servicioLogin.registrarUsuario(usuario);
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
        } catch (DatosIncorrectos e) {
            model.put("error", "Los datos del usuario son incorrectos");
            ModelAndView modelAndView = new ModelAndView("formulario-registro", model);
            return modelAndView;
        } catch (UsuarioExistente e) {
            model.put("error", "El usuario ya existe");
        }
        return new ModelAndView("redirect:/iniciar-sesion");
    }
}