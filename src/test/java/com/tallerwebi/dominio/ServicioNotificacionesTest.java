package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;
import com.tallerwebi.infraestructura.ServicioNotificacionImpl;

public class ServicioNotificacionesTest {

    
    @Mock
    private RepositorioNotificacion repositorioNotificacion;
    @Mock
    private RepositorioNotificacionUsuario repositorioNotificacionUsuario;
    @Mock
    private RepositorioUsuario repositorioUsuario;
    @InjectMocks
    private ServicioNotificacionImpl servicioNotificacion;

    @BeforeEach
    public void SetUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCrearNotificacion() {
        
        Notificacion esperaro = givenExisteNotificacion("Saludo", "Hola probando Test");
        
        Notificacion resultado = whenCreoNotificacion("Saludo", "Hola probando Test");
        
        thenNotificacionCreadaExitosa(esperaro, resultado);
    
    }

    private Notificacion givenExisteNotificacion(String titulo, String contenido) {
        Notificacion notificacion = new Notificacion();
        notificacion.setTitulo(titulo);
        notificacion.setContenido(contenido);
        return notificacion;
    }

    private Notificacion whenCreoNotificacion(String titulo, String contenido) {
        return servicioNotificacion.crearNotificacion(titulo, contenido);
    }

    private void thenNotificacionCreadaExitosa(Notificacion esperaro, Notificacion resultado) {
        assertNotNull(resultado);
        assertEquals(esperaro.getTitulo(), resultado.getTitulo());
        assertEquals(esperaro.getContenido(), resultado.getContenido());
        verify(repositorioNotificacion, times(1)).guardar(any(Notificacion.class));
    }

    @Test
    public void testEnviarNotificacionUsuarioExistente() throws UsuarioNoExistente {

        Usuario usuario = givenExisteUsuario("admin@gmail.com");
        
        whenEnvioNotificacion(new Notificacion(), LocalDateTime.now(), usuario.getEmail());
        
        thenNotificacionEnviadaExitosa();
    
    }

