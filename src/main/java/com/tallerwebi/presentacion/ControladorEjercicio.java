package com.tallerwebi.presentacion;
import java.time.format.DateTimeFormatter;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EjercicioInvalido;
import com.tallerwebi.dominio.excepcion.EjercicioNoExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

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
        Usuario usuario = (Usuario) model.get("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/inicio");
        }

        Ejercicio ejercicio = repositorioEjercicio.obtenerEjercicioPorId(id);
        model.put("ejercicio", ejercicio);
        return new ModelAndView("ejercicio", model);
    }


    @RequestMapping(value = "/guardarEjercicio", method = RequestMethod.POST)
    public ModelAndView guardarEjercicio(@RequestParam("idEjercicio") Long idEjercicio,
                                         @RequestParam("intensidad") String intensidad,
                                         @RequestParam("fecha") Date fecha,
                                         @RequestParam("minutos") Integer minutos,
                                         HttpServletRequest request,
                                         RedirectAttributes redirectAttributes) throws EjercicioNoExistente, EjercicioInvalido {

        Ejercicio ejercicio = repositorioEjercicio.obtenerEjercicioPorId(idEjercicio);
        //conseguir el id de usuario
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        ModelMap model = new ModelMap();
        model.put("ejercicio", ejercicio);


        try {
            servicioEjercicio.guardarEjercicioUsuario(ejercicio.getNombre(),intensidad,ejercicio,usuario,fecha,minutos);
            redirectAttributes.addFlashAttribute("mensajeEjercicioAgregado", "El ejercicio se ha guardado correctamente.");


            return new ModelAndView("redirect:/misEjercicios?fecha="+fecha);
        }catch (Exception EjercicioInvalido){
            model.put("mensaje", "El ejercicio no se ha guardado correctamente.");
            return new ModelAndView("ejercicio", model);
        }

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
            model.addAttribute("unknown", "Ejercicio no encontrado");
            return new ModelAndView("actividadesFisicas", model);
        }
    }
    @RequestMapping(value = "/actividadesFisicas", method = RequestMethod.GET)
    public ModelAndView irAEnForma(@RequestParam(value = "search", required = false) String search, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        obtenerUsuarioSession(request, model);
        Usuario usuario = (Usuario) model.get("usuario");
        if (usuario == null) {
            return new ModelAndView("redirect:/inicio");
        }
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

    @RequestMapping(value = "/misEjercicios", method = RequestMethod.GET)
    public ModelAndView verEjerciciosPorFecha(@RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha, Model model, HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario==null){
            return new ModelAndView("redirect:/inicio");

        }
        List<EjercicioUsuario> ejercicios = servicioEjercicio.obtenerEjercicioUsuarioPorFecha(usuario, fecha);
        modelo.put("ejercicios", ejercicios);
        modelo.put("fecha", fecha);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fechaFormateada = fecha.format(formatter);

        modelo.put("fechaFormateada",fechaFormateada);

        return new ModelAndView("misEjercicios", modelo);
    }
    @RequestMapping(value = "/verEjercicio", method = RequestMethod.GET)
    public ModelAndView verEjercicio(@RequestParam("id") Long id) {
        ModelMap modelo = new ModelMap();
        EjercicioUsuario ejercicioUsuario=servicioEjercicio.buscarEjercicioUsuarioPorId(id);
        Ejercicio ejercicio=servicioEjercicio.obtenerEjercicioPorId(ejercicioUsuario.getEjercicio().getId());
        modelo.put("ejercicio", ejercicio);
        return new ModelAndView("ejercicio", modelo);
    }

    @RequestMapping(value = "/misEjercicios/eliminarEjercicio/{idEjercicioUsuario}/{fecha}", method = RequestMethod.GET)
    public ModelAndView eliminarEjercicioUsuario(@PathVariable("idEjercicioUsuario") Long idEjercicio,
                                                   @PathVariable("fecha") String fecha, HttpServletRequest request,
                                                 RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return new ModelAndView("redirect:/inicio");
        }

        try {
            EjercicioUsuario ejercicio= servicioEjercicio.buscarEjercicioUsuarioPorId(idEjercicio);
            servicioEjercicio.eliminarEjercicioUsuario(ejercicio, parseFecha(fecha));
            redirectAttributes.addFlashAttribute("mensajeEjercicioEliminadoCorrectamente","El ejercicio " + ejercicio.getNombre()+
                    " fue eliminado correctamente!");
            return new ModelAndView("redirect:/misEjercicios?fecha=" + fecha);
        } catch (Exception e) {

            return new ModelAndView("redirect:/misEjercicios?fecha=" + fecha);
        }
    }
    private LocalDate parseFecha(String fechaStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        return LocalDate.parse(fechaStr, formatter);
    }
    private void obtenerUsuarioSession(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
    }

}