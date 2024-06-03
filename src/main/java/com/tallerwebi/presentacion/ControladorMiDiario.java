package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.AlturaIncorrectaException;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.dominio.excepcion.EdadInvalidaException;
import com.tallerwebi.dominio.excepcion.PesoIncorrectoException;
import org.hibernate.annotations.common.reflection.XMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorMiDiario {

    private ServicioDatosUsuario servicioDatosUsuario;
    private ServicioColacion servicioColacion;
    private ServicioAlimento servicioALimento;

    @Autowired
    public ControladorMiDiario(ServicioDatosUsuario servicioDatosUsuario, ServicioColacion servicioColacion, ServicioAlimento servicioALimento) {
        this.servicioDatosUsuario = servicioDatosUsuario;
        this.servicioColacion = servicioColacion;
        this.servicioALimento = servicioALimento;
    }

  @RequestMapping(value ="/diarioAlimentos", method = RequestMethod.GET)
  public ModelAndView mostrarVista(){
        return new ModelAndView("diarioAlimentos");
  }

    @RequestMapping(value = "/diarioAlimentos/{fecha}", method = RequestMethod.GET)
    public ModelAndView mostrarDiarioAlimentosPorFecha(@PathVariable("fecha") String fechaStr, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        // Convierte la fecha de String a LocalDate
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        LocalDate fecha = LocalDate.parse(fechaStr, formatter);

        // Obtén las colaciones para la fecha seleccionada y el usuario
        Map<TipoColacion, List<Alimento>> colacionesPorTipo = new HashMap<>();
        for (TipoColacion tipo : TipoColacion.values()) {
            List<Alimento> alimentos = servicioColacion.obtenerAlimentosPorFechaYUsuarioYTipoColacion(fecha, usuario, tipo);
            colacionesPorTipo.put(tipo, alimentos);
        }


        model.put("colacionesPorTipo", colacionesPorTipo);
        model.put("fechaActual", fecha);

        return new ModelAndView("diarioAlimentos",model);
    }

    @RequestMapping(value = "/diarioAlimentos/agregar", method = RequestMethod.POST)
    public ModelAndView guardarUnaColacion(@RequestParam("alimentoId") Long idAlimento, @RequestParam("tipoColacion") String tipoColacion,
                                           @RequestParam("fecha") String fecha, @RequestParam("cantidad") Integer cantidad,
                                           @RequestParam("action") String action,
                                           HttpServletRequest request) {

        Alimento alimento = servicioALimento.obtenerAlimentosPorId(idAlimento);
        //conseguir el id de usuario
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        ModelAndView modelAndView;
        switch (action) {
            case "guardar":

                servicioColacion.guardarColacionUsuario(alimento,usuario,TipoColacion.valueOf(tipoColacion),
                        calcularFechaPorString(fecha));
                modelAndView = new ModelAndView("diarioAlimentos");

            case "actualizar":
                // Aquí va la lógica para actualizar la vista detalles_alimentos
                modelAndView = new ModelAndView("detalles_alimento");

            case "cancelar":
                // Aquí va la lógica para volver a diario alimentos
                modelAndView = new ModelAndView("diarioAlimentos");
                break;

            default:
                throw new IllegalArgumentException("Acción no reconocida: " + action);
        }

        return modelAndView;
    }


    private void obtenerUsuarioSession(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
    }

    private LocalDate calcularFechaPorString(String fecha) {
        switch (fecha) {
            case "ayer":
                return LocalDate.now().minusDays(1);
            case "hoy":
                return LocalDate.now();
            case "mañana":
                return LocalDate.now().plusDays(1);
            default:
                throw new IllegalArgumentException("Fecha no reconocida: " + fecha);
        }
    }
}





