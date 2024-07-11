package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.infraestructura.ServicioCalendarioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("usuario")
public class ControladorEstadisticaUsuario {


    private ServicioDatosUsuario servicioDatosUsuario;
    private ServicioPago servicioPago;


    @Autowired
    public ControladorEstadisticaUsuario(ServicioDatosUsuario servicioDatosUsuarioImpl, ServicioPago servicioPagoImpl) {

        this.servicioPago = servicioPagoImpl;
        this.servicioDatosUsuario = servicioDatosUsuarioImpl;
    }

    @RequestMapping(value = "/estadisticasUsuario", method = RequestMethod.GET)
    public ModelAndView irAlasEstadisticas(HttpServletRequest request) throws UsuarioNoExistente, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, PesoMetaIncorrectoException {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario != null && servicioPago.isPremiumUser(usuario.getEmail())) {

            List<HistoriaPesoUsuario> historial = servicioDatosUsuario.obtenerTodoElHistorialDePeso(usuario);


            GraficoHistorialPeso grafico = new GraficoHistorialPeso(historial);


            model.addAttribute("grafico", grafico);

            Double pesoDisminuido = servicioDatosUsuario.pesoDisminuidoALaFecha(usuario);
            Double pesoGanado = servicioDatosUsuario.pesoGanadoALaFecha(usuario);
            Double pesoFaltanteParaMeta = servicioDatosUsuario.CantidadDePesoFaltanteParaLLegarALaMeta(usuario);
            Double pesoActual = servicioDatosUsuario.obtenerPesoActual(usuario);
            Double pesoInicial = servicioDatosUsuario.obtenerPesoInicial(usuario);

                Integer icr=servicioDatosUsuario.calcularIngestaCalorica(usuario);
                MacronutrientesUsuario macronutrientesUsuario = servicioDatosUsuario.CalcularDistribucionDeMacronutrientes(usuario);
                model.put("icr", icr);
                model.put("carbos", macronutrientesUsuario.getCarbohidratosAConsumir());
                model.put("grasas",  macronutrientesUsuario.getGrasaAConsumir());
                model.put("proteinas", macronutrientesUsuario.getProteinaAConsumir());

            model.addAttribute("pesoDisminuido", pesoDisminuido);
            model.addAttribute("pesoGanado", pesoGanado);
            model.addAttribute("pesoFaltanteParaMeta", pesoFaltanteParaMeta);
            model.addAttribute("pesoFaltanteParaMeta", pesoFaltanteParaMeta);
            model.addAttribute("pesoInicial", pesoInicial);
            model.addAttribute("pesoActual", pesoActual);

            return new ModelAndView("estadisticasUsuario", model);
        }
            return new ModelAndView("accesoDenegado", model);
        }
}