package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.MacronutrientesUsuario;
import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.AlturaIncorrectaException;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.dominio.excepcion.EdadInvalidaException;
import com.tallerwebi.dominio.excepcion.PesoIncorrectoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorMiDiario {

    private ServicioDatosUsuario servicioDatosUsuario;

    @Autowired
    public ControladorMiDiario(ServicioDatosUsuario servicioDatosUsuario) {
        this.servicioDatosUsuario = servicioDatosUsuario;
    }

    @RequestMapping(value = "/diarioAlimentos",method = RequestMethod.GET)
    public ModelAndView irAMiDiarioAlimentos(HttpServletRequest request) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if(usuario != null) {
            ModelMap modelo = new ModelMap();
            Integer icr=servicioDatosUsuario.calcularIngestaCalorica(usuario);
            MacronutrientesUsuario macronutrientesUsuario = servicioDatosUsuario.CalcularDistribucionDeMacronutrientes(usuario);
            modelo.put("icr", icr);
            modelo.put("carbos", macronutrientesUsuario.getCarbohidratosAConsumir());
            modelo.put("grasas",  macronutrientesUsuario.getGrasaAConsumir());
            modelo.put("proteinas", macronutrientesUsuario.getProteinaAConsumir());
            return new ModelAndView("diarioAlimentos",modelo);
        }
        else {
            return new ModelAndView("redirect:/inicio");
        }

    }

}
