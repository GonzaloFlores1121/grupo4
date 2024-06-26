package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.PesoIncorrectoException;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;
import com.tallerwebi.infraestructura.ServicioCalendarioImpl;
import com.tallerwebi.infraestructura.ServicioDatosUsuarioImpl;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.jfree.chart.ChartUtils;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@SessionAttributes("usuario")
public class ControladorCalendario {

    private  ServicioCalendarioImpl servicioCalendarioImpl;
    private ServicioDatosUsuario servicioDatosUsuario;
    private RepositorioEjercicio repositorioEjercicio;
    private RepositorioEjercicioUsuario repositorioEjercicioUsuario;
    private RepositorioUsuario repositorioUsuario;


    @Autowired
    public ControladorCalendario (RepositorioEjercicio repositorioEjercicio , RepositorioEjercicioUsuario repositorioEjercicioUsuario, RepositorioUsuario repositorioUsuario, ServicioCalendarioImpl servicioCalendarioImpl, ServicioDatosUsuario servicioDatosUsuarioImpl){
        this.repositorioEjercicio = repositorioEjercicio;
        this.repositorioEjercicioUsuario = repositorioEjercicioUsuario;
        this.repositorioUsuario = repositorioUsuario;
        this.servicioCalendarioImpl = servicioCalendarioImpl;
        this.servicioDatosUsuario = servicioDatosUsuarioImpl;
    }

    @RequestMapping(value = "/calendarioDieta", method = RequestMethod.GET)
    public ModelAndView irAMiCalendarioDieta(HttpServletRequest request) throws UsuarioNoExistente {
        ModelMap model = new ModelMap();
        obtenerUsuarioSession(request, model);
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario==null){
            return new ModelAndView("redirect:/inicio");
        }
        String mensaje = (String) session.getAttribute("mensaje");
        if (mensaje != null) {
            model.put("mensaje", mensaje);
            session.removeAttribute("mensaje");
        }

        try {
            Map<Date, Calendario> fechas = servicioCalendarioImpl.obtenerFechasCalendario(usuario);
            model.put("listaFechas", fechas);
            return new ModelAndView("calendarioDieta", model);
        } catch (Exception e) {
            model.put("error", "Ninguna Actividad fue encontrada aún :(");
            return new ModelAndView("calendarioDieta", model);
        }
    }


    private void obtenerUsuarioSession(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
    }

}
