package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {

    @RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public ModelAndView irAInicio() {
        return new ModelAndView("inicio");
    }

    @RequestMapping(value = "/irAFormulario", method = RequestMethod.GET)
    public ModelAndView irAFormulario() {

        return new ModelAndView("formulario-registro");
    }

    @RequestMapping(value = "/menuprincipal", method = RequestMethod.GET)
    public ModelAndView irAlMenuPrincipal() {

        return new ModelAndView("menuprincipal");
    }
    @RequestMapping(value = "/enviarFormulario", method = RequestMethod.POST)
    public ModelAndView enviarFormulario(@RequestParam("nombre_miembro") String nombre, @RequestParam("contrasena") String contrasena, @RequestParam("correo_electronico") String correo_electronico) {

        return new ModelAndView("redirect:/menuprincipal");
    }


}