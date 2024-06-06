package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorAlimentos {

    private ServicioCategoriaAlimento servicioCategoriaAlimentos;
    private ServicioAlimento servicioAlimento;

    @Autowired
    public ControladorAlimentos(ServicioCategoriaAlimento servicioCategoriaAlimentos, ServicioAlimento servicioAlimento) {
        this.servicioCategoriaAlimentos = servicioCategoriaAlimentos;
        this.servicioAlimento = servicioAlimento;
    }

    @RequestMapping(path = "/categoria_alimentos", method = RequestMethod.GET)
    public ModelAndView verCategoriaAlimentos(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario != null) {
            model.addAttribute("usuario", usuario);
            List<CategoriaAlimento> listaCategoriaAlimentos = servicioCategoriaAlimentos.obtenerTodasLasCategorias();
            model.put("categorias", listaCategoriaAlimentos);
            return new ModelAndView("categoria_alimentos", model);
        }
        return new ModelAndView("redirect:/inicio");
    }

    @RequestMapping(path = "/categorias/{id}", method = RequestMethod.GET)
    public ModelAndView irACategoria(@PathVariable Long id, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario != null) {
            try {
                model.addAttribute("usuario", usuario);
                CategoriaAlimento categoria = servicioCategoriaAlimentos.obtenerCategoriaPorId(id);
                model.put("categoria", categoria);
            } catch (Exception e) {
                model.put("error", e.getMessage());
            }            
            return new ModelAndView("alimentos", model);
        }
        return new ModelAndView("redirect:/inicio");
    }

    @RequestMapping(path = "/alimentos/{id}", method = RequestMethod.GET)
    public ModelAndView irALimentos(@PathVariable Long id, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario != null) {
            model.addAttribute("usuario", usuario);
            Alimento alimento = servicioAlimento.obtenerAlimentosPorId(id);
            model.put("alimento", alimento);
            return new ModelAndView("detalles_alimento", model);        
        }
        return new ModelAndView("redirect:/inicio");
    }

}
