package com.tallerwebi.dominio;

import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioHistorialPesoUsuarioTest {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioHistorialPesoUsuario repositorioHistorialPesoUsuario;

    @Test
    @Transactional
    @Rollback
    public void modificoPesoUsuario() {
        Usuario usuario = givenTengoUnUsuario();

        whenModificoElPeso(70.0, usuario);

        thenSeActualizaElPeso(usuario);

    }

    private void thenSeActualizaElPeso(Usuario usuario) {
        assertEquals(70.0, usuario.getPeso());
    }

    private void whenModificoElPeso(double v, Usuario usuario) {
        repositorioHistorialPesoUsuario.modificarPeso(v, usuario);
        sessionFactory.getCurrentSession().refresh(usuario);

    }

    private Usuario givenTengoUnUsuario() {
        Usuario usuario = new Usuario();
        usuario.setId( 1L);
        usuario.setNombre("Mili");
        usuario.setPeso(56.0);
        usuario.setEmail("mili@gmail.com");
        sessionFactory.getCurrentSession().save(usuario);
        return usuario;
    }

   /*
   @Test
    @Transactional
    @Rollback
    public void obtengoHistorialPesoUsuario() {
        Usuario usuario = givenTengoUnUsuario();
        whenAgregoUnHistorial(70.0, usuario);
        thenObtengoElHistorialDelPesoDelUsuario(usuario);
    }

    private void whenAgregoUnHistorial(Double peso, Usuario usuario) {
        HistoriaPesoUsuario historial = new HistoriaPesoUsuario();
        historial.setPeso(peso);
        historial.setUsuario(usuario);
        historial.setFecha(new java.sql.Date(System.currentTimeMillis()));
        sessionFactory.getCurrentSession().save(historial);
    }

    private void thenObtengoElHistorialDelPesoDelUsuario(Usuario usuario) {
        List<HistoriaPesoUsuario> lista = repositorioHistorialPesoUsuario.obtenerHistorialPesoUsuario(usuario);
        assertEquals(1, lista.size());
    }*/


}

