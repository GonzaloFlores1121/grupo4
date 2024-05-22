package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.AlimentoReceta;
import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.ServicioReceta;
import com.tallerwebi.dominio.excepcion.RecetaNoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ControladorRecetas {
private ServicioReceta servicioRecetas;

    @Autowired
    public ControladorRecetas(ServicioReceta servicioRecetas) {
        this.servicioRecetas = servicioRecetas;
    }

    @RequestMapping(value = "/recetas",method = RequestMethod.GET)
    public ModelAndView irARecetas() {
        ModelMap model = new ModelMap();
        List<Receta> recetas = servicioRecetas.obtenerTodasLasRecetas();
        model.put("recetas", recetas);

        return new ModelAndView("recetas", model);
    }


    @RequestMapping(value = "/recetas/{id}",method = RequestMethod.GET)
    public ModelAndView mostrarDescripcionRecetas(@PathVariable Long id) throws RecetaNoEncontradaException {
        ModelMap model = new ModelMap();
        Receta receta=servicioRecetas.obtenerRecetaPorId(id);
        List <Alimento> alimentos= receta.getAlimentoRecetas().stream()
                                   .map(AlimentoReceta::getAlimento)
                                   .collect(Collectors.toList());
        model.put("receta",receta);
        model.put("alimentos",alimentos);
        return new ModelAndView("descripcionRecetas",model);
    }
}
