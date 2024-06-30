package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPago;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorPremium {

    private ServicioPago servicioPago;

    @Autowired
    public ControladorPremium(ServicioPago servicioPago) {

        this.servicioPago  = servicioPago;
    }

    @RequestMapping(value = "/contenidoPremium", method = RequestMethod.GET)
    public ModelAndView irAApartadoPremium(HttpServletRequest request) {
        HttpSession session = request.getSession();
        Usuario usuario= (Usuario) session.getAttribute("usuario");
        if (usuario != null && servicioPago.isPremiumUser(usuario.getEmail())) {
            return new ModelAndView("contenidoPremium");
        } else {
            return new ModelAndView("accesoDenegado");
        }
    }
}
