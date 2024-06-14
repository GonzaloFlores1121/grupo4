package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;

@Controller
public class ControladorMisAlimentos {

    private ServicioColacion servicioColacion;
    private ServicioAlimento servicioALimento;


    @Autowired
    public ControladorMisAlimentos( ServicioColacion servicioColacion, ServicioAlimento servicioALimento) {

        this.servicioColacion = servicioColacion;
        this.servicioALimento = servicioALimento;
    }

    @RequestMapping(value = "/misAlimentos", method = RequestMethod.GET)
    public ModelAndView verAlimentosPorFecha(@RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha, Model model, HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        List<Colacion> colaciones = servicioColacion.obtenerColacionesDelUsuarioPOrFecha(usuario, fecha);
        modelo.put("colaciones", colaciones);
        modelo.put("fecha", fecha);
        return new ModelAndView("misAlimentos", modelo);
    }

    @RequestMapping(value = "/verAlimento", method = RequestMethod.GET)
    public ModelAndView verAlimento(@RequestParam("id") Long id) {
        ModelMap modelo = new ModelMap();
        Colacion colacion = servicioColacion.obtenerColacionPorId(id);
        Alimento alimento=servicioALimento.obtenerAlimentosPorId(colacion.getAlimentos().getId());
        modelo.put("alimento", alimento);
        return new ModelAndView("detalles_alimento", modelo);
    }

}
