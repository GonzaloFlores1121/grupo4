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
public class RepositorioAlimentoTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioAlimento repositorioAlimento;

    @Test
    @Transactional
    @Rollback
    public void testObtenerTodosLosAlimentos() {
        Integer esperado = 3;
        CategoriaAlimento categoria = givenExisteCategoria(1L, "fruta");
        givenExisteAlimento(1L, "banana", categoria);
        givenExisteAlimento(2L, "uvas", categoria);
        givenExisteAlimento(3L, "pera", categoria);
        List<Alimento> alimentos = whereObtenerAlimentos();
        thenAlimentosObtenidos(esperado, alimentos);
    }

    private CategoriaAlimento givenExisteCategoria(Long id, String nombre) {
        CategoriaAlimento categoria = new CategoriaAlimento();
        categoria.setId(id);
        categoria.setNombre(nombre);
        sessionFactory.getCurrentSession().save(categoria);
        return categoria;
    }

    private void givenExisteAlimento(Long id, String nombre, CategoriaAlimento categoria) {
        Alimento alimento = new Alimento();
        alimento.setId(id);
        alimento.setNombre(nombre);
        alimento.setCategoria(categoria);
        sessionFactory.getCurrentSession().save(alimento);
    }

    private List<Alimento> whereObtenerAlimentos() {
        return repositorioAlimento.consultarAlimentos();
    }

    private void thenAlimentosObtenidos(Integer esperado, List<Alimento> alimentos) {
        assertEquals(esperado, alimentos.size());
    }

    @Test
    @Transactional
    @Rollback
    public void testObtenerAlimentoPorNombre() {
        Integer esperado = 2;
        CategoriaAlimento categoria = givenExisteCategoria(1L, "fruta");
        givenExisteAlimento(1L, "manzana", categoria);
        givenExisteAlimento(2L, "naranja", categoria);
        givenExisteAlimento(3L, "uvas", categoria);
        givenExisteAlimento(4L, "banana", categoria);
        List<Alimento> alimentos = whereObtenerAlimentosPorNombre(categoria, "ANA");
        thenAlimentosObtenidos(esperado, alimentos);
    }

    private List<Alimento> whereObtenerAlimentosPorNombre(CategoriaAlimento categoria, String nombre) {
        return repositorioAlimento.consultarAlimentosPorCategoriaYNombre(categoria.getId(), nombre);
    }

    @Test
    @Transactional
    @Rollback
    public void testNoObtenerAlimentosPorNombreIncorrecto() {
        Integer esperado = 0;
        CategoriaAlimento categoria = givenExisteCategoria(1L, "fruta");
        givenExisteAlimento(1L, "manzana", categoria);
        givenExisteAlimento(2L, "banana", categoria);
        List<Alimento> alimentos = whereObtenerAlimentosPorNombre(categoria, "pacolo");
        thenAlimentosObtenidos(esperado, alimentos);
    }

    @Test
    @Transactional
    @Rollback
    public void testNoObtenerAlimentosPorIdCategoriaIncorrecto() {
        Integer esperado = 0;
        CategoriaAlimento frutas = givenExisteCategoria(1L, "fruta");
        CategoriaAlimento verduras = givenExisteCategoria(2L, "verduras");
        givenExisteAlimento(1L, "manzana", frutas);
        givenExisteAlimento(2L, "banana", frutas);
        givenExisteAlimento(3L, "anana", frutas);
        List<Alimento> alimentos = whereObtenerAlimentosPorNombre(verduras, "ana");
        thenAlimentosObtenidos(esperado, alimentos);
    }

}
