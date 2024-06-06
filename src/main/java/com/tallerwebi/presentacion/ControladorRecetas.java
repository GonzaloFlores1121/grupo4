package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.RecetaNoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorRecetas {
    private ServicioReceta servicioRecetas;

    @Autowired
    public ControladorRecetas(ServicioReceta servicioRecetas) {
        this.servicioRecetas = servicioRecetas;
    }

    @RequestMapping(value = "/recetas", method = RequestMethod.GET)
    public ModelAndView irARecetas(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        obtenerUsuarioSession(request, model);

        List<Receta> todasLasRecetas = servicioRecetas.obtenerTodasLasRecetas();

        model.put("recetas", todasLasRecetas);

        return new ModelAndView("recetas", model);
    }

    @RequestMapping(value = "/recetasFavoritas", method = RequestMethod.GET)
    public ModelAndView mostrarRecetasFavoritas(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        obtenerUsuarioSession(request, model);
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        List<RecetaFavorito> recetasFavoritas = servicioRecetas.obtenerRecetasFavoritas(usuario);

        model.put("recetasFavoritas", recetasFavoritas);

        return new ModelAndView("recetasFavoritas", model);
    }


    @RequestMapping(value = "/recetas/{id}",method = RequestMethod.GET)
    public ModelAndView mostrarDescripcionRecetas(@PathVariable Long id, HttpServletRequest request) throws RecetaNoEncontradaException {
        ModelMap model = new ModelMap();
        obtenerUsuarioSession(request, model);
        Receta receta=servicioRecetas.obtenerRecetaPorId(id);
        List <Alimento> alimentos= receta.getAlimentoRecetas().stream()
                .map(AlimentoReceta::getAlimento)
                .collect(Collectors.toList());
        model.put("receta",receta);
        model.put("alimentos",alimentos);
        return new ModelAndView("descripcionRecetas",model);
    }

    @RequestMapping(value = "/agregarAFavoritos/{id}",method = RequestMethod.GET)
    public ModelAndView agregarAFavoritos(@PathVariable Long id, HttpServletRequest request) throws RecetaNoEncontradaException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Receta receta=servicioRecetas.obtenerRecetaPorId(id);
        servicioRecetas.agregarRecetaFavorita(usuario,receta);
        return new ModelAndView("redirect:/recetas");
    }

    @RequestMapping(value = "/eliminarReceta/{id}",method = RequestMethod.GET)
    public ModelAndView eliminarReceta(@PathVariable Long id, HttpServletRequest request) throws RecetaNoEncontradaException {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        RecetaFavorito receta=servicioRecetas.obtenerRecetaFavoritaPorId(id);
        try{ servicioRecetas.eliminarReceta(usuario,receta);}
        catch (RecetaNoEncontradaException e) {

        }
        return new ModelAndView("redirect:/recetasFavoritas");
    }

    private void obtenerUsuarioSession(HttpServletRequest request, ModelMap model) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        model.addAttribute("usuario", usuario);
    }
}