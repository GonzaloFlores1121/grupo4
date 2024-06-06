package com.tallerwebi.presentacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.CategoriaAlimento;
import com.tallerwebi.dominio.ServicioAlimento;
import com.tallerwebi.dominio.ServicioCategoriaAlimento;
import com.tallerwebi.dominio.Usuario;

public class ControladorAlimentosTest {

    private ServicioCategoriaAlimento servicioCategoriaAlimentos = mock(ServicioCategoriaAlimento.class);
    private ServicioAlimento servicioAlimento = mock(ServicioAlimento.class);
    private ControladorAlimentos controladorAlimentos = new ControladorAlimentos(servicioCategoriaAlimentos, servicioAlimento);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpSession session = mock(HttpSession.class);

    @BeforeEach
    public void setUp() {
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testVerCategoriaAlimentosExitoso() {
        Usuario usuario = givenExisteUsuario();
        List<CategoriaAlimento> categorias = givenExistenCategorias();
        ModelAndView vista = whenObtenerCategoriasAlimentos();
        thenObtenerVistaCategoriasAlimentos(usuario, categorias, vista);
    }
    
    private Usuario givenExisteUsuario() {
        Usuario usuario = new Usuario();
        when(session.getAttribute("usuario")).thenReturn(usuario);
        return usuario;
    }
    private List<CategoriaAlimento> givenExistenCategorias() {
        List<CategoriaAlimento> categorias = new ArrayList<>();
        categorias.add(new CategoriaAlimento());
        categorias.add(new CategoriaAlimento());
        when(servicioCategoriaAlimentos.obtenerTodasLasCategorias()).thenReturn(categorias);
        return categorias;
    }

    private ModelAndView whenObtenerCategoriasAlimentos() {
        return controladorAlimentos.verCategoriaAlimentos(request);
    }

    private void thenObtenerVistaCategoriasAlimentos(Usuario usuario, List<CategoriaAlimento> categorias, ModelAndView vista) {
        assertEquals("categoria_alimentos", vista.getViewName());
        assertEquals(usuario, vista.getModelMap().get("usuario"));
        assertEquals(categorias, vista.getModelMap().get("categorias"));        
    }

    @Test
    public void testVerCategoriaAlimentosFallido() {
        givenNoExisteUsuario();
        ModelAndView vista = whenObtenerCategoriasAlimentos();
        thenRedirigirAInicio(vista);
    }    

    private void givenNoExisteUsuario() {
        when(session.getAttribute("usuario")).thenReturn(null);
    }

    private void thenRedirigirAInicio(ModelAndView vista) {
        assertEquals("redirect:/inicio", vista.getViewName());
    }

    @Test
    public void testIrACategoriaExitoso() {
        Usuario usuario = givenExisteUsuario();
        CategoriaAlimento categoria = givenExisteCategoria(1L, "frutas");
        ModelAndView vista = whenObtenerCategoria(categoria.getId());
        thenObtenerVistaIrACategoria(usuario, categoria, vista);
    }

    private CategoriaAlimento givenExisteCategoria(Long id, String nombre) {
        CategoriaAlimento categoria = new CategoriaAlimento();
        categoria.setId(id);
        categoria.setNombre(nombre);
        List<CategoriaAlimento> categorias = new ArrayList<>();
        categorias.add(categoria);
        when(servicioCategoriaAlimentos.obtenerCategoriaPorId(categoria.getId())).thenReturn(categoria);        
        return categoria;
    } 

    private ModelAndView whenObtenerCategoria(Long id) {
        return controladorAlimentos.irACategoria(id, request);
    }

    private void thenObtenerVistaIrACategoria(Usuario usuario, CategoriaAlimento categoria, ModelAndView vista) {
        assertEquals("alimentos", vista.getViewName());
        assertEquals(usuario, vista.getModelMap().get("usuario"));
        assertEquals(categoria, vista.getModelMap().get("categoria"));        
    }
 
    @Test
    public void testIrACategoriaFallido() {
        givenNoExisteUsuario();
        ModelAndView vista = whenObtenerCategoria(1L);
        thenRedirigirAInicio(vista);
    }

    @Test
    public void testIrALimentosExitoso() {
        Usuario usuario = givenExisteUsuario();
        Alimento alimento = givenExisteAlimento(1L, "manzana");
        ModelAndView vista = whenObtenerAlimento(alimento.getId());
        thenObtenerVistaIrAliemento(usuario, alimento, vista);
    }  

    private Alimento givenExisteAlimento(Long id, String nombre) {
        Alimento alimento = new Alimento();
        alimento.setId(id);
        alimento.setNombre(nombre);
        when(servicioAlimento.obtenerAlimentosPorId(id)).thenReturn(alimento);
        return alimento;        
    }

    private ModelAndView whenObtenerAlimento(Long id) {
        return controladorAlimentos.irALimentos(id, request);
    }

    private void thenObtenerVistaIrAliemento(Usuario usuario, Alimento alimento, ModelAndView vista) {
        assertEquals("detalles_alimento", vista.getViewName());
        assertEquals(usuario, vista.getModelMap().get("usuario"));
        assertEquals(alimento, vista.getModelMap().get("alimento"));
    }

    @Test
    public void testIrALimentosFallido() {
        givenNoExisteUsuario();
        ModelAndView vista = whenObtenerAlimento(1L);
        thenRedirigirAInicio(vista);
    }

}
