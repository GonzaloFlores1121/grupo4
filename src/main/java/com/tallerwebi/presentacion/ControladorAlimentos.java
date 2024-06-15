package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorAlimentos {

    private ServicioCategoriaAlimento servicioCategoriaAlimentos;
    private ServicioAlimento servicioAlimento;
    private ServicioColacion servicioColacion;

    @Autowired
    public ControladorAlimentos(ServicioCategoriaAlimento servicioCategoriaAlimentos, ServicioAlimento servicioAlimento,ServicioColacion servicioColacion) {
        this.servicioCategoriaAlimentos = servicioCategoriaAlimentos;
        this.servicioAlimento = servicioAlimento;
        this.servicioColacion = servicioColacion;
    }

    @RequestMapping(path = "/categoria_alimentos", method = RequestMethod.GET)
    public ModelAndView verCategoriaAlimentos(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario != null) {
            model.addAttribute("usuario", usuario);
            List<CategoriaAlimento> listaCategoriaAlimentos = servicioCategoriaAlimentos.obtenerTodasLasCategorias();
            model.addAttribute("categorias", listaCategoriaAlimentos);
            return new ModelAndView("categoria_alimentos", model);
        }
        return new ModelAndView("redirect:/inicio");
    }

    @RequestMapping(path = "/buscarCategoria", method = RequestMethod.GET)
    public ModelAndView buscarCategoria(@RequestParam(value = "search", required = false) String search, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario !=  null) {
            model.addAttribute("usuario", usuario);
            List<CategoriaAlimento> categorias = servicioCategoriaAlimentos.obtenerCategoriasPorNombre(search);
            if(categorias.isEmpty()) {
                model.addAttribute("unknown", "Categoria no encontrada");
                categorias = servicioCategoriaAlimentos.obtenerTodasLasCategorias();
                model.addAttribute("categorias", categorias);
                return new ModelAndView("categoria_alimentos", model);
            }
            model.addAttribute("categorias", categorias);   
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
            model.addAttribute("usuario", usuario);
            try {
                CategoriaAlimento categoria = servicioCategoriaAlimentos.obtenerCategoriaPorId(id);
                model.put("categoria", categoria);
            } catch (Exception e) {
                model.put("error", e.getMessage());
            }            
            return new ModelAndView("alimentos", model);
        }
        return new ModelAndView("redirect:/inicio");
    }

    @RequestMapping(path = "/buscarAlimento/{idCategoria}", method = RequestMethod.GET)
    public ModelAndView buscarAlimento(@PathVariable Long idCategoria, @RequestParam(value = "search", required = false) String search, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario !=  null) {
            model.addAttribute("usuario", usuario);   
            CategoriaAlimento categoria = servicioCategoriaAlimentos.obtenerCategoriaPorId(idCategoria);
            List<Alimento> alimentos = servicioAlimento.listarAlimentosPorCategoriaYNombre(idCategoria, search);
            if(alimentos.isEmpty()) {
                model.addAttribute("unknown", "Alimento no encontrada");    
                model.addAttribute("categoria", categoria);
                return new ModelAndView("alimentos", model);
            }
            model.addAttribute("alimentos", alimentos);
            model.addAttribute("categoria", categoria);   
            return new ModelAndView("alimentos", model);                 
        }
        return new ModelAndView("redirect:/inicio");
    }

    @RequestMapping(path = "/alimentos/{id}", method = RequestMethod.GET)
    public ModelAndView irALimentos(@PathVariable Long id, HttpServletRequest request,@RequestParam(value = "from", defaultValue = "defaultFromValue") String from) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if(usuario != null) {
            model.addAttribute("usuario", usuario);
            Alimento alimento = servicioAlimento.obtenerAlimentosPorId(id);

            Integer cantidad= alimento.getCantidad();
            model.put("alimento", alimento);
            model.put("from",from);
            model.put("cantidad",cantidad);
            return new ModelAndView("detalles_alimento", model);        
        }
        return new ModelAndView("redirect:/inicio");
    }


}
