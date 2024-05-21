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



