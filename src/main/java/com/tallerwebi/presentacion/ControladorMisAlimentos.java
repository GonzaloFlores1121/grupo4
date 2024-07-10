package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Controller
public class ControladorMisAlimentos {

    private ServicioColacion servicioColacion;
    private ServicioAlimento servicioALimento;
    private ServicioDatosUsuario servicioDatosUsuario;


    @Autowired
    public ControladorMisAlimentos( ServicioColacion servicioColacion, ServicioAlimento servicioALimento, ServicioDatosUsuario servicioDatosUsuario) {

        this.servicioColacion = servicioColacion;
        this.servicioALimento = servicioALimento;
        this.servicioDatosUsuario = servicioDatosUsuario;
    }

    @RequestMapping(value = "/misAlimentos", method = RequestMethod.GET)
    public ModelAndView verAlimentosPorFecha(@RequestParam("fecha") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fecha, Model model, HttpServletRequest request) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, UsuarioNoExistente, PesoIncorrectoException, EjercicioNoExistente {
        ModelMap modelo = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario==null){
            return new ModelAndView("redirect:/inicio");
        }
        modelo.put("usuario", usuario);
        List<Colacion> colaciones = servicioColacion.obtenerColacionesDelUsuarioPOrFecha(usuario, fecha);
        modelo.put("colaciones", colaciones);
        modelo.put("fecha",fecha);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        String fechaFormateada = fecha.format(formatter);

