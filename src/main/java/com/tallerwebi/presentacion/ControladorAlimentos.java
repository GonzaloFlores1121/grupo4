package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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

    @RequestMapping(value = "/categoria_alimentos", method = RequestMethod.GET)
    public ModelAndView verCategoriaAlimentos(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        obtenerUsuarioSession(request, model);
        List<CategoriaAlimento> listaCategoriaAlimentos = servicioCategoriaAlimentos.obtenerTodasLasCategorias();
        model.put("categorias", listaCategoriaAlimentos);
        return new ModelAndView("categoria_alimentos", model);
    }

    @RequestMapping(value = "/categorias/{id}", method = RequestMethod.GET)
    public ModelAndView irACategoria(@PathVariable Long id, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        obtenerUsuarioSession(request, model);
        try {
            CategoriaAlimento categoria = servicioCategoriaAlimentos.obtenerCategoriaPorId(id);
            model.put("categoria", categoria);
        } catch (Exception e) {
            model.put("error", e.getMessage());
        }
        return new ModelAndView("alimentos", model);
    }

    @RequestMapping(value = "/alimentos/{id}", method = RequestMethod.GET)
    public ModelAndView irALimentos(@PathVariable Long id, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        obtenerUsuarioSession(request, model);
        Alimento alimento = servicioAlimento.obtenerAlimentosPorId(id);
        model.put("alimento", alimento);
        return new ModelAndView("detalles_alimento", model);
    }


    private void obtenerUsuarioSession(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
    }
}

