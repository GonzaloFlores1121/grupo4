package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Controller
public class ControladorEjercicio {

    private RepositorioEjercicio repositorioEjercicio;
    private RepositorioEjercicioUsuario repositorioEjercicioUsuario;
    private RepositorioUsuario repositorioUsuario;
    private ServicioEjercicio servicioEjercicio;

    @Autowired
    public ControladorEjercicio(RepositorioEjercicio repositorioEjercicio , RepositorioEjercicioUsuario repositorioEjercicioUsuario, RepositorioUsuario repositorioUsuario, ServicioEjercicio servicioEjercicio){
        this.repositorioEjercicio = repositorioEjercicio;
        this.repositorioEjercicioUsuario = repositorioEjercicioUsuario;
        this.repositorioUsuario = repositorioUsuario;
        this.servicioEjercicio = servicioEjercicio;
    }

    @RequestMapping(value = "/irAEjercicio", method = RequestMethod.POST)
    public ModelAndView irAEjercicio(@RequestParam("id") Long id) {
        ModelMap model = new ModelMap();
        Ejercicio ejercicio = repositorioEjercicio.obtenerEjercicioPorId(id);
        model.put("ejercicio", ejercicio);
        return new ModelAndView("ejercicio", model);
    }

    @RequestMapping(value = "/guardarEjercicio", method = RequestMethod.POST)
    public ModelAndView guardarEjercicio(@RequestParam("idEjercicio") Long idEjercicio,
                                         @RequestParam("intensidad") String intensidad,
                                         @RequestParam("fecha") Date fecha,
                                         @RequestParam("minutos") Integer minutos,
                                         HttpServletRequest request) {

       Ejercicio ejercicio= repositorioEjercicio.obtenerEjercicioPorId(idEjercicio);
        //conseguir el id de usuario
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");


        // Aquí puedes crear un objeto EjercicioUsuario con los datos recibidos
        EjercicioUsuario ejercicioUsuario = new EjercicioUsuario();
        ejercicioUsuario.setNombre(ejercicio.getNombre());
        ejercicioUsuario.setIntensidad(intensidad);
        ejercicioUsuario.setEjercicio(ejercicio);
        ejercicioUsuario.setUsuario(usuario);
        ejercicioUsuario.setFecha(fecha);
        ejercicioUsuario.setMinutos(minutos);
        ModelMap model= new ModelMap();
        model.put("ejercicio", ejercicio);
        // Guarda el ejercicioUsuario en la base de datos
        boolean guardadoExitoso = servicioEjercicio.guardarEjercicio(ejercicioUsuario);
        if (guardadoExitoso) {
           model.put("mensaje", "El ejercicio se ha guardado correctamente.");

        } else {
            model.put("mensaje", "El ejercicio no se ha guardado correctamente. ");

        }

        return new ModelAndView("ejercicio",model);
    }

    @RequestMapping(value = "/actividadesFisicas",method = RequestMethod.GET)
    public ModelAndView irAEnForma(){
        ModelMap model = new ModelMap();
        try {
            List<Ejercicio> ejercicios = repositorioEjercicio.obtenerTodosLosEjercicios();
            model.put("listaEjercicios", ejercicios); // Cambia "ejercicio" a "listaEjercicios"
            return new ModelAndView("actividadesFisicas", model);
        }catch (Exception e){
            model.put("error", "Ningun Ejercicio Encontrado");
            return new ModelAndView("actividadesFisicas", model);
        }

    }
}
