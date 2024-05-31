package com.tallerwebi.presentacion;

import javax.servlet.http.HttpSession;

import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.Usuario;

import com.tallerwebi.dominio.excepcion.PesoIncorrectoException;
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

        return new ModelAndView("estadisticasUsuario");
    }
    @RequestMapping(value = "/diarioPeso",method = RequestMethod.GET)
    public ModelAndView irADiarioPeso(){
        return new ModelAndView("diarioPeso");
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

    @RequestMapping(value = "/cambioPeso", method = RequestMethod.POST)
    public ModelAndView cambiarPeso(@ModelAttribute("nuevoPeso") Double nuevoPeso, HttpServletRequest request) throws PesoIncorrectoException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        try {
            servicioDatosUsuario.actualizarPeso(usuario, nuevoPeso);
            session.setAttribute("mensaje", "Su peso se ha actualizado correctamente");
        } catch (Exception e) {
            session.setAttribute("mensaje", "Su peso no se ha actualizado");
        }
        return new ModelAndView("redirect:/home");
    }
}



