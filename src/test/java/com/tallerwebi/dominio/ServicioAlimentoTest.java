package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.tallerwebi.infraestructura.ServicioAlimentoImpl;

public class ServicioAlimentoTest {

    private RepositorioAlimento repositorioAlimento = mock(RepositorioAlimento.class);
    private ServicioAlimentoImpl servicioAlimento = new ServicioAlimentoImpl(repositorioAlimento);

    @Test
    public void testObtenerAlimentosPorIdExitoso() {
        Alimento esperado = givenExisteAlimento(1L, "manzana");
        Alimento resultado = whenObtenerAlimento(esperado.getId());
        thenAlimentoObtenido(esperado, resultado);
    }

    private Alimento givenExisteAlimento(Long id, String nombre) {
        Alimento alimento = new Alimento();
        alimento.setId(id);
        alimento.setNombre(nombre);
        when(repositorioAlimento.consultarAlimentoPorID(id)).thenReturn(alimento);
        return alimento;
    }

    private Alimento whenObtenerAlimento(Long id) {
        return servicioAlimento.obtenerAlimentosPorId(id);
    }

    private void thenAlimentoObtenido(Alimento esperado, Alimento resultado) {
        assertNotNull(resultado);
        verify(repositorioAlimento, times(1)).consultarAlimentoPorID(esperado.getId());
    }

    @Test
    public void testObtenerAlimentosPorIdFallido() {
        Alimento esperado = givenNoExisteAlimento(1L, "pescado");
        Alimento resultado = whenObtenerAlimento(esperado.getId());
        thenAlimentoNoObtenido(esperado, resultado);
    }

    private Alimento givenNoExisteAlimento(Long id, String nombre) {
        Alimento alimento = new Alimento();
        alimento.setId(id);
        alimento.setNombre(nombre);
        when(repositorioAlimento.consultarAlimentoPorID(id)).thenReturn(null);
        return alimento;
    }

    private void thenAlimentoNoObtenido(Alimento esperado, Alimento resultado) {
        assertNull(resultado);
        verify(repositorioAlimento, times(1)).consultarAlimentoPorID(esperado.getId());
    }

    @Test
    public void testListarAlimentos() {
        List<Alimento> esperado = givenExisteListaAlimento();
        List<Alimento> resultado = whenListarAlimentos();
        thenAlimentosObtenidos(esperado, resultado);
    }

    private List<Alimento> givenExisteListaAlimento() {
        List<Alimento> alimentos = new ArrayList<>();
        alimentos.add(new Alimento());
        alimentos.add(new Alimento());
        when(repositorioAlimento.consultarAlimentos()).thenReturn(alimentos);
        return alimentos;
    }

    private List<Alimento> whenListarAlimentos() {
        return servicioAlimento.listarAlimentos();
    }

    private void thenAlimentosObtenidos(List<Alimento> esperado, List<Alimento> resultado) {
        assertNotNull(resultado);
        assertEquals(esperado.size(), resultado.size());
        verify(repositorioAlimento, times(1)).consultarAlimentos();        
    } 

    @Test
    public void testListarAlimentosPorCategoriaYNombreExitoso() {
        String nombreAlimento = "manzana";
        CategoriaAlimento categoria = givenExisteCategoria(1L, "fruta");
        List<Alimento> esperado = givenExisteListaAlimentosConCategoriaYNombre(1L, nombreAlimento, categoria);
        List<Alimento> resultado = whenListarAlimentosPorCategoriaYNombre(categoria.getId(), nombreAlimento);
        thenAlimentoObtenidoPorCategoriaYNombre(esperado, resultado, categoria.getId(), nombreAlimento);
    }

    private CategoriaAlimento givenExisteCategoria(Long id, String nombre) {
        CategoriaAlimento categoria = new CategoriaAlimento();
        categoria.setId(id);
        categoria.setNombre(nombre);
        return categoria;
    }

    private List<Alimento> givenExisteListaAlimentosConCategoriaYNombre(Long id, String nombre, CategoriaAlimento categoria) {
        List<Alimento> alimentos = new ArrayList<>();
        Alimento alimento = new Alimento();
        alimento.setId(id);
        alimento.setNombre(nombre);
        alimento.setCategoria(categoria);
        alimentos.add(alimento);
        when(repositorioAlimento.consultarAlimentosPorCategoriaYNombre(categoria.getId(), nombre)).thenReturn(alimentos);        
        return alimentos;
    }

    private List<Alimento> whenListarAlimentosPorCategoriaYNombre(Long idCategoria, String nombre) {
        return servicioAlimento.listarAlimentosPorCategoriaYNombre(idCategoria, nombre);
    }

    private void thenAlimentoObtenidoPorCategoriaYNombre(List<Alimento> esperado, List<Alimento> resultado, Long idCategoria, String nombreAlimento) { 
        assertNotNull(resultado);
        assertEquals(esperado.size(), resultado.size());
        verify(repositorioAlimento, times(1)).consultarAlimentosPorCategoriaYNombre(idCategoria, nombreAlimento);
    }

    @Test
    public void testListarAlimentosPorCategoriaYNombreFallido() {
        String nombreAlimento = "pan";
        CategoriaAlimento categoria = givenExisteCategoria(1L, "frutas");
        givenNoExisteListaAlimentosConCategoriaYNombre(1L, nombreAlimento, categoria);
        List<Alimento> resultado = whenListarAlimentosPorCategoriaYNombre(categoria.getId(), nombreAlimento);
        thenAlimentoNoObtenidoPorCategoriaYNombre(resultado, categoria.getId(), nombreAlimento);
    }

    private List<Alimento> givenNoExisteListaAlimentosConCategoriaYNombre(Long id, String nombre, CategoriaAlimento categoria) {
        List<Alimento> alimentos = new ArrayList<>();
        Alimento alimento = new Alimento();
        alimento.setId(id);
        alimento.setNombre(nombre);
        alimento.setCategoria(categoria);
        alimentos.add(alimento);
        when(repositorioAlimento.consultarAlimentosPorCategoriaYNombre(categoria.getId(), nombre)).thenReturn(null);
        return alimentos;
    }

    private void thenAlimentoNoObtenidoPorCategoriaYNombre(List<Alimento> resultado, Long idCategoria, String nombreAlimento) {
        assertNull(resultado);
        verify(repositorioAlimento, times(1)).consultarAlimentosPorCategoriaYNombre(idCategoria, nombreAlimento);
    }

}