        modelo.put("fechaFormateada", fechaFormateada);
        return new ModelAndView("misAlimentos", modelo);
    }


    @RequestMapping(value = "/detalles_alimento/agregar", method = RequestMethod.POST)
    public ModelAndView manejarActionsDelFormularioDeAlimentos(@RequestParam("alimentoId") Long idAlimento, @RequestParam("tipoColacion") int tipoColacion,
                                                               @RequestParam("fecha") String fecha, @RequestParam("cantidad") Integer cantidad,
                                                               @RequestParam("action") String action,
                                                               @RequestParam("from") String from,
                                                               @RequestParam("nombre") String nombre,
                                                               HttpServletRequest request,
                                                               RedirectAttributes redirectAttributes) {
        ModelMap model = new ModelMap();
        Alimento alimento = servicioALimento.obtenerAlimentosPorId(idAlimento);
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.put("usuario", usuario);

        TipoColacion tipo = TipoColacion.values()[tipoColacion];
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }
        model.put("cantidad",cantidad);


        switch (action) {
            case "guardar":
                return manejarLaAccionDeGuardadoColacion(alimento, usuario, tipo, fecha, cantidad, nombre, from, model,redirectAttributes);
            case "actualizar":
                return manejarLaAccionDeActualizarLosDatosDeLaVistaDeAlimentos(model, cantidad, alimento,from);
            case "cancelar":
                return manejarLaAccionDeCancelarElGuardadoDeColacion(alimento,from,fecha);
            default:
                throw new IllegalArgumentException("Acción no reconocida: " + action);
        }
    }

    private ModelAndView manejarLaAccionDeGuardadoColacion(Alimento alimento, Usuario usuario, TipoColacion tipo,
                                                           String fechaString, int cantidad, String nombre, String from,
                                                           ModelMap model,RedirectAttributes redirectAttributes) {
        LocalDate fecha = parseFecha(fechaString);

        if ("misAlimentos".equals(from)) {
            return handleMisAlimentosAction(alimento, usuario, tipo, fecha, cantidad, nombre, model,redirectAttributes);
        } else {
            return handleOtherAction(alimento, usuario, tipo, fecha, cantidad, nombre, model,redirectAttributes);
        }
    }

    private ModelAndView handleMisAlimentosAction(Alimento alimento, Usuario usuario, TipoColacion tipo, LocalDate fecha,
                                                  int cantidad, String nombre, ModelMap model, RedirectAttributes redirectAttributes) {
        Colacion colacion = servicioColacion.obtenerColacionPorAlimento(alimento);
        LocalDate fechaAntiguaColacion = colacion.getFecha();
        TipoColacion tipoVieja = colacion.getTipo();

        if (!tipo.equals(colacion.getTipo()) || !fecha.equals(colacion.getFecha())) {
            try {
                servicioColacion.eliminarColacionUsuario(alimento, usuario, tipoVieja, fechaAntiguaColacion);
                servicioColacion.guardarColacionUsuario(alimento, usuario, cantidad, tipo, fecha, nombre);

                redirectAttributes.addFlashAttribute("mensajeAlimentoModificado", "El alimento se ha modificado correctamente.");
            } catch (Exception e) {
                model.put("mensaje", "Error: " + e.getMessage());
                model.put("alimento", alimento);
                return new ModelAndView("detalles_alimento", model);
            }
        } else {
            updateAlimento(alimento, cantidad, nombre);
            redirectAttributes.addFlashAttribute("mensajeAlimentoModificado", "El alimento se ha modificado correctamente.");
        }

        return new ModelAndView("redirect:/misAlimentos?fecha="+fecha);
    }

    private ModelAndView handleOtherAction(Alimento alimento, Usuario usuario, TipoColacion tipo, LocalDate fecha,
                                           int cantidad, String nombre, ModelMap model, RedirectAttributes redirectAttributes) {
        try {
            servicioColacion.guardarColacionUsuario(alimento, usuario, cantidad, tipo, fecha, nombre);
            redirectAttributes.addFlashAttribute("mensajeAlimentoAgregado","El alimento se ha agregado correctamente");
        } catch (Exception e) {
            model.put("mensaje", "Error agregando colacion: " + e.getMessage());
            model.put("alimento", alimento);
            return new ModelAndView("detalles_alimento", model);
        }

        return new ModelAndView("redirect:/misAlimentos?fecha="+fecha);
    }

    private void updateAlimento(Alimento alimento, int cantidad, String nombre) {
        alimento.setCantidad(cantidad);
        alimento.setNombre(nombre);
        alimento.actualizarValoresNutricionalesPorCantidad();
        servicioALimento.actualizarAlimento(alimento);
    }



    private ModelAndView manejarLaAccionDeActualizarLosDatosDeLaVistaDeAlimentos(ModelMap model,int cantidad,
                                                                                 Alimento alimento,String from) {
        model.put("cantidad",cantidad);
        model.put("alimento",alimento);
        model.put("from",from);
        return new ModelAndView("detalles_alimento", model);
    }

    private ModelAndView manejarLaAccionDeCancelarElGuardadoDeColacion(Alimento alimento, String from, String fecha) {

        if("misAlimentos".equals(from)){
            return new ModelAndView("redirect:/misAlimentos?fecha="+fecha);
        }else{
            Long id= alimento.getCategoria().getId();
            return new ModelAndView("redirect:/categorias/"+id);
        }

    }



    @RequestMapping(value = "/misAlimentos/eliminarAlimento/{idAlimento}/{tipoColacion}/{fecha}", method = RequestMethod.GET)
    public ModelAndView eliminarAlimentoDeColacion(@PathVariable("idAlimento") Long idAlimento, @PathVariable("tipoColacion")TipoColacion tipoColacion,
                                                   @PathVariable("fecha") String fecha, HttpServletRequest request,
                                                   RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return new ModelAndView("redirect:/inicio");
        }

        try {
            Alimento alimento = servicioALimento.obtenerAlimentosPorId(idAlimento);
            servicioColacion.eliminarColacionUsuario(alimento, usuario, tipoColacion,parseFecha(fecha));
            redirectAttributes.addFlashAttribute("mensajeAlimentoEliminado","Se eliminó el alimento "+alimento.getNombre()
            + " correctamente!");

            return new ModelAndView("redirect:/misAlimentos?fecha=" + fecha);
        } catch (Exception e) {

            return new ModelAndView("redirect:/misAlimentos?fecha=" + fecha);
        }
    }

    private LocalDate parseFecha(String fechaStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        return LocalDate.parse(fechaStr, formatter);
    }

    private String formatFecha(LocalDate fecha) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        return fecha.format(outputFormatter);
    }
}
