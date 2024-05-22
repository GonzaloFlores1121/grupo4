package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.infraestructura.ServicioCalendarioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("usuario")
public class ControladorCalendario {

    private final ServicioCalendarioImpl servicioCalendarioImpl;
    private RepositorioEjercicio repositorioEjercicio;
    private RepositorioEjercicioUsuario repositorioEjercicioUsuario;
    private RepositorioUsuario repositorioUsuario;


    @Autowired
    public ControladorCalendario (RepositorioEjercicio repositorioEjercicio , RepositorioEjercicioUsuario repositorioEjercicioUsuario, RepositorioUsuario repositorioUsuario, ServicioCalendarioImpl servicioCalendarioImpl){
        this.repositorioEjercicio = repositorioEjercicio;
        this.repositorioEjercicioUsuario = repositorioEjercicioUsuario;
        this.repositorioUsuario = repositorioUsuario;
        this.servicioCalendarioImpl = servicioCalendarioImpl;
    }

    @RequestMapping(value = "/calendarioDieta",method = RequestMethod.GET)
    public ModelAndView irAMiCalendarioDieta(HttpServletRequest request){
        ModelMap model = new ModelMap();
        obtenerUsuarioSession(request, model);
        try {
           Map<Date,Calendario> fechas = servicioCalendarioImpl.obtenerFechasCalendario();
            model.put("listaFechas", fechas);
            return new ModelAndView("calendarioDieta", model);
        }catch (Exception e){
            model.put("error", "Ninguna Activida fue encontrada aun :(");
            return new ModelAndView("calendarioDieta", model);
        }

    }

    private void obtenerUsuarioSession(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
    }

}
