package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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
public class RepositorioNotificacionUsuarioTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioNotificacionUsuario repositorioNotificacionUsuario;

    @Test
    @Transactional
    @Rollback
    public void testObtenerNotificacionUsuarioPorIdNotificacionYIdUsuario() {
        Long idNotificacion = 1L;
        Long idUsuario = 1L;
        givenExisteNotificacionUsuario(idNotificacion, idUsuario);
        NotificacionUsuario notificacionUsuario = whenBuscarNotificacionUsuario(idNotificacion, idUsuario);
        thenObtenerNotificacionUsuario(notificacionUsuario);
    }

    private void givenExisteNotificacionUsuario(Long idNotificacion, Long idUsuario) {
        Notificacion notificacion = new Notificacion();
        notificacion.setId(idNotificacion);
        sessionFactory.getCurrentSession().save(notificacion);
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        sessionFactory.getCurrentSession().save(usuario);
        NotificacionUsuario notificacionUsuario = new NotificacionUsuario();
        notificacionUsuario.setNotificacion(notificacion);
        notificacionUsuario.setUsuario(usuario);
        sessionFactory.getCurrentSession().save(notificacionUsuario);
    }

    private NotificacionUsuario whenBuscarNotificacionUsuario(long idNotificacion, Long idUsuario) {
        return repositorioNotificacionUsuario.get(idNotificacion, idUsuario);
    }

    private void thenObtenerNotificacionUsuario(NotificacionUsuario notificacionUsuario) {
        assertNotNull(notificacionUsuario);
    }

    @Test
    @Transactional
    @Rollback
    public void testNoObtenerUsuarioInexistente() {
        Long idNotificacion = 2L;
        Long idUsuario = 2L;
        givenNoExisteNotificacionUsuario();
        NotificacionUsuario notificacionUsuario = whenBuscarNotificacionUsuario(idNotificacion, idUsuario);
        thenNoObtenerNotificacionUsuario(notificacionUsuario);
    }

    private void givenNoExisteNotificacionUsuario() {}

    private void thenNoObtenerNotificacionUsuario(NotificacionUsuario notificacionUsuario) {
        assertNull(notificacionUsuario);
    }

    @Test
    @Transactional
    @Rollback
    public void testObtenerNotificacionesUsuarios() {
        Integer esperado = 3;
        givenExisteNotificacionUsuario(1L, 1L);
        givenExisteNotificacionUsuario(2L, 2L);
        givenExisteNotificacionUsuario(3L, 3L);
        List<NotificacionUsuario> notificacionesUsuarios = whenBuscarNotificacionesUsuarios();
        thenObtenerNotificacionesUsuarios(esperado, notificacionesUsuarios); 
    }

    private List<NotificacionUsuario> whenBuscarNotificacionesUsuarios() {
        return repositorioNotificacionUsuario.getAll();
    }

    private void thenObtenerNotificacionesUsuarios(Integer esperado, List<NotificacionUsuario> notificacionUsuarios) {
        assertEquals(esperado, notificacionUsuarios.size());
    }

    @Test
    @Transactional
    @Rollback
    public void testObtenerNotificacionesPorIdUsuario() {
        Integer esperado  = 2;
        Usuario usuario = givenExisteUsuario(1L);
        givenExisteNotificacionesUsuarios(usuario, 1L);
        givenExisteNotificacionesUsuarios(usuario, 2L);
        List<Notificacion> notificaciones = whenBuscoNotificaciones(usuario.getId());
        thenObtenerNotificaciones(esperado, notificaciones);
    }

    private Usuario givenExisteUsuario(Long idUsuario) {
        Usuario usuario = new Usuario();
        usuario.setId(idUsuario);
        sessionFactory.getCurrentSession().save(usuario);
        return usuario;
    }

    private void givenExisteNotificacionesUsuarios(Usuario usuario, Long idNotificacion) {
        Notificacion notificacion = new Notificacion();
        notificacion.setId(idNotificacion);
        sessionFactory.getCurrentSession().save(notificacion);
        NotificacionUsuario notificacionUsuario = new NotificacionUsuario();
        notificacionUsuario.setUsuario(usuario);
        notificacionUsuario.setNotificacion(notificacion);
        sessionFactory.getCurrentSession().save(notificacionUsuario);
    }

    private List<Notificacion> whenBuscoNotificaciones(Long idUsuario) {
        return repositorioNotificacionUsuario.getAllNotification(idUsuario);
    } 

    private void thenObtenerNotificaciones(Integer esperado, List<Notificacion> notificaciones) {
        assertEquals(esperado, notificaciones.size());
    }

}
