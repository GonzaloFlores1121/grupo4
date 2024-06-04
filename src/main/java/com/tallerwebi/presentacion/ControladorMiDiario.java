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
  public ModelAndView mostrarVista(HttpServletRequest request){
      ModelMap model = new ModelMap();
      obtenerUsuarioSession(request, model);
        return new ModelAndView("diarioAlimentos");
  }

    @RequestMapping(value = "/diarioAlimentos/{fecha}", method = RequestMethod.GET)
    public ModelAndView mostrarDiarioAlimentosPorFecha(@PathVariable("fecha") String fechaStr, HttpServletRequest request) {
        ModelMap model = new ModelMap();


        obtenerUsuarioSession(request, model);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        LocalDate fecha = LocalDate.parse(fechaStr, formatter);

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        List<Alimento> desayuno = servicioColacion.obtenerAlimentosPorFechaYUsuarioYTipoColacion(fecha, usuario, TipoColacion.DESAYUNO);
        List<Alimento> snacks = servicioColacion.obtenerAlimentosPorFechaYUsuarioYTipoColacion(fecha, usuario, TipoColacion.SNACKS);
        List<Alimento> almuerzo = servicioColacion.obtenerAlimentosPorFechaYUsuarioYTipoColacion(fecha, usuario, TipoColacion.ALMUERZO);
        List<Alimento> merienda = servicioColacion.obtenerAlimentosPorFechaYUsuarioYTipoColacion(fecha, usuario, TipoColacion.MERIENDA);
        List<Alimento> cena = servicioColacion.obtenerAlimentosPorFechaYUsuarioYTipoColacion(fecha, usuario, TipoColacion.CENA);

        model.put("desayuno", desayuno);
        model.put("snacks", snacks);
        model.put("almuerzo", almuerzo);
        model.put("merienda", merienda);
        model.put("cena", cena);
        model.put("fechaActual", fecha);

        return new ModelAndView("diarioAlimentos", model);
    }

    @RequestMapping(value = "/diarioAlimentos/agregar", method = RequestMethod.POST)
    public ModelAndView guardarUnaColacion(@RequestParam("alimentoId") Long idAlimento, @RequestParam("tipoColacion") int tipoColacion,
                                           @RequestParam("fecha") int fecha, @RequestParam("cantidad") Integer cantidad,
                                           @RequestParam("action") String action,
                                           HttpServletRequest request) {

        ModelMap model = new ModelMap();
        Alimento alimento = servicioALimento.obtenerAlimentosPorId(idAlimento);
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        ModelAndView modelAndView;

        TipoColacion tipo = TipoColacion.values()[tipoColacion];
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }
        switch (action) {
            case "guardar":
                try {
                    servicioColacion.guardarColacionUsuario(alimento,usuario,tipo,
                            calcularFechaPorString(fecha));
                    model.put("mensaje","Colacion agregada correctamente");
                    modelAndView = new ModelAndView("diarioAlimentos",model);
                } catch(Exception e) {
                    model.put("mensaje","Error agregando colacion: " + e.getMessage());
                    modelAndView = new ModelAndView("diarioAlimentos",model);
                }
                break;

            case "actualizar":
                modelAndView = new ModelAndView("detalles_alimento");
                break;

            case "cancelar":
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

  public LocalDate calcularFechaPorString(int fecha) {
        switch (fecha) {
            case 1:
                return LocalDate.now().minusDays(1);
            case 2:
                return LocalDate.now();
            case 3:
                return LocalDate.now().plusDays(1);
            default:
                throw new IllegalArgumentException("Fecha no reconocida: " + fecha);
        }
    }
}





