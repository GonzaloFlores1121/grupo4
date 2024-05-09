package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.dominio.DatosLogin;
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
@SessionAttributes("usuario")
public class ControladorRegistro {


    private ServicioDatosUsuario servicioDatosUsuario;

    @Autowired
    public ControladorRegistro(ServicioDatosUsuario servicioDatosUsuario) {
        this.servicioDatosUsuario = servicioDatosUsuario;
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

    @RequestMapping(value ="/ingresarUsuario", method = RequestMethod.POST)
    public ModelAndView ingresarUsuario() {
        return new ModelAndView("redirect:/menuprincipal");
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
            servicioDatosUsuario.registrarUsuario(usuario);
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
        } catch (DatosIncorrectos e) {
            model.put("error", "Datos incorrectos");
            ModelAndView modelAndView = new ModelAndView("formulario-registro", model);
            return modelAndView;
        }
        return new ModelAndView("redirect:/menuprincipal");
    }
}