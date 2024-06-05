package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.tallerwebi.infraestructura.ServicioCategoriaAlimentoImpl;

public class ServicioCategoriaAlimentoTest {

    RepositorioCategoriaAlimento repositorioCategoriaAlimento = mock(RepositorioCategoriaAlimento.class);
    ServicioCategoriaAlimentoImpl servicioCategoriaAlimento = new ServicioCategoriaAlimentoImpl(repositorioCategoriaAlimento);

    @Test
    public void testObtenerCategoriaPorIdExitoso() {
        CategoriaAlimento esperado = givenExisteCategoria(1L, "frutas");
        CategoriaAlimento resultado = whenObtenerCategoria(esperado.getId());
        thenCategoriaObtenida(esperado, resultado);
    }

    private CategoriaAlimento givenExisteCategoria(Long id, String nombre) {
        CategoriaAlimento categoria = new CategoriaAlimento();
        categoria.setId(id);
        categoria.setNombre(nombre);
        when(repositorioCategoriaAlimento.obtenerCategoriaPorId(id)).thenReturn(categoria);
        return categoria;
    }

    private CategoriaAlimento whenObtenerCategoria(Long id) {
        return servicioCategoriaAlimento.obtenerCategoriaPorId(id);
    }

    private void thenCategoriaObtenida(CategoriaAlimento esperado, CategoriaAlimento resultado) {
        assertNotNull(resultado);
        assertEquals(esperado.getNombre(), resultado.getNombre());
        verify(repositorioCategoriaAlimento, times(1)).obtenerCategoriaPorId(esperado.getId());
    }   

    @Test
    public void testObtenerCategoriaPorIdFallido() {
        CategoriaAlimento categoria = givenNoExisteCategoria(1L, "verduras");
        Exception exception = whenObtenerException(categoria.getId());
        thenExceptionObtenida(exception, categoria);
    }

    private CategoriaAlimento givenNoExisteCategoria(Long id, String nombre) {
        CategoriaAlimento categoria = new CategoriaAlimento();
        categoria.setId(id);
        categoria.setNombre(nombre);
        when(repositorioCategoriaAlimento.obtenerCategoriaPorId(id)).thenReturn(null);
        return categoria;
    }

    private Exception whenObtenerException(Long id) {
        Exception exception = assertThrows(RuntimeException.class, () -> {
            servicioCategoriaAlimento.obtenerCategoriaPorId(1L);
        });
        return exception;
    }

    private void thenExceptionObtenida(Exception exception, CategoriaAlimento categoria) {
        assertEquals("Categoria no encontrada", exception.getMessage());
        verify(repositorioCategoriaAlimento, times(1)).obtenerCategoriaPorId(categoria.getId()); 
    }
   
    @Test
    public void testObtenerTodasLasCategorias() {
        List<CategoriaAlimento> esperado = givenExisteListaCategorias();
        List<CategoriaAlimento> resultado = whenObtenerCategorias();
        thenCategoriasObtenidas(esperado, resultado);
    }    

    private List<CategoriaAlimento> givenExisteListaCategorias () {
        List<CategoriaAlimento> categorias = new ArrayList<>();
        categorias.add(new CategoriaAlimento());
        categorias.add(new CategoriaAlimento());
        when(repositorioCategoriaAlimento.obtenerTodasLasCateogorias()).thenReturn(categorias);
        return categorias;
    }

    private List<CategoriaAlimento> whenObtenerCategorias() {
        return servicioCategoriaAlimento.obtenerTodasLasCategorias();
    }

    private void thenCategoriasObtenidas(List<CategoriaAlimento> esperado, List<CategoriaAlimento> resultado) {
        assertNotNull(resultado);
        assertEquals(esperado.size(), resultado.size());
        verify(repositorioCategoriaAlimento, times(1)).obtenerTodasLasCateogorias();
    }

    @Test
    public void testObtenerCategoriasPorNombre() {
        String nombreCategoria = "frutas";
        List<CategoriaAlimento> esperado = givenExisteListaCategoriasConNombre(nombreCategoria);
        List<CategoriaAlimento> resultado = whenObtenerCategoriasPorNombre(nombreCategoria);
        thenCategoriaObtenidasPorNombre(esperado, resultado, nombreCategoria);
    }    

    private List<CategoriaAlimento> givenExisteListaCategoriasConNombre(String nombre) {
        List<CategoriaAlimento> categorias = new ArrayList<>();
        CategoriaAlimento categoria = new CategoriaAlimento();
        categoria.setNombre(nombre);
        categorias.add(categoria);
        when(repositorioCategoriaAlimento.obtenerCategoriasPorNombre(nombre)).thenReturn(categorias);
        return categorias;
    }

    private List<CategoriaAlimento> whenObtenerCategoriasPorNombre(String nombre) {
        return servicioCategoriaAlimento.obtenerCategoriasPorNombre(nombre);
    }

    private void thenCategoriaObtenidasPorNombre(List<CategoriaAlimento> esperado, List<CategoriaAlimento> resultado, String nombre) {
        assertNotNull(resultado);
        assertEquals(esperado.size(), resultado.size());
        assertEquals(nombre, resultado.get(0).getNombre());
        verify(repositorioCategoriaAlimento, times(1)).obtenerCategoriasPorNombre(nombre);        
    }

    @Test
    public void testNoObtenerCategoriasPorNombre() {
        String nombreCategoria = "verduras";
        givenNoExisteCategoriaPorNombre(nombreCategoria, "frutas");
        List<CategoriaAlimento> resultado = whenObtenerCategoriasPorNombre(nombreCategoria);
        thenCategoriasNoObtenidas(resultado, nombreCategoria);
    }

    private List<CategoriaAlimento> givenNoExisteCategoriaPorNombre(String nombreEsperado, String nombreResultado) {
        List<CategoriaAlimento> categorias = new ArrayList<>();
        CategoriaAlimento categoria = new CategoriaAlimento();
        categoria.setNombre(nombreResultado);
        categorias.add(categoria);
        when(repositorioCategoriaAlimento.obtenerCategoriasPorNombre(nombreEsperado)).thenReturn(null);
        return categorias;
    }

    private void thenCategoriasNoObtenidas(List<CategoriaAlimento> resultado, String nombreEsperado) {
        assertNull(resultado);
        verify(repositorioCategoriaAlimento, times(1)).obtenerCategoriasPorNombre(nombreEsperado);
    }

}
