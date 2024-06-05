package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioCategoriaAlimentoTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioCategoriaAlimento repositorioCategoriaAlimento;

    @Test
    @Transactional
    @Rollback
    public void testObtenerTodosLasCategorias() {
        Integer esperado = 3;
        givenExisteCategoria(1L, "fruta");
        givenExisteCategoria(2L, "verduras");
        givenExisteCategoria(3L, "carne");
        List<CategoriaAlimento> categorias = whereObtenerCategorias();
        thenCategoriasObtenidas(esperado, categorias);
    }

    private void givenExisteCategoria(Long id, String nombre) {
        CategoriaAlimento categoria = new CategoriaAlimento();
        categoria.setId(id);
        categoria.setNombre(nombre);
        sessionFactory.getCurrentSession().save(categoria);
    }

    private List<CategoriaAlimento> whereObtenerCategorias() {
        return repositorioCategoriaAlimento.obtenerTodasLasCateogorias();
    }

    private void thenCategoriasObtenidas(Integer esperado, List<CategoriaAlimento> alimentos) {
        assertEquals(esperado, alimentos.size());
    }

    @Test
    @Transactional
    @Rollback
    public void testObtenerCategoriasPorNombreExitoso() {
        Integer esperado = 2;
        givenExisteCategoria(1L, "fruta");
        givenExisteCategoria(2L, "cereales");
        givenExisteCategoria(3L, "verduras");
        List<CategoriaAlimento> categorias = whereObtenerCategoriasPorNombre("ER");
        thenCategoriasObtenidas(esperado, categorias);
    }

    private List<CategoriaAlimento> whereObtenerCategoriasPorNombre(String nombre) {
        return repositorioCategoriaAlimento.obtenerCategoriasPorNombre(nombre);
    }

    @Test
    @Transactional
    @Rollback
    public void testObtenerCategoriasPorNombreFallido() {
        Integer esperado = 0;
        givenExisteCategoria(1L, "fruta");
        givenExisteCategoria(2L, "verduras");
        List<CategoriaAlimento> categorias = whereObtenerCategoriasPorNombre("CARNE");
        thenCategoriasObtenidas(esperado, categorias);
    }

}
