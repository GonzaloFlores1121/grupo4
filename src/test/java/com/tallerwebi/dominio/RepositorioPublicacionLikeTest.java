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
public class RepositorioPublicacionLikeTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioPublicacionLike repositorioPublicacionLike;

    /*@Test
    @Transactional
    @Rollback
    public void testObtenerTodosLosMeGustaPorUsuario() {
        Integer esperado = 2;
        Usuario usuario = givenExisteUsuario(1L, "pacolo");
        Publicacion publicacion1 = givenExistePublicacion(1L, "test");
        Publicacion publicacion2 = givenExistePublicacion(2L, "otro test");
        givenExisteMeGusta(1L, publicacion1, usuario);
        givenExisteMeGusta(2L, publicacion2, usuario);
        List<PublicacionLike> likes = whenObtenerMeGustaPorUsuario(usuario.getId());
        thenMeGustaObtenidosPorUsuario(esperado, likes);
    }

    private Usuario givenExisteUsuario(Long id, String nombre) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre(nombre);
        sessionFactory.getCurrentSession().save(usuario);
        return usuario;
    }

    private Publicacion givenExistePublicacion(Long id, String contenido) {
        Publicacion publicacion = new Publicacion();
        publicacion.setId(id);
        publicacion.setTexto(contenido);
        sessionFactory.getCurrentSession().save(publicacion);
        return publicacion;
    }

    private void givenExisteMeGusta(Long id, Publicacion publicacion, Usuario usuario) {
        PublicacionLike like = new PublicacionLike();
        like.setId(id);
        like.setPublicacion(publicacion);
        like.setUsuario(usuario);
        sessionFactory.getCurrentSession().save(like);
    }

    private List<PublicacionLike> whenObtenerMeGustaPorUsuario(Long idUser) {
        return repositorioPublicacionLike.getAllUserLikes(idUser);
    }

    private void thenMeGustaObtenidosPorUsuario(Integer esperado, List<PublicacionLike> likes) {
        assertEquals(esperado, likes.size());
    }

    @Test
    @Transactional
    @Rollback
    public void testObtenerTodosLosMeGustaPorPublicacion() {
        Integer esperado = 2;
        Usuario usuario1 = givenExisteUsuario(1L, "pacolo");
        Usuario usuario2 = givenExisteUsuario(2L, "aviantrix");
        Publicacion publicacion = givenExistePublicacion(1L, "hola mundo!");
        givenExisteMeGusta(1L, publicacion, usuario1);
        givenExisteMeGusta(2L, publicacion, usuario2);
        List<PublicacionLike> likes = whenObtenerMeGustaPorPublicacion(publicacion.getId());
        thenMeGustaObtenidosPorPublicacion(esperado, likes);

    }

    private List<PublicacionLike> whenObtenerMeGustaPorPublicacion(Long idPublication) {
        return repositorioPublicacionLike.getAllPublicationLikes(idPublication);
    }

    private void thenMeGustaObtenidosPorPublicacion(Integer esperado, List<PublicacionLike> likes) {
        assertEquals(esperado, likes.size());
    }*/

}
