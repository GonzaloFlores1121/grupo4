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

import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;
import com.tallerwebi.infraestructura.ServicioFollowImpl;

public class ServicioFollowTest {

    private RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
    private RepositorioUsuarioFollow repositorioUsuarioFollow = mock(RepositorioUsuarioFollow.class);
    private ServicioFollowImpl servicioFollow = new ServicioFollowImpl(repositorioUsuario, repositorioUsuarioFollow);

    @Test
    public void testFollow() {
        Usuario usuario = givenExisteUsuario(1L, "pacolo");
        Usuario seguidor = givenExisteUsuario(2L, "pwnies");
        whenDarFollow(usuario, seguidor);
        thenFollow();
    }

    private Usuario givenExisteUsuario(Long id, String nombre) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre(nombre);
        when(repositorioUsuario.buscarPorId(id)).thenReturn(usuario);
        return usuario;
    }

    private void whenDarFollow(Usuario usuario, Usuario seguidor) {
        servicioFollow.follow(usuario, seguidor);
    }

    private void thenFollow() {
        verify(repositorioUsuarioFollow, times(1)).save(any(UsuarioFollow.class));
    }

    @Test
    public void testObtenerFollowExitoso() throws UsuarioNoExistente {
        Usuario usuario = givenExisteUsuario(1L, "pacolo");
        Usuario seguidor = givenExisteUsuario(2L, "pwnies");
        UsuarioFollow esperado = givenExisteUsuarioFollow(1L, usuario, seguidor);
        UsuarioFollow resultado = whenObtenerFollow(usuario.getId(), seguidor.getId());
        thenFollowObtenido(usuario, seguidor, esperado, resultado);
    }

    private UsuarioFollow givenExisteUsuarioFollow(Long id, Usuario usuario, Usuario seguidor) {
        UsuarioFollow follow = new UsuarioFollow();
        follow.setId(id);
        follow.setUsuario(usuario);
        follow.setSeguidor(seguidor);
        when(repositorioUsuarioFollow.get(usuario.getId(), seguidor.getId())).thenReturn(follow);
        return follow;
    }

    private UsuarioFollow whenObtenerFollow(Long idUser, Long idUserFollow) throws UsuarioNoExistente {
        return servicioFollow.obtenerFollow(idUser, idUserFollow);
    }

    private void thenFollowObtenido(Usuario usuario, Usuario seguidor, UsuarioFollow esperado, UsuarioFollow resultado) {
        verify(repositorioUsuario, times(1)).buscarPorId(usuario.getId());
        verify(repositorioUsuario, times(1)).buscarPorId(seguidor.getId());
        verify(repositorioUsuarioFollow, times(1)).get(usuario.getId(), seguidor.getId());
        assertEquals(esperado, resultado);
    }

    @Test
    public void testObtenerFollowFallido() {
        Usuario usuario = givenNoExisteUsuario(1L, "pacolo");
        Usuario seguidor = givenNoExisteUsuario(2L, "pwnies");
        assertThrows(UsuarioNoExistente.class, () -> {
            servicioFollow.obtenerFollow(usuario.getId(), seguidor.getId());
        });
        thenFollowNoObtenido(usuario, seguidor);
    }

    private Usuario givenNoExisteUsuario(Long id, String nombre) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setNombre(nombre);
        when(repositorioUsuario.buscarPorId(id)).thenReturn(null);
        return usuario;
    }

    private void thenFollowNoObtenido(Usuario usuario, Usuario seguidor) {
        verify(repositorioUsuarioFollow, never()).get(usuario.getId(), seguidor.getId());
    }

    /* 
    @Test
    public void testObtenerTodosLosFollowsExitoso() throws UsuarioNoExistente {
        Usuario usuario = givenExisteUsuario(1L, "pacolo");
        Usuario seguidor1 = givenExisteUsuario(2L, "pwnies");
        Usuario seguidor2 = givenExisteUsuario(3L, "aviantrix");
        List<UsuarioFollow> esperado = givenExisteListaFollows(usuario, seguidor1, seguidor2);
        List<UsuarioFollow> resultado = whenObtenerFollowsPorUsuario(usuario.getId());
        thenFollowsObtenidosPorUsuario(usuario, esperado, resultado);
    }

    private List<UsuarioFollow> givenExisteListaFollows(Usuario usuario, Usuario seguidor1, Usuario seguidor2) {
        List<UsuarioFollow> follows = new ArrayList<>();
        UsuarioFollow follow1 = givenExisteUsuarioFollow(1L, usuario, seguidor2);
        follows.add(follow1);
        UsuarioFollow follow2 = givenExisteUsuarioFollow(2L, usuario, seguidor2);
        follows.add(follow2);
        when(repositorioUsuarioFollow.getAllFollows(usuario.getId())).thenReturn(follows);
        return follows;
    }

    private List<UsuarioFollow> whenObtenerFollowsPorUsuario(Long idUser) throws UsuarioNoExistente {
        return servicioFollow.obtenerTodosLosFollows(idUser);
    }

    private void thenFollowsObtenidosPorUsuario(Usuario usuario, List<UsuarioFollow> esperado, List<UsuarioFollow> resultado) {
        verify(repositorioUsuario, times(1)).buscarPorId(usuario.getId());
        verify(repositorioUsuarioFollow, times(1)).getAllFollows(usuario.getId());
        assertEquals(esperado.size(), resultado.size());        
    }

    @Test
    public void testObtenerTodosLosFollowsUsuarioNoExistente() {
        Usuario usuario = givenNoExisteUsuario(1L, "pacolo");
        assertThrows(UsuarioNoExistente.class, () -> {
            servicioFollow.obtenerTodosLosFollows(usuario.getId());
        });
        thenFollowsNoObtenidosPorUsuario(usuario);
    }

    private void thenFollowsNoObtenidosPorUsuario(Usuario usuario) {
        verify(repositorioUsuarioFollow, never()).getAllFollows(usuario.getId());
    }*/

    @Test
    public void testUnfollow() {
        Usuario usuario = givenExisteUsuario(1L, "pacolo");
        Usuario seguidor = givenExisteUsuario(2L, "pwnies");
        UsuarioFollow follow = givenExisteUsuarioFollow(1L, usuario, seguidor);
        whenQuitarFollow(follow);
        thenFollowQuitado(follow);
    }

    private void whenQuitarFollow(UsuarioFollow follow) {
        servicioFollow.unfollow(follow);
    }

    private void thenFollowQuitado(UsuarioFollow follow) {
        verify(repositorioUsuarioFollow, times(1)).delete(follow);
    }

}
