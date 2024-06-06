package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
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
        ModelMap model = new ModelMap();
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaStr = fechaActual.format(formatter);
        return new ModelAndView("redirect:/diarioAlimentos/" + fechaStr, model);
    }

    @RequestMapping(value = "/diarioAlimentos/agregarAlimentos", method = RequestMethod.POST)
    public ModelAndView agregarAlimentoAcolacion(@RequestParam List<Long> alimentoIds, @RequestParam("tipoColacion") int tipoColacion,
                                           @RequestParam("fecha") int fecha, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.put("usuario", usuario);
        ModelAndView modelAndView;
        TipoColacion tipo = TipoColacion.values()[tipoColacion];
        if (usuario == null) {
            return new ModelAndView ("redirect:/inicio");
        } else {
            try {

                for (Long alimentoId : alimentoIds) {
                    Alimento alimento = servicioALimento.obtenerAlimentosPorId(alimentoId);
                    servicioColacion.guardarColacionUsuario(alimento, usuario, tipo,
                            calcularFechaPorString(fecha));
                    model.put("alimento", alimento);
                }
                model.put("mensaje", "Colacion agregada correctamente");
                modelAndView = new ModelAndView("redirect:/diarioAlimentos", model);
            } catch (Exception e) {
                model.put("mensaje", "Error agregando colacion: " + e.getMessage());
                modelAndView = new ModelAndView("redirect:/diarioAlimentos", model);
            }
        }
        return new ModelAndView("redirect:/diarioAlimentos");
    }

    @RequestMapping(value = "/diarioAlimentos/{fecha}", method = RequestMethod.GET)
    public ModelAndView mostrarDiarioAlimentosPorFecha(@PathVariable("fecha") String fechaStr, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        obtenerUsuarioSession(request, model);
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        LocalDate fecha = LocalDate.parse(fechaStr, formatter);

     //Pasarle al model la fecha para visibilizarla en la vista.
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fechaFormateada = fecha.format(outputFormatter);

if(usuario==null){
    return new ModelAndView("redirect:/inicio");
}
        List<Alimento> desayuno = servicioColacion.obtenerAlimentosPorFechaYUsuarioYTipoColacion(fecha, usuario, TipoColacion.DESAYUNO);
        List<Alimento> snacks = servicioColacion.obtenerAlimentosPorFechaYUsuarioYTipoColacion(fecha, usuario, TipoColacion.SNACKS);
        List<Alimento> almuerzo = servicioColacion.obtenerAlimentosPorFechaYUsuarioYTipoColacion(fecha, usuario, TipoColacion.ALMUERZO);
        List<Alimento> merienda = servicioColacion.obtenerAlimentosPorFechaYUsuarioYTipoColacion(fecha, usuario, TipoColacion.MERIENDA);
        List<Alimento> cena = servicioColacion.obtenerAlimentosPorFechaYUsuarioYTipoColacion(fecha, usuario, TipoColacion.CENA);
        List<Alimento> alimentosbd = servicioALimento.listarAlimentos();

        model.put("alimentosbd", alimentosbd);
        model.put("desayuno", desayuno);
        model.put("snacks", snacks);
        model.put("almuerzo", almuerzo);
        model.put("merienda", merienda);
        model.put("cena", cena);
        model.put("fechaFormateada", fechaFormateada);


        return new ModelAndView("diarioAlimentos", model);
    }



    @RequestMapping(value = "/detalles_alimento/agregar", method = RequestMethod.POST)
    public ModelAndView guardarUnaColacion(@RequestParam("alimentoId") Long idAlimento, @RequestParam("tipoColacion") int tipoColacion,
                                           @RequestParam("fecha") int fecha, @RequestParam("cantidad") Integer cantidad,
                                           @RequestParam("action") String action,
                                           HttpServletRequest request) {

        ModelMap model = new ModelMap();
        Alimento alimento = servicioALimento.obtenerAlimentosPorId(idAlimento);
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.put("usuario", usuario);
        ModelAndView modelAndView;

        TipoColacion tipo = TipoColacion.values()[tipoColacion];
        if (usuario == null) {
            return new ModelAndView("redirect:/inicio");
        }else{
            switch (action) {
                case "guardar":
                    try {
                        servicioColacion.guardarColacionUsuario(alimento,usuario,tipo,
                                calcularFechaPorString(fecha));
                        model.put("mensaje","Colacion agregada correctamente");
                        model.put("alimento", alimento); // Agregar alimento al modelo
                        modelAndView = new ModelAndView("detalles_alimento",model); // Cambiar a detalles_alimento
                    } catch(Exception e) {
                        model.put("mensaje","Error agregando colacion: " + e.getMessage());
                        modelAndView = new ModelAndView("detalles_alimento",model);
                    }
                    break;

                case "actualizar":
                    model.put("alimento",alimento);
                    modelAndView = new ModelAndView("detalles_alimento",model);

                    break;

                case "cancelar":

                    return new ModelAndView("redirect:/diarioAlimentos");


                default:
                    throw new IllegalArgumentException("Acción no reconocida: " + action);
            }

            return modelAndView;
        }

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





