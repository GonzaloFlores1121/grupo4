package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EjercicioNoExistente;
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
    public ControladorEjercicio(RepositorioEjercicio repositorioEjercicio, RepositorioEjercicioUsuario repositorioEjercicioUsuario, RepositorioUsuario repositorioUsuario, ServicioEjercicio servicioEjercicio) {
        this.repositorioEjercicio = repositorioEjercicio;
        this.repositorioEjercicioUsuario = repositorioEjercicioUsuario;
        this.repositorioUsuario = repositorioUsuario;
        this.servicioEjercicio = servicioEjercicio;
    }

    @RequestMapping(value = "/irAEjercicio", method = RequestMethod.POST)
    public ModelAndView irAEjercicio(@RequestParam("id") Long id, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        obtenerUsuarioSession(request, model);
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

        Ejercicio ejercicio = repositorioEjercicio.obtenerEjercicioPorId(idEjercicio);
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
        ModelMap model = new ModelMap();
        model.put("ejercicio", ejercicio);
        // Guarda el ejercicioUsuario en la base de datos
        boolean guardadoExitoso = servicioEjercicio.guardarEjercicio(ejercicioUsuario);
        if (guardadoExitoso) {
            model.put("mensaje", "El ejercicio se ha guardado correctamente.");

        } else {
            model.put("mensaje", "El ejercicio no se ha guardado correctamente. ");

        }

        return new ModelAndView("ejercicio", model);
    }

    @RequestMapping(value = "/buscarEjercicio", method = RequestMethod.GET)
    public ModelAndView buscarEjercicio(@RequestParam(value = "search", required = false) String search, HttpServletRequest request) throws EjercicioNoExistente {
        ModelMap model = new ModelMap();
        obtenerUsuarioSession(request, model);
        try {
            List<Ejercicio> ejercicios;
            ejercicios = servicioEjercicio.obtenerEjercicioPorNombreOIntensidad(search);
            model.put("listaEjercicios", ejercicios);
            return new ModelAndView("actividadesFisicas", model);
        } catch (Exception e) {
            List<Ejercicio> ejercicios;
            ejercicios = servicioEjercicio.obtenerTodosLosEjercicios();
            model.put("listaEjercicios", ejercicios);
            return new ModelAndView("actividadesFisicas", model);
        }
    }
    @RequestMapping(value = "/actividadesFisicas", method = RequestMethod.GET)
    public ModelAndView irAEnForma(@RequestParam(value = "search", required = false) String search, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        obtenerUsuarioSession(request, model);
        try {
            List<Ejercicio> ejercicios;
            if (search != null && !search.isEmpty()) {
                ejercicios = servicioEjercicio.obtenerEjercicioPorNombreOIntensidad(search);
            } else {
                ejercicios = servicioEjercicio.obtenerTodosLosEjercicios();
            }
            model.put("listaEjercicios", ejercicios);
            return new ModelAndView("actividadesFisicas", model);
        } catch (Exception e) {
            model.put("error", "Ningún Ejercicio Encontrado");
            return new ModelAndView("actividadesFisicas", model);
        }
    }

    private void obtenerUsuarioSession(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
    }

}
