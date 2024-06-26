package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.excepcion.PublicacionNoExistente;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;
import com.tallerwebi.infraestructura.ServicioLikeImpl;

public class ServicioLikeTest {

    private RepositorioComunidad repositorioComunidad = mock(RepositorioComunidad.class);
    private RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
    private RepositorioPublicacionLike repositorioPublicacionLike = mock(RepositorioPublicacionLike.class);
    private ServicioLikeImpl servicioLike = new ServicioLikeImpl(repositorioComunidad, repositorioUsuario, repositorioPublicacionLike);

    /*@Test
    public void testLike() {
        Usuario usuario = givenExisteUsuario(1L, "pacolo");
        Publicacion publicacion = givenExistePublicacion(1L, "hola mundo!");
        whenDarLike(publicacion, usuario);
        thenLike();
    }

    private Usuario givenExisteUsuario(Long id, String nombre) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre(nombre);
        when(repositorioUsuario.buscarPorId(id)).thenReturn(usuario);
        return usuario;
    }

    private Publicacion givenExistePublicacion(Long id, String contenido) {
        Publicacion publicacion = new Publicacion();
        publicacion.setId(id);
        publicacion.setTexto(contenido);
        when(repositorioComunidad.consultarPublicacion(id)).thenReturn(publicacion);
        return publicacion;
    }

    private void whenDarLike(Publicacion publicacion, Usuario usuario) {
        servicioLike.like(publicacion, usuario);
    }

    private void thenLike() {
        verify(repositorioPublicacionLike, times(1)).save(any(PublicacionLike.class));
    }

    @Test
    public void testObtenerTodosLosLikePorUsuarioExitoso() throws UsuarioNoExistente {
        Usuario usuario = givenExisteUsuario(1L, "pacolo");
        Publicacion publicacion1 = givenExistePublicacion(1L, "buenos dias");
        Publicacion publicacion2 = givenExistePublicacion(2L, "buenas noches");
        List<PublicacionLike> esperado = givenExisteListaLikesPorUsuario(usuario, publicacion1, publicacion2);
        List<PublicacionLike> resultado = whenObtenerLikesPorUsuario(usuario.getId());
        thenLikesObtenidosPorUsuario(usuario, esperado, resultado);
    }

    private List<PublicacionLike> givenExisteListaLikesPorUsuario(Usuario usuario, Publicacion publicacion1, Publicacion publicacion2) {
        List<PublicacionLike> likes = new ArrayList<>();
        PublicacionLike like1 = new PublicacionLike();
        like1.setUsuario(usuario);
        like1.setPublicacion(publicacion1);
        likes.add(like1);
        PublicacionLike like2 = new PublicacionLike();
        like2.setUsuario(usuario);
        like2.setPublicacion(publicacion2);
        likes.add(like2);
        when(repositorioPublicacionLike.getAllUserLikes(usuario.getId())).thenReturn(likes);
        return likes;
    }
    
    private List<PublicacionLike> whenObtenerLikesPorUsuario(Long idUser) throws UsuarioNoExistente {
        return servicioLike.obtenerTodosLosLikePorUsuario(idUser);
    }

    private void thenLikesObtenidosPorUsuario(Usuario usuario, List<PublicacionLike> esperado, List<PublicacionLike> resultado) {
        verify(repositorioUsuario, times(1)).buscarPorId(usuario.getId());
        verify(repositorioPublicacionLike, times(1)).getAllUserLikes(usuario.getId());
        assertEquals(esperado.size(), resultado.size());        
    }

    @Test
    public void testObtenerTodosLosLikePorUsuarioFallido() {
        Usuario usuario = givenNoExisteUsuario(1L, "pacolo");
        assertThrows(UsuarioNoExistente.class, () -> {
            servicioLike.obtenerTodosLosLikePorUsuario(usuario.getId());
        });
        thenLikesNoObtenidosPorUsuario(usuario);
    }

    private Usuario givenNoExisteUsuario(Long id, String nombre) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre(nombre);
        when(repositorioUsuario.buscarPorId(id)).thenReturn(null);
        return usuario;
    }

    private void thenLikesNoObtenidosPorUsuario(Usuario usuario) {
        verify(repositorioPublicacionLike, never()).getAllUserLikes(usuario.getId());        
    }

    @Test
    public void testObtenerTodosLosLikesPorPublicacionExitoso() throws PublicacionNoExistente {
        Publicacion publicacion = givenExistePublicacion(1L, "hola mundo!");
        Usuario usuario1 = givenExisteUsuario(1L, "pacolo");
        Usuario usuario2 = givenExisteUsuario(2L, "pwnies");
        List<PublicacionLike> esperado = givenExisteListaLikesPorPublicacion(publicacion, usuario1, usuario2);
        List<PublicacionLike> resultado = whenObtenerLikesPorPublicacion(publicacion.getId());
        thenLikesObtenidosPorPublicacion(publicacion, esperado, resultado);
    }

    private List<PublicacionLike> givenExisteListaLikesPorPublicacion(Publicacion publicacion, Usuario usuario1, Usuario usuario2) {
        List<PublicacionLike> likes = new ArrayList<>();
        PublicacionLike like1 = new PublicacionLike();
        like1.setPublicacion(publicacion);
        like1.setUsuario(usuario1);
        likes.add(like1);
        PublicacionLike like2 = new PublicacionLike();
        like2.setPublicacion(publicacion);
        like2.setUsuario(usuario2);
        likes.add(like2);
        when(repositorioPublicacionLike.getAllPublicationLikes(publicacion.getId())).thenReturn(likes);
        return likes;
    }

    private List<PublicacionLike> whenObtenerLikesPorPublicacion(Long idPublication) throws PublicacionNoExistente {
        return servicioLike.obtenerTodosLosLikesPorPublicacion(idPublication);
    }

    private void thenLikesObtenidosPorPublicacion(Publicacion publicacion, List<PublicacionLike> esperado, List<PublicacionLike> resultado) {
        verify(repositorioComunidad, times(1)).consultarPublicacion(publicacion.getId());
        verify(repositorioPublicacionLike, times(1)).getAllPublicationLikes(publicacion.getId());
        assertEquals(esperado.size(), resultado.size());        
    }

    @Test
    public void testObtenerTodosLosLikesPorPublicacionFallido() {
        Publicacion publicacion = givenNoExistePublicacion(1L, "hola mundo!");
        assertThrows(PublicacionNoExistente.class, () -> {
            servicioLike.obtenerTodosLosLikesPorPublicacion(publicacion.getId());
        });
        thenLikesNoObtenidosPorPublicacion(publicacion);
    }

    private Publicacion givenNoExistePublicacion(Long id, String contenido) {
        Publicacion publicacion = new Publicacion();
        publicacion.setId(id);
        publicacion.setTexto(contenido);
        when(repositorioComunidad.consultarPublicacion(id)).thenReturn(null);
        return publicacion;
    } 

    private void thenLikesNoObtenidosPorPublicacion(Publicacion publicacion) {

        verify(repositorioPublicacionLike, never()).getAllPublicationLikes(publicacion.getId());
    }

    @Test
    public void testUnlike() {
        Usuario usuario = givenExisteUsuario(1L, "pacolo");
        Publicacion publicacion = givenExistePublicacion(1L, "hola mundo!");
        PublicacionLike like = givenExistePublicacionLike(1L, publicacion, usuario);
        whenQuitarLike(like);
        thenLikeQuitado(like);
    }

    private PublicacionLike givenExistePublicacionLike(Long id, Publicacion publicacion, Usuario usuario) {
        PublicacionLike like = new PublicacionLike();
        like.setId(id);
        like.setPublicacion(publicacion);
        like.setUsuario(usuario);
        return like;
    }

    private void whenQuitarLike(PublicacionLike like) {
        servicioLike.unlike(like);
    }

    private void thenLikeQuitado(PublicacionLike like) {
        verify(repositorioPublicacionLike, times(1)).delete(like);
    }*/

}
