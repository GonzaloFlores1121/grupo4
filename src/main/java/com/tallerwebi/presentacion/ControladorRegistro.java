package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioNotificacion;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorRegistro {


    private ServicioDatosUsuario servicioDatosUsuario;
    private ServicioLogin servicioLogin;
    private ServicioNotificacion servicioNotificacion;

    @Autowired
    public ControladorRegistro(ServicioDatosUsuario servicioDatosUsuario, ServicioLogin servicioLogin, ServicioNotificacion servicioNotificacion) {
        this.servicioDatosUsuario = servicioDatosUsuario;
        this.servicioLogin = servicioLogin;
        this.servicioNotificacion = servicioNotificacion;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/inicio");
    }
    
    @RequestMapping(value = "/inicio", method = RequestMethod.GET)
    public ModelAndView irAInicio() {
        return new ModelAndView("inicio");
    }

    @RequestMapping(value = "/formulario-registro", method = RequestMethod.GET)
    public ModelAndView irAFormulario() {
        ModelMap modelo = new ModelMap();
        modelo.put("usuario", new Usuario());
        return new ModelAndView("formulario-registro", modelo);
    }

    @RequestMapping(value = "/iniciar-sesion", method = RequestMethod.GET)
    public ModelAndView irAInicioSesion() {
        return new ModelAndView("iniciar-sesion");
    }

    @RequestMapping(path = "/validarLogin", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(usuario.getEmail(), usuario.getPassword());
        if (usuarioBuscado != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuarioBuscado);
            return new ModelAndView("redirect:/home");
        }
        model.put("error", "Usuario o clave incorrecta");

        return new ModelAndView("iniciar-sesion", model);
    }



    @Transactional
    @RequestMapping(value = "/enviarFormulario", method = RequestMethod.POST)
    public ModelAndView enviarFormulario(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        List<String> errores = new ArrayList<>();
        try {
            servicioLogin.registrar(usuario);
            Notificacion notificacion = servicioNotificacion.crearNotificacion( "Bienvenido a fatloss", "Nos alegra que te unas a nosotros en tu camino hacia una vida mas saludable. FatLoss es tu app de nutricion ideal para alcanzar tus objetivos de perdida de peso.");
            servicioNotificacion.enviarNotificacion(notificacion, LocalDateTime.now(), usuario.getEmail()); 
            Notificacion segundaNoti = servicioNotificacion.crearNotificacion("Meta Establecida", "Para alcanzar tu peso ideal, sigue una dieta equilibrada, haz ejercicio regularmente y manten la constancia. Monitorea tu progreso y ajusta segun sea necesario. ¡Tu puedes lograrlo!");           
            servicioNotificacion.enviarNotificacion(segundaNoti, LocalDateTime.now(), usuario.getEmail());
        } catch (UsuarioExistente e) {
            errores.add("El usuario ya existe");
        } catch (AlturaIncorrectaException e) {
            errores.add("La altura debe ser mayor a 0 y menor que 3 metros");
        } catch (EdadInvalidaException e) {
            errores.add("La edad debe ser entre 12 y 100 años");
        } catch (PesoIncorrectoException e) {
            errores.add("El peso debe ser mayor a 0 y menor a 500kg");
        } catch (Exception e) {
            errores.add("Error al registrar el nuevo usuario");
        }

        if (!errores.isEmpty()) {
            model.put("errores", errores);
            return new ModelAndView("formulario-registro", model);
        }

        return new ModelAndView("redirect:/iniciar-sesion");
    }

}