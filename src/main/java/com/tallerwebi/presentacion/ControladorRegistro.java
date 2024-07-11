package com.tallerwebi.presentacion;

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

@Controller
public class ControladorRegistro {

    private ServicioLogin servicioLogin;
    private ServicioNotificacion servicioNotificacion;
    private ServicioDatosUsuario servicioDatosUsuario;

    @Autowired
    public ControladorRegistro(ServicioLogin servicioLogin, ServicioNotificacion servicioNotificacion, ServicioDatosUsuario servicioDatosUsuario) {
        this.servicioLogin = servicioLogin;
        this.servicioNotificacion = servicioNotificacion;
        this.servicioDatosUsuario = servicioDatosUsuario;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/inicio");
    }

    @RequestMapping(path = "/inicio", method = RequestMethod.GET)
    public ModelAndView irAInicio() {
        return new ModelAndView("inicio");
    }

    @RequestMapping(path = "/formulario-registro", method = RequestMethod.GET)
    public ModelAndView irAFormulario() {
        ModelMap model = new ModelMap();
        model.addAttribute("usuario", new Usuario());
        return new ModelAndView("formulario-registro", model);
    }


    @RequestMapping(path = "/enviarFormulario", method = RequestMethod.POST)
    public ModelAndView enviarFormulario(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        String error = null;
        try {

            servicioLogin.registrarUsuario(usuario);
            servicioDatosUsuario.ingresarPesoInicial(usuario.getPeso(),usuario);
            String titulo = "Bienvenido a fatloss";
            String contenido = "Nos alegra que te unas a nosotros en tu camino hacia una vida mas saludable. FatLoss es tu app de nutricion ideal para alcanzar tus objetivos de perdida de peso.";
            servicioNotificacion.enviarNotificacion(titulo, contenido, LocalDateTime.now(), usuario.getId());
        } catch (UsuarioExistente e) {
            error = "El usuario ya existe.";
        } catch (PesoMetaIncorrectoException e) {
            error = "El peso meta debe ser menor a su peso.";
        } catch (AlturaIncorrectaException e) {
            error = "La altura debe ser mayor a 0 y menor que 3 metros.";
        } catch (EdadInvalidaException e) {
            error = "La edad debe ser entre 12 y 100 años.";
        } catch (PesoIncorrectoException e) {
            error = "El peso debe ser mayor a 0 y menor a 500kg.";
        } catch (Exception e) {
            error = "Fallo desconocida al registrar usuario.";
        }

        if (error != null) {
            model.addAttribute("error", error);
            return new ModelAndView("formulario-registro", model);
        }
        return new ModelAndView("redirect:/iniciar-sesion");
    }

    @RequestMapping(path = "/iniciar-sesion", method = RequestMethod.GET)
    public ModelAndView irAInicioSesion() {
        return new ModelAndView("iniciar-sesion");
    }

    @RequestMapping(path = "/validarLogin", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        Usuario usuarioBuscado = servicioLogin.verificarUsuario(usuario.getEmail(), usuario.getPassword());
        if (usuarioBuscado != null) {
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuarioBuscado);

            return new ModelAndView("redirect:/home");
        }
        model.addAttribute("error", "Usuario o clave incorrecta");
        return new ModelAndView("iniciar-sesion", model);
    }

}
