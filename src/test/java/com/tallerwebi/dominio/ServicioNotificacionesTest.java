
package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;
import com.tallerwebi.infraestructura.ServicioNotificacionImpl;


public class ServicioNotificacionesTest {
    
    private RepositorioNotificacion repositorioNotificacion = mock(RepositorioNotificacion.class);
    private RepositorioNotificacionUsuario repositorioNotificacionUsuario = mock(RepositorioNotificacionUsuario.class);
    private RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);

     ServicioNotificacionImpl servicioNotificacion = new ServicioNotificacionImpl(repositorioNotificacion, repositorioNotificacionUsuario, repositorioUsuario);

    @Test
    public void testCrearNotificacion() {
        givenNoExisteNotificacion();
        Notificacion notificacion = whenCreoNotificacion("Saludo", "Hola probando Test");
        thenNotificacionCreadaExitosa(notificacion); 
    }

    private void givenNoExisteNotificacion() {}

    private Notificacion whenCreoNotificacion(String titulo, String contenido) {
        return servicioNotificacion.crearNotificacion(titulo, contenido);
    }

    private void thenNotificacionCreadaExitosa(Notificacion notificacion) {
        assertNotNull(notificacion);
        verify(repositorioNotificacion, times(1)).save(notificacion);
    }

    @Test
    public void testCrearNotificacionUsuario() {
        Usuario usuario = givenExisteUsuario();
        NotificacionUsuario notificacionUsuario = whenCreoNotificacionUsuario(usuario);
        thenNotificacionUsuarioCreadaExitosa(notificacionUsuario);
    }

    private Usuario givenExisteUsuario() {
        Usuario usuario= new Usuario();
        usuario.setId(1L);
        when(repositorioUsuario.buscarPorId(1L)).thenReturn(usuario);
        return usuario;
    }

    private NotificacionUsuario whenCreoNotificacionUsuario(Usuario usuario) {
        Notificacion notificacion = whenCreoNotificacion("Saludo", "Hola Mundo");
        return servicioNotificacion.crearNotificacionUsuario(notificacion, usuario, LocalDateTime.now());
    }

    private void thenNotificacionUsuarioCreadaExitosa(NotificacionUsuario notificacionUsuario) {
        assertNotNull(notificacionUsuario);
        verify(repositorioNotificacionUsuario, times(1)).save(notificacionUsuario);
    }

    @Test
    public void testEnviarNotificacionUsuarioExistente() throws UsuarioNoExistente {
        Usuario usuario = givenExisteUsuario();
        whenEnvioNotificacion(LocalDateTime.now(), usuario.getId());
        thenNotificacionEnviadaExitosa();
    }

    private void whenEnvioNotificacion(LocalDateTime fechaHora, Long idUsuario) throws UsuarioNoExistente {
        servicioNotificacion.enviarNotificacion("Saludo", "Hola Mundo", fechaHora, idUsuario);
    } 

    private void thenNotificacionEnviadaExitosa() {
        verify(repositorioNotificacionUsuario, times(1)).save(any(NotificacionUsuario.class));
    }

    @Test
    public void testEnviarNotificacionUsuarioNoExistente() {
        Long idUsuario = 2L;
        givenNoExisteUsuario(idUsuario);
        assertThrows(UsuarioNoExistente.class, () -> {
            servicioNotificacion.enviarNotificacion("Saludo", "Contenido no disponible.", LocalDateTime.now(), idUsuario);
        });
    }

    private void givenNoExisteUsuario(Long idUsuario) {
        when(repositorioUsuario.buscarPorId(idUsuario)).thenReturn(null);
    }
 
    @Test
    public void enviarNotificaciones() {
        givenExisteListaUsuarios();
        whenEnvioNotificaciones();
        thenNotificacionesEnviadasExitosa();
    }

    private void givenExisteListaUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario1 = new Usuario();
        usuarios.add(usuario1);
        Usuario usuario2 = new Usuario();
        usuarios.add(usuario2);
        when(repositorioUsuario.obtenerTodos()).thenReturn(usuarios);
    }

    private void whenEnvioNotificaciones() {
        servicioNotificacion.enviarNotificaciones("Saludo para Todos", "Hola todo el Mundo", LocalDateTime.now());
    }

    private void thenNotificacionesEnviadasExitosa() {
        verify(repositorioNotificacionUsuario, times(2)).save(any(NotificacionUsuario.class));
    }

    @Test
    public void testEliminarNotificacion() {
        NotificacionUsuario notificacionUsuario = givenExisteNotificacionUsuario(1L, 1L);
        whenEliminoNotificacion(1L, 1L);
        thenNotificacionEliminadaConExito(notificacionUsuario);
    }

    private NotificacionUsuario givenExisteNotificacionUsuario(long idNotificacion, Long idUsuario) {
        NotificacionUsuario notificacionUsuario = new NotificacionUsuario();
        when(repositorioNotificacionUsuario.get(idNotificacion, idUsuario)).thenReturn(notificacionUsuario);
        return notificacionUsuario;
    }

    private void whenEliminoNotificacion(Long idNotificacion, Long idUsuario) {
        servicioNotificacion.eliminarNotificacion(idNotificacion, idUsuario);
    }

    private void thenNotificacionEliminadaConExito(NotificacionUsuario notificacionUsuario) {
        verify(repositorioNotificacionUsuario, times(1)).delete(notificacionUsuario);
    }

    @Test
    public void testEliminarNotificaciones() {
        NotificacionUsuario notificacionUsuario1 = new NotificacionUsuario();
        NotificacionUsuario notificacionUsuario2 = new NotificacionUsuario();
        givenExistenNotificacionesUsuarios(notificacionUsuario1, notificacionUsuario2);
        whenEliminarNotificaciones();
        thenEliminarNotificacionesExitosa(notificacionUsuario1, notificacionUsuario2);
    }

    private void givenExistenNotificacionesUsuarios(NotificacionUsuario notificacionUsuario1, NotificacionUsuario notificacionUsuario2) {
        List<NotificacionUsuario> notificacionesUsuarios = new ArrayList<>();
        notificacionUsuario1.setFechaHora(LocalDateTime.now().minusDays(1));
        notificacionesUsuarios.add(notificacionUsuario1);
        notificacionUsuario2.setFechaHora(LocalDateTime.now().plusDays(1));
        notificacionesUsuarios.add(notificacionUsuario2);
        when(repositorioNotificacionUsuario.getAll()).thenReturn(notificacionesUsuarios);
    }

    private void whenEliminarNotificaciones() {
        servicioNotificacion.eliminarNotificaciones(LocalDateTime.now());
    }

    private void thenEliminarNotificacionesExitosa(NotificacionUsuario notificacionUsuario1, NotificacionUsuario notificacionUsuario2) {
        verify(repositorioNotificacionUsuario, times(1)).delete(notificacionUsuario1);
        verify(repositorioNotificacionUsuario, never()).delete(notificacionUsuario2);
    }
 
    @Test
    public void testObtenerNotificacionesPorUsuario() {
        List<Notificacion> esperaro = givenExistenNotificaciones();
        List<Notificacion> resultado = whenObtengoNotificaciones(esperaro);
        thenNotificacionesObtenidasConExito(esperaro, resultado);
    }

    private List<Notificacion> givenExistenNotificaciones(){ 
        List<Notificacion> notificaciones = new ArrayList<>();
        notificaciones.add(new Notificacion());
        notificaciones.add(new Notificacion());
        return notificaciones;        
    }

    private List<Notificacion> whenObtengoNotificaciones(List<Notificacion> notificaciones) {
        Usuario usuario = new Usuario();
        when(repositorioNotificacionUsuario.getAllNotification(usuario.getId())).thenReturn(notificaciones);
        return servicioNotificacion.obtenerNotificaciones(usuario.getId());
    }

    private void thenNotificacionesObtenidasConExito(List<Notificacion> esperaro, List<Notificacion> resultado) {
        assertEquals(esperaro, resultado);
    }

}
