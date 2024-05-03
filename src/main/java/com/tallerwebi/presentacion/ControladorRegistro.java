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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;


import javax.servlet.http.HttpServletRequest;

@Controller
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

    @RequestMapping(value = "/irAFormulario", method = RequestMethod.GET)
    public ModelAndView irAFormulario() {
        ModelAndView modelAndView = new ModelAndView("formulario-registro");
        modelAndView.addObject("datosLogin", new DatosLogin()); // Agrega datosLogin al modelo
        return modelAndView;
    }

    @RequestMapping(value = "/menuprincipal", method = RequestMethod.GET)
    public ModelAndView irAlMenuPrincipal() {

        return new ModelAndView("menuprincipal");
    }

    @RequestMapping(value = "/enviarFormulario", method = RequestMethod.POST)
    public ModelAndView enviarFormulario(@ModelAttribute("datosLogin") DatosLogin datosLogin, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        try {
            Usuario nuevoUsuario = new Usuario();
            nuevoUsuario.setEmail(datosLogin.getEmail());
            nuevoUsuario.setPassword(datosLogin.getPassword());
            nuevoUsuario.setGenero(datosLogin.getSexo());
            nuevoUsuario.setNivelDeActividad(datosLogin.getNivelDeActividad());
            nuevoUsuario.setPeso(datosLogin.getPeso());
            nuevoUsuario.setAltura(datosLogin.getAltura());
            nuevoUsuario.getEdad();
            servicioDatosUsuario.registrarUsuario(nuevoUsuario);
        } catch (DatosIncorrectos e) {
            ModelAndView modelAndView = new ModelAndView("redirect:/irAFormulario");
            modelAndView.addObject("error", "Error al registrar el usuario: " + e.getMessage());
            return modelAndView;
        }

        return new ModelAndView("redirect:/menuprincipal");
    }
}