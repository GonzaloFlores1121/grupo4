package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.AlturaIncorrectaException;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.dominio.excepcion.EdadInvalidaException;
import com.tallerwebi.dominio.excepcion.PesoIncorrectoException;
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
        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-dd");
        String fechaStr = fechaActual.format(formatter);
        return new ModelAndView("redirect:/diarioAlimentos/" + fechaStr);
    }

    @RequestMapping(value = "/diarioAlimentos/{fecha}", method = RequestMethod.GET)
    public ModelAndView mostrarDiarioAlimentosPorFecha(@PathVariable("fecha") String fechaStr,@RequestParam(value = "mensajeEliminacion", required = false) String mensajeEliminacion, HttpServletRequest request) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        ModelMap model = new ModelMap();
        obtenerUsuarioSession(request, model);
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if(usuario==null){
            return new ModelAndView("redirect:/inicio");
        }
        if (mensajeEliminacion != null) {
            model.put("mensajeEliminacion", mensajeEliminacion);
        }

        LocalDate fecha = parseFecha(fechaStr);
        String fechaFormateada = formatFecha(fecha);
        model.put("fechaFormateada", fechaFormateada);

        obtenerLasColacionesYAgregarlasEnElModel(fecha, usuario, model);
        agregarAlimentosBD(model);
        calcularMacronutrientes(usuario, model);

        return new ModelAndView("diarioAlimentos", model);
    }

    @RequestMapping(value = "/diarioAlimentos/agregarAlimentos", method = RequestMethod.POST)
    public ModelAndView agregarAlimentoAcolacion(@RequestParam List<Long> alimentoIds, @RequestParam("tipoColacion") int tipoColacion,
                                           @RequestParam("fecha") int fecha, int cantidad,HttpServletRequest request) {
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
                    servicioColacion.guardarColacionUsuario(alimento, usuario, cantidad,tipo,
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
    @RequestMapping(value = "/diarioAlimentos/eliminarAlimento/{idAlimento}/{tipoColacion}/{fecha}", method = RequestMethod.GET)
    public ModelAndView eliminarAlimentoDeColacion(@PathVariable("idAlimento") Long idAlimento, @PathVariable("tipoColacion") int tipoColacion,
                                                   @PathVariable("fecha") String fecha, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return new ModelAndView("redirect:/inicio");
        }

        try {
            Alimento alimento = servicioALimento.obtenerAlimentosPorId(idAlimento);
            servicioColacion.eliminarColacionUsuario(alimento, usuario, TipoColacion.values()[tipoColacion], parseFecha(fecha));
            return new ModelAndView("redirect:/diarioAlimentos/" + fecha);
        } catch (Exception e) {

            return new ModelAndView("redirect:/diarioAlimentos/" + fecha );
        }
    }

    @RequestMapping(value = "/detalles_alimento/agregar", method = RequestMethod.POST)
    public ModelAndView guardarUnaColacion(@RequestParam("alimentoId") Long idAlimento, @RequestParam("tipoColacion") int tipoColacion,
                                           @RequestParam("fecha") String fecha, @RequestParam("cantidad") Integer cantidad,
                                           @RequestParam("action") String action,
                                           HttpServletRequest request) {
        ModelMap model = new ModelMap();
        Alimento alimento = servicioALimento.obtenerAlimentosPorId(idAlimento);
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.put("usuario", usuario);

        TipoColacion tipo = TipoColacion.values()[tipoColacion];
        if (usuario == null) {
            return new ModelAndView("redirect:/login");
        }

        switch (action) {
            case "guardar":
                return manejarLaAccionDeGuardadoColacion(alimento, usuario, tipo, fecha,cantidad, model);
            case "actualizar":
                return manejarLaAccionDeActualizarLosDatosDeLaVistaDeAlimentos(model,cantidad,alimento);
            case "cancelar":
                return manejarLaAccionDeCancelarElGuardadoDeColacion();
            default:
                throw new IllegalArgumentException("Acción no reconocida: " + action);
        }
    }

    private ModelAndView manejarLaAccionDeGuardadoColacion(Alimento alimento, Usuario usuario, TipoColacion tipo, String fechaString,int cantidad ,ModelMap model) {
      LocalDate fecha= parseFecha(fechaString);
        try {
            servicioColacion.guardarColacionUsuario(alimento, usuario,cantidad,tipo,parseFecha(fechaString));

            return new ModelAndView("redirect:/diarioAlimentos/"+ fecha);
        } catch (Exception e) {
            model.put("mensaje", "Error agregando colacion: " + e.getMessage());
            model.put("alimento",alimento);
            return new ModelAndView("detalles_alimento", model);
        }
    }

    private ModelAndView manejarLaAccionDeActualizarLosDatosDeLaVistaDeAlimentos(ModelMap model,int cantidad,Alimento alimento) {
      model.put("cantidad",cantidad);
        model.put("alimento",alimento);
        return new ModelAndView("detalles_alimento", model);
    }

    private ModelAndView manejarLaAccionDeCancelarElGuardadoDeColacion() {
        return new ModelAndView("redirect:/diarioAlimentos");
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

    private LocalDate parseFecha(String fechaStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d");
        return LocalDate.parse(fechaStr, formatter);
    }

    private String formatFecha(LocalDate fecha) {
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEEE d 'de' MMMM 'de' yyyy", new Locale("es", "ES"));
        return fecha.format(outputFormatter);
    }

    private void  obtenerLasColacionesYAgregarlasEnElModel(LocalDate fecha, Usuario usuario, ModelMap model) {
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

        model.put("tipoColacionDesayuno", TipoColacion.DESAYUNO.ordinal());
        model.put("tipoColacionAlmuerzo", TipoColacion.ALMUERZO.ordinal());
        model.put("tipoColacionMerienda", TipoColacion.MERIENDA.ordinal());
        model.put("tipoColacionCena", TipoColacion.CENA.ordinal());
        model.put("tipoColacionSnacks", TipoColacion.SNACKS.ordinal());
    }

    private void agregarAlimentosBD(ModelMap model) {
        List<Alimento> alimentosbd = servicioALimento.listarAlimentos();
        model.put("alimentosbd", alimentosbd);
    }

    private void calcularMacronutrientes(Usuario usuario, ModelMap model) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        Integer icr=servicioDatosUsuario.calcularIngestaCalorica(usuario);
        MacronutrientesUsuario macronutrientesUsuario = servicioDatosUsuario.CalcularDistribucionDeMacronutrientes(usuario);
        model.put("icr", icr);
        model.put("carbos", macronutrientesUsuario.getCarbohidratosAConsumir());
        model.put("grasas",  macronutrientesUsuario.getGrasaAConsumir());
        model.put("proteinas", macronutrientesUsuario.getProteinaAConsumir());
    }
}





