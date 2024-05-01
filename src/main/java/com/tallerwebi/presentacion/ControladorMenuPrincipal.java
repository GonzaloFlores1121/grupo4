package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.Usuario;

import java.util.TreeMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorMenuPrincipal {

    @RequestMapping(value = "/actividadesFisicas",method = RequestMethod.GET)
    public ModelAndView irAEnForma(){

        return new ModelAndView("actividadesFisicas");
    }

    @RequestMapping(value = "/ejercicio",method = RequestMethod.GET)
    public ModelAndView irAEjercicio(){

        return new ModelAndView("ejercicio");
    }

    @RequestMapping(value = "/recetas",method = RequestMethod.GET)
    public ModelAndView irARecetas(){

        return new ModelAndView("recetas");
    }

    @RequestMapping(value = "/diarioEjercicio",method = RequestMethod.GET)
    public ModelAndView irADiarioEjercicio(){

        return new ModelAndView("diarioEjercicio");
    }

    //probando un poco lo del modelo 
    @RequestMapping(path="/perfilUsuario", method=RequestMethod.GET)
    public ModelAndView perfilUsuario() {
        ModelMap modelo = new ModelMap();
        modelo.put("usuario", "admin");
        modelo.put("contraseña", "1234");
        modelo.put("email", "admin1234@gmail.com");
        modelo.put("edad", 25);
        modelo.put("genero", "Masculino");
        modelo.put("altura", 155);
        modelo.put("peso", 45);
        modelo.put("nivelDeActividadFisica", "Sedentaria");
        modelo.put("dietaObjetivo", "Mantener mi peso actual");
        modelo.put("pesoMeta", 45);
        return new ModelAndView("perfilUsuario", modelo);
    }

    //viendo que tal va con un map a posteriori estas weas se borraran, pero solo para ir viendo :v
    @RequestMapping(path="/notificaciones", method=RequestMethod.GET) 
    public ModelAndView notificaciones() {
        ModelMap modelo = new ModelMap();
        Map<String, String> notificaciones = new TreeMap<>();
        notificaciones.put("Control Peso", "A las 8 a.m. debes controlar tu peso.");
        notificaciones.put("Alimentacion", "No olvides que a las 12 a.m. debe almorzar de manera saludable.");
        notificaciones.put("Mantenimiento", "Desde las 8 p.m. a las 10 p.m. la aplicacion se hallara en mantenimiento gracias por su comprension.");
        notificaciones.put("Novedades", "No olvides leer el parche con las nuevas actualizaciones.");
        modelo.addAttribute("notificaciones", notificaciones);
        return new ModelAndView("notificaciones", modelo);
    }

}
