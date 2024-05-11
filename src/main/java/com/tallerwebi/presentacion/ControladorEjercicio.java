package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.ServicioEjercicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorEjercicio {

    private ServicioEjercicio servicioEjercicio;

    @Autowired
    public ControladorEjercicio(ServicioEjercicio servicioEjercicio){
        this.servicioEjercicio = servicioEjercicio;
    }
    @RequestMapping(value = "/actividadesFisicas",method = RequestMethod.GET)
    public ModelAndView irAEnForma(){
        ModelMap model = new ModelMap();
        List<Ejercicio> ejercicios = servicioEjercicio.obtenerTodosLosEjercicios();
        model.put("listaEjercicios", ejercicios); // Cambia "ejercicio" a "listaEjercicios"
        return new ModelAndView("actividadesFisicas", model);

    }
}
