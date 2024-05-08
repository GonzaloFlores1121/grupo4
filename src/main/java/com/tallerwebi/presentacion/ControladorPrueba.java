package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

@Controller
@SessionAttributes("usuario")
public class ControladorPrueba {

    @RequestMapping("/irsaludo")
    public ModelAndView saludar(){

        ModelMap modelo = new ModelMap();
        Usuario user= new Usuario();
        modelo.put("dia","Jueves");
        modelo.put("mes","Abril");
        modelo.put("user",user);
    user.setEmail("panchovilla@gmail.com");
        return new ModelAndView("saludo",modelo);
    }
}
