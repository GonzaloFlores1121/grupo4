package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;

import com.tallerwebi.dominio.excepcion.AlimentoNoEncontradoException;
import com.tallerwebi.dominio.excepcion.CategoriaAlimentoNoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorAlimentos {

    private ServicioCategoriaAlimento servicioCategoriaAlimentos;
    private ServicioAlimento servicioAlimento;
    private ServicioColacion servicioColacion;

    @Autowired
    public ControladorAlimentos(ServicioCategoriaAlimento servicioCategoriaAlimentos, ServicioAlimento servicioAlimento, ServicioColacion servicioColacion) {
        this.servicioCategoriaAlimentos = servicioCategoriaAlimentos;
        this.servicioAlimento = servicioAlimento;
        this.servicioColacion = servicioColacion;
    }

    @RequestMapping(path = "/categoria_alimentos", method = RequestMethod.GET)
    public ModelAndView verCategoriaAlimentos(HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
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
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            List<CategoriaAlimento> categorias = servicioCategoriaAlimentos.obtenerCategoriasPorNombre(search);
            if (categorias.isEmpty()) {
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
    public ModelAndView irACategoria(@PathVariable Long id, HttpServletRequest request)
            throws CategoriaAlimentoNoEncontradaException {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return new ModelAndView("redirect:/inicio");
        }
        model.put("usuario", usuario);

        CategoriaAlimento categoria = servicioCategoriaAlimentos.obtenerCategoriaPorId(id);
        if (categoria == null) {
            throw new CategoriaAlimentoNoEncontradaException(id);
        }
        model.put("categoria", categoria);
        return new ModelAndView("alimentos", model);
    }


    @RequestMapping(path = "/buscarAlimento/{idCategoria}", method = RequestMethod.GET)
    public ModelAndView buscarAlimento(@PathVariable Long idCategoria, @RequestParam(value = "search", required = false) String search, HttpServletRequest request) {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        if (usuario != null) {
            model.addAttribute("usuario", usuario);
            CategoriaAlimento categoria = servicioCategoriaAlimentos.obtenerCategoriaPorId(idCategoria);
            List<Alimento> alimentos = servicioAlimento.listarAlimentosPorCategoriaYNombre(idCategoria, search);
            if (alimentos.isEmpty()) {
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
    public ModelAndView irALimentos(@PathVariable Long id, HttpServletRequest request, @RequestParam(value = "from", defaultValue = "defaultFromValue") String from)
            throws AlimentoNoEncontradoException {
        ModelMap model = new ModelMap();
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        if (usuario == null) {
            return new ModelAndView("redirect:/inicio");
        }
        model.put("usuario", usuario);
        Alimento alimento = servicioAlimento.obtenerAlimentosPorId(id);

        if (alimento == null) {
            throw new AlimentoNoEncontradoException(id);
        }

        // Obtiene la categoría del alimento
        CategoriaAlimento categoria = alimento.getCategoria();
        model.put("categoriaId", categoria.getId()); // Pasa el ID de la categoría a la vista
        model.put("categoriaNombre", categoria.getNombre()); // Pasa el nombre de la categoría a la vista

        Colacion colacion = servicioColacion.obtenerColacionPorAlimento(alimento);
        if (colacion != null) {
            model.put("tipoColacion", colacion.getTipo().ordinal());
            LocalDate fechaColacion = colacion.getFecha();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String fechaFormateada = fechaColacion.format(formatter);
            model.put("fechaColacion", fechaFormateada);
        }

        Integer cantidad = alimento.getCantidad();
        model.put("alimento", alimento);
        model.put("from", from);
        model.put("cantidad", cantidad);
        return new ModelAndView("detalles_alimento",model);
    }
}



