package com.tallerwebi.presentacion;

import javax.servlet.http.HttpSession;

import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.Usuario;

import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.infraestructura.ServicioCalendarioImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import javax.servlet.http.HttpServletRequest;
import java.sql.Date;
import java.time.LocalDate;

@Controller
@SessionAttributes("usuario")
public class ControladorHome {


    private final ServicioCalendarioImpl servicioCalendarioImpl;
    private ServicioDatosUsuario servicioDatosUsuario;

    @Autowired
    public ControladorHome(ServicioDatosUsuario servicioDatosUsuario, ServicioCalendarioImpl servicioCalendarioImpl) {
        this.servicioDatosUsuario = servicioDatosUsuario;
        this.servicioCalendarioImpl = servicioCalendarioImpl;
    }




    @RequestMapping(value = "/home", method = RequestMethod.GET)
    public ModelAndView irAlHome(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        LocalDate localDate=LocalDate.now();

        if(usuario==null){
            return new ModelAndView("redirect:/inicio");
        }
        modelo.put("nombre", usuario.getNombre());


        return new ModelAndView("home", modelo);
    }



    @RequestMapping(value = "/diarioEjercicio",method = RequestMethod.GET)
    public ModelAndView irADiarioEjercicio(HttpServletRequest request){

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if(usuario==null){
            return new ModelAndView("redirect:/inicio");
        }
        return new ModelAndView("estadisticasUsuario");
    }
    @RequestMapping(value = "/diarioPeso",method = RequestMethod.GET)
    public ModelAndView irADiarioPeso(){
        return new ModelAndView("diarioPeso");
    }

    @RequestMapping(path="/mostrarNombreUsuario", method=RequestMethod.GET)
    public ModelAndView mostrarNombreUsuario(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            modelo.put("nombre", "hfukrgfuii");
        } else {
        modelo.put("nombre", "Nombre de usuario desconocido");
    }
        return new ModelAndView("home", modelo);
    }

    @RequestMapping(value = "/cambioPeso", method = RequestMethod.POST)
    public ModelAndView cambiarPeso(@ModelAttribute("nuevoPeso") Double nuevoPeso, HttpServletRequest request) throws PesoIncorrectoException, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, UsuarioNoExistente, PesoMetaIncorrectoException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        ModelMap modelo = new ModelMap();

            servicioDatosUsuario.actualizarPeso(usuario, nuevoPeso);
            modelo.put("mensaje", "Su peso se ha actualizado correctamente");

        return new ModelAndView("redirect:/home",modelo);
    }
}



