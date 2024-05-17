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
    public ModelAndView irAMiCalendarioDieta(){
        ModelMap model = new ModelMap();
        try {
           Map<Date,Calendario> fechas = servicioCalendarioImpl.obtenerFechasCalendario();
            model.put("listaFechas", fechas);
            return new ModelAndView("calendarioDieta", model);
        }catch (Exception e){
            model.put("error", "Ninguna Activida fue encontrada aun :(");
            return new ModelAndView("calendarioDieta", model);
        }

    }

}
