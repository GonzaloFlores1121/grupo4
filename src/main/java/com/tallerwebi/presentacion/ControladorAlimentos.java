package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.CategoriaAlimento;
import com.tallerwebi.dominio.ServicioAlimento;
import com.tallerwebi.dominio.ServicioCategoriaAlimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorAlimentos {

   private ServicioCategoriaAlimento servicioCategoriaAlimentos;
    private ServicioAlimento servicioAlimento;

   @Autowired
    public ControladorAlimentos(ServicioCategoriaAlimento servicioCategoriaAlimentos,ServicioAlimento servicioAlimento){
        this.servicioCategoriaAlimentos = servicioCategoriaAlimentos;
        this.servicioAlimento=servicioAlimento;
    }

    @RequestMapping(value = "/categoria_alimentos",method = RequestMethod.GET)
    public ModelAndView verCategoriaAlimentos(){
        ModelMap model= new ModelMap();
        List<CategoriaAlimento> listaCategoriaAlimentos = servicioCategoriaAlimentos.obtenerTodasLasCategorias();
        model.put("categorias",listaCategoriaAlimentos);

        return new ModelAndView("categoria_alimentos",model);
    }

    @RequestMapping(value= "/categorias/{id}",method = RequestMethod.GET)
    public ModelAndView irACategoria(@PathVariable Long id){
        ModelMap model = new ModelMap();
        CategoriaAlimento categoria = servicioCategoriaAlimentos.obtenerCategoriaPorId(id);
        model.put("categoria",categoria);
        return new ModelAndView("alimentos",model);
    }

@RequestMapping(value = "/alimentos/{id}",method = RequestMethod.GET)
    public ModelAndView irALimentos(@PathVariable Long id){
        ModelMap model= new ModelMap();
    Alimento alimento= servicioAlimento.obtenerAlimentosPorId(id);
        model.put("alimento",alimento);

        return new ModelAndView("detalles_alimento",model);
    }

}
