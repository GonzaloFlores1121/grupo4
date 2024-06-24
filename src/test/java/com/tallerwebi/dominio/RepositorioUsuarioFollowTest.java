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
public class RepositorioUsuarioFollowTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired 
    private RepositorioUsuarioFollow repositorioUsuarioFollow;
    
    @Test
    @Transactional
    @Rollback
    public void testObtenerUsuarioSeguidorExitoso() {
        Usuario usuario = givenExisteUsuario(1L, "pacolo");
        Usuario seguidor = givenExisteUsuario(2L, "aviantrix");
        givenExisteUsuarioSeguidor(1L, usuario, seguidor);
        UsuarioFollow usuarioSeguidor = whenObtenerUsuarioSeguidor(usuario.getId(), seguidor.getId());
        thenUsuarioSeguidorObtenido(usuarioSeguidor, usuario, seguidor);
    };

    private Usuario givenExisteUsuario(Long id, String nombre) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre(nombre);
        sessionFactory.getCurrentSession().save(usuario);
        return usuario;
    }

    private void givenExisteUsuarioSeguidor(Long id, Usuario usuario, Usuario seguidor) {
        UsuarioFollow usuarioSeguidor = new UsuarioFollow();
        usuarioSeguidor.setId(id);
        usuarioSeguidor.setUsuario(usuario);
        usuarioSeguidor.setSeguidor(seguidor);
        sessionFactory.getCurrentSession().save(usuarioSeguidor);
    }

    private UsuarioFollow whenObtenerUsuarioSeguidor(Long idUsuario, Long idSeguidor) {
        return repositorioUsuarioFollow.get(idUsuario, idSeguidor);
    }

    private void thenUsuarioSeguidorObtenido(UsuarioFollow usuarioSeguidor, Usuario usuario, Usuario seguidor) {
        assertNotNull(usuarioSeguidor);
        assertEquals(usuarioSeguidor.getUsuario(), usuario);
        assertEquals(usuarioSeguidor.getSeguidor(), seguidor);
    }

    @Test
    @Transactional
    @Rollback
    public void testObtenerUsuarioSeguidorFallido() {
        Usuario usuario = givenExisteUsuario(1L, "pacolo");
        Usuario seguidor = givenExisteUsuario(2L, "aviantrix");
        givenNoExisteUsuarioSeguidor();
        UsuarioFollow usuarioSeguidor = whenObtenerUsuarioSeguidor(usuario.getId(), seguidor.getId());
        thenUsuarioSeguidorNoObtenido(usuarioSeguidor);
    }

    private void givenNoExisteUsuarioSeguidor() {}

    private void thenUsuarioSeguidorNoObtenido(UsuarioFollow usuarioSeguidor) {
        assertNull(usuarioSeguidor);
    }

    /* 
    @Test
    @Transactional
    @Rollback
    public void testObtenerTodosLosUsuariosSeguidoresPorIdExitoso() {
        Integer esperado = 2;
        Usuario usuario = givenExisteUsuario(1L, "pacolo");
        Usuario seguidor1 = givenExisteUsuario(2L, "aviantrix");
        Usuario seguidor2 = givenExisteUsuario(3L, "pwnies");
        givenExisteUsuarioSeguidor(1L, usuario, seguidor1);
        givenExisteUsuarioSeguidor(2L, usuario, seguidor2);
        List<UsuarioFollow> seguidores = whenObtenerTodosLosUsuariosSeguidores(usuario.getId());
        thenUsuariosSeguidoresObtenidos(esperado, seguidores);
    }

    private List<UsuarioFollow> whenObtenerTodosLosUsuariosSeguidores(Long idUsuario) {
        return repositorioUsuarioFollow.getAllFollows(idUsuario);
    }

    private void thenUsuariosSeguidoresObtenidos(Integer esperado, List<UsuarioFollow> seguidores) {
        assertEquals(esperado, seguidores.size());
    }*/

}
