package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.AlturaIncorrectaException;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.dominio.excepcion.EdadInvalidaException;
import com.tallerwebi.dominio.excepcion.PesoIncorrectoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;
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

    @RequestMapping(value = "/diarioAlimentos", method = RequestMethod.GET)
    public ModelAndView irAMiDiarioAlimentos(HttpServletRequest request) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null) {
            List<Colacion> colaciones = servicioColacion.listarColaciones();
            List<Alimento> alimentos = servicioALimento.listarAlimentos();
            if (colaciones == null || alimentos == null) {

            }
            ModelMap modelo = new ModelMap();
            Integer icr = servicioDatosUsuario.calcularIngestaCalorica(usuario);
            MacronutrientesUsuario macronutrientesUsuario = servicioDatosUsuario.CalcularDistribucionDeMacronutrientes(usuario);
            modelo.put("icr", icr);
            modelo.put("carbos", macronutrientesUsuario.getCarbohidratosAConsumir());
            modelo.put("grasas", macronutrientesUsuario.getGrasaAConsumir());
            modelo.put("proteinas", macronutrientesUsuario.getProteinaAConsumir());
            modelo.put("colaciones", colaciones);
            modelo.put("alimentos", alimentos);
            return new ModelAndView("diarioAlimentos", modelo);
        } else {
            return new ModelAndView("redirect:/inicio");
        }

    }

    @RequestMapping(value = "/diarioAlimentos/agregarAlimentos", method = RequestMethod.POST)
    public String agregarAlimentosAColacion(@RequestParam Long colacionId, @RequestParam List<Long> alimentoIds) {
        if (colacionId == null || alimentoIds == null || alimentoIds.isEmpty()) {
            throw new IllegalArgumentException("colacionId o alimentoIds no puede ser null o vacío");
        }
        for (Long alimentoId : alimentoIds) {
            servicioColacion.agregarAlimentoAColacion(colacionId, alimentoId);
        }
    return "redirect:/diarioAlimentos";
   }
}
