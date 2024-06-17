package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;
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


    @Autowired
    public ControladorEstadisticaUsuario( ServicioDatosUsuario servicioDatosUsuarioImpl) {


        this.servicioDatosUsuario = servicioDatosUsuarioImpl;
    }

    @RequestMapping(value = "/estadisticasUsuario", method = RequestMethod.GET)
    public ModelAndView irAlasEstadisticas(HttpServletRequest request) throws UsuarioNoExistente {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            // Manejar el caso en que el usuario no esté en la sesión
            return new ModelAndView("redirect:/login");
        }

        // Obtener historial de peso del usuario
        List<HistoriaPesoUsuario> historial = servicioDatosUsuario.obtenerTodoElHistorialDePeso(usuario);

        // Crear el gráfico de historial de peso
        new GraficoHistorialPeso(historial);

        // Agregar el gráfico al modelo (en caso de que se necesite en la vista)
        model.addAttribute("grafico", "chart.png");

        return new ModelAndView("estadisticasUsuario", model);
    }
}