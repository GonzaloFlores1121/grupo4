package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.ui.ModelMap;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorRegistro {


    private ServicioDatosUsuario servicioDatosUsuario;
    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorRegistro(ServicioDatosUsuario servicioDatosUsuario, ServicioLogin servicioLogin) {
        this.servicioDatosUsuario = servicioDatosUsuario;
        this.servicioLogin = servicioLogin;
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
            return new ModelAndView("redirect:/menuprincipal");
        }
        model.put("error", "Usuario o clave incorrecta");

        return new ModelAndView("iniciar-sesion", model);
    }


    @RequestMapping(value = "/menuprincipal", method = RequestMethod.GET)
    public ModelAndView irAlMenuPrincipal(HttpServletRequest request) {
        ModelMap modelo = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            modelo.put("nombre", usuario.getNombre());
        } else {
            modelo.put("nombre", "Usuario");
        }
        return new ModelAndView("menuprincipal", modelo);
    }

    @RequestMapping(value = "/enviarFormulario", method = RequestMethod.POST)
    public ModelAndView enviarFormulario(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        List<String> errores = new ArrayList<>();
        try {
            servicioLogin.registrar(usuario);
        } catch (UsuarioExistente e) {
            errores.add("El usuario ya existe");
        } catch (AlturaIncorrectaException e) {
            errores.add("La altura debe ser mayor a 0 y metro a 3 metros");
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