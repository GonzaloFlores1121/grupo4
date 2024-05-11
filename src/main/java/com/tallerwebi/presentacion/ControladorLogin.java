package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.AlturaIncorrectaException;
import com.tallerwebi.dominio.excepcion.EdadInvalidaException;
import com.tallerwebi.dominio.excepcion.PesoIncorrectoException;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.DatosLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorLogin {

    private ServicioLogin servicioLogin;

    @Autowired
    public ControladorLogin(ServicioLogin servicioLogin){
        this.servicioLogin = servicioLogin;
    }


    @RequestMapping(path = "/validar-login", method = RequestMethod.POST)
    public ModelAndView validarLogin(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
        ModelMap model = new ModelMap();

        Usuario usuarioBuscado = servicioLogin.consultarUsuario(usuario.getEmail(), usuario.getPassword());
        if (usuarioBuscado != null) {
            request.getSession().setAttribute("ROL", usuarioBuscado.getRol());
            return new ModelAndView("redirect:/menuprincipal");
        } else {
            model.put("error", "Usuario o clave incorrecta");
        }
        return new ModelAndView("iniciar-sesion", model);
    }

    @RequestMapping(path = "/registrarme", method = RequestMethod.POST)
    public ModelAndView registrarme(@ModelAttribute("usuario") Usuario usuario, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        List<String> errores = new ArrayList<>();
        try {
            servicioLogin.registrar(usuario);
            HttpSession session = request.getSession();
            session.setAttribute("usuario", usuario);
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



    @RequestMapping(path = "/", method = RequestMethod.GET)
    public ModelAndView inicio() {
        return new ModelAndView("redirect:/inicio");
    }
}

