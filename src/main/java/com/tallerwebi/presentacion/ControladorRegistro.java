package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorRegistro {

    @RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public String irAInicio() {
        return "inicio";
    }

    @RequestMapping(value = "/irAFormulario", method = RequestMethod.GET)
    public String irAFormulario() {
        return "formulario-registro";
    }
}