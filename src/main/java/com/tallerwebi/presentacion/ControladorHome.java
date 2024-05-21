package com.tallerwebi.presentacion;

import javax.servlet.http.HttpSession;

import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.Usuario;

import java.util.TreeMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("usuario")
public class ControladorHome {


    private ServicioDatosUsuario servicioDatosUsuario;

    @Autowired
    public ControladorHome(ServicioDatosUsuario servicioDatosUsuario) {
        this.servicioDatosUsuario = servicioDatosUsuario;
    }

    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView irAlHome(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            modelo.put("nombre", usuario.getNombre());
        } else {
            modelo.put("nombre", "Usuario");
        }
        return new ModelAndView("home", modelo);
    }



    @RequestMapping(value = "/diarioEjercicio",method = RequestMethod.GET)
    public ModelAndView irADiarioEjercicio(){

        return new ModelAndView("diarioEjercicio");
    }
    @RequestMapping(value = "/diarioPeso",method = RequestMethod.GET)
    public ModelAndView irADiarioPeso(){
        return new ModelAndView("diarioPeso");
    }

    //probando un poco lo del modelo 
    @RequestMapping(path="/perfilUsuario", method=RequestMethod.GET)
    public ModelAndView perfilUsuario() {
        return new ModelAndView("perfilUsuario");
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

    @RequestMapping(path="/configuracion", method=RequestMethod.GET)
    public ModelAndView configuracion() {
        return new ModelAndView("configuracion");
    }

    @RequestMapping(path="/guardarConfiguracion", method=RequestMethod.POST)
    public ModelAndView guardarConfiguracion() {
        return new ModelAndView("redirect:/menuprincipal");
    }


    @RequestMapping(path="/mostrarNombreUsuario", method=RequestMethod.GET)
    public ModelAndView mostrarNombreUsuario(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            modelo.put("nombre", "hfukrgfuii");
        } else {
        modelo.put("nombre", "Nombre de usuario desconocido");
    }
        return new ModelAndView("home", modelo);
    }
}