    private Usuario givenExisteUsuario(String email) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        ConfiguracionUsuario config = new ConfiguracionUsuario();
        config.setRecibirNotificaciones(true);
        usuario.setConfiguracionUsuario(config);
        when(repositorioUsuario.buscar(email)).thenReturn(usuario);
        return usuario;
    }

    private void whenEnvioNotificacion(Notificacion notificacion, LocalDateTime fechaHora, String email) throws UsuarioNoExistente {
        servicioNotificacion.enviarNotificacion(notificacion, fechaHora, email);
    } 

    private void thenNotificacionEnviadaExitosa() {
        verify(repositorioNotificacionUsuario, times(1)).guardar(any(NotificacionUsuario.class));
    }

    @Test
    public void testEnviarNotificacionUsuarioNoExistente() {
        
        givenNoExisteUsuario("unknown@gmail.com");

        assertThrows(UsuarioNoExistente.class, () -> {
            servicioNotificacion.enviarNotificacion(new Notificacion(), LocalDateTime.now(), "unknown@gmail.com");
        });
    
    }

    private void givenNoExisteUsuario(String email) {
        when(repositorioUsuario.buscar(email)).thenReturn(null);
    }

    @Test
    public void enviarNotificaciones() {

        thenExisteListaUsuarios();
        
        whenEnvioNotificaciones(new Notificacion(), LocalDateTime.now());

        thenNotificacionesEnviadasExitosa();
        
    }

    private void thenExisteListaUsuarios() {
        List<Usuario> usuarios = new ArrayList<>();
        Usuario usuario1 = new Usuario();
        ConfiguracionUsuario config1 = new ConfiguracionUsuario();
        config1.setRecibirNotificaciones(true);
        usuario1.setConfiguracionUsuario(config1);
        usuarios.add(usuario1);

        Usuario usuario2 = new Usuario();
        ConfiguracionUsuario config2 = new ConfiguracionUsuario();
        config2.setRecibirNotificaciones(false);
        usuario2.setConfiguracionUsuario(config2);
        usuarios.add(usuario2);

        when(repositorioUsuario.buscarTodos()).thenReturn(usuarios);
    }

    private void whenEnvioNotificaciones(Notificacion notificacion, LocalDateTime fechaHora) {
        servicioNotificacion.enviarNotificaciones(notificacion, fechaHora);
    }

    private void thenNotificacionesEnviadasExitosa() {
        verify(repositorioNotificacionUsuario, times(1)).guardar(any(NotificacionUsuario.class));
    }

    @Test
    public void testEliminarNotificacion() {

        NotificacionUsuario notificacionUsuario = givenExisteNotificacionUsuario(1L, 1L);

        whenEliminoNotificacion(1L, 1L);

        thenNotificacionEliminadaConExito(notificacionUsuario);
        
    }

    private NotificacionUsuario givenExisteNotificacionUsuario(long idNotificacion, Long idUsuario) {
        NotificacionUsuario notificacionUsuario = new NotificacionUsuario();
        when(repositorioNotificacionUsuario.buscar(idNotificacion, idUsuario)).thenReturn(notificacionUsuario);
        return notificacionUsuario;
    }

    private void whenEliminoNotificacion(Long idNotificacion, Long idUsuario) {
        servicioNotificacion.eliminarNotificacion(idNotificacion, idUsuario);
    }

    private void thenNotificacionEliminadaConExito(NotificacionUsuario notificacionUsuario) {
        verify(repositorioNotificacionUsuario, times(1)).borrar(notificacionUsuario);
    }

    
    @Test
    public void testEliminarNotificaciones() {

        LocalDateTime fechaHora = LocalDateTime.now();
        NotificacionUsuario notificacionUsuario1 = new NotificacionUsuario();
        NotificacionUsuario notificacionUsuario2 = new NotificacionUsuario();
        
        givenExistenNotificacionesUsuarios(fechaHora, notificacionUsuario1, notificacionUsuario2);

        whenEliminarNotificaciones(fechaHora);

        thenEliminarNotificacionesExitosa(notificacionUsuario1, notificacionUsuario2);

    }

    private void givenExistenNotificacionesUsuarios(LocalDateTime fechaHora, NotificacionUsuario notificacionUsuario1, NotificacionUsuario notificacionUsuario2) {
        List<NotificacionUsuario> notificacionesUsuarios = new ArrayList<>();

        notificacionUsuario1.setFechaHora(fechaHora.minusDays(1));
        notificacionesUsuarios.add(notificacionUsuario1);

        notificacionUsuario2.setFechaHora(fechaHora.plusDays(1));
        notificacionesUsuarios.add(notificacionUsuario2);

        when(repositorioNotificacionUsuario.buscarTodos()).thenReturn(notificacionesUsuarios);
    }

    private void whenEliminarNotificaciones(LocalDateTime fechaHora) {
        servicioNotificacion.eliminarNotificaciones(fechaHora);
    }

    private void thenEliminarNotificacionesExitosa(NotificacionUsuario notificacionUsuario1, NotificacionUsuario notificacionUsuario2) {
        verify(repositorioNotificacionUsuario, times(1)).borrar(notificacionUsuario1);
        verify(repositorioNotificacionUsuario, never()).borrar(notificacionUsuario2);
    }

    @Test
    public void testObtenerNotificacionesPorUsuario() {

        List<Notificacion> esperaro = givenExistenNotificaciones();
        
        List<Notificacion> resultado = whenObtengoNotificaciones(esperaro);

        thenNotificacionesObtenidasConExito(esperaro, resultado);
        
    }

    private List<Notificacion> givenExistenNotificaciones() {
        
        List<Notificacion> notificaciones = new ArrayList<>();
        
        return notificaciones;        
    }

    private List<Notificacion> whenObtengoNotificaciones(List<Notificacion> notificaciones) {
        Usuario usuario = new Usuario();
        when(repositorioNotificacionUsuario.buscarPorUsuario(usuario)).thenReturn(notificaciones);
        return servicioNotificacion.obtenerNotificacionesPorUsuario(usuario);
    }

    private void thenNotificacionesObtenidasConExito(List<Notificacion> esperaro, List<Notificacion> resultado) {
        assertEquals(esperaro, resultado);
    }

}
