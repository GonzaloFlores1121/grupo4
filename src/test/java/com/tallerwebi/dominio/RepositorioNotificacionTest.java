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
public class RepositorioNotificacionTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioNotificacion repositorioNotificacion;

    @Test
    @Transactional
    @Rollback
    public void testObtenerTodosLasNotificaciones() {
        Integer esperado = 2;
        givenExisteNotificacion();
        givenExisteNotificacion();
        List<Notificacion> notificaciones = whenBuscoNotificaciones();
        thenObtenerNotificaciones(esperado, notificaciones);
    }

    private void givenExisteNotificacion() {
        Notificacion notificacion = new Notificacion();
        sessionFactory.getCurrentSession().save(notificacion);
    }

    private List<Notificacion> whenBuscoNotificaciones() {
        return repositorioNotificacion.getAll();
    }

    private void thenObtenerNotificaciones(Integer esperado, List<Notificacion> notificaciones) { 
        assertEquals(esperado, notificaciones.size());
    }

}
