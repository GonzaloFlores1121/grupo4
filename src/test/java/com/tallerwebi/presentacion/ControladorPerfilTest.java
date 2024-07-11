package com.tallerwebi.presentacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tallerwebi.dominio.excepcion.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioNotificacion;
import com.tallerwebi.dominio.Usuario;

@Controller
public class ControladorPerfilTest {

    private ServicioLogin servicioLogin = mock(ServicioLogin.class);
    private ServicioNotificacion servicioNotificacion = mock(ServicioNotificacion.class);
    private ControladorPerfil controladorPerfil = new ControladorPerfil(servicioLogin, servicioNotificacion);
    private HttpServletRequest request = mock(HttpServletRequest.class);
    private HttpSession session = mock(HttpSession.class);

    @BeforeEach
    public void setUp() {
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testPerfilUsuarioExitoso() {
        givenExisteUsuarioLogueado(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenCreoVistaPerfilUsurio();
        thenVistaPerfilUsuarioExitoso(vista);
    }

    private Usuario givenExisteUsuarioLogueado(Long id, String email, String password) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setGenero("masculino");
        usuario.setImagen("icono-perfil-1.png");
        when(session.getAttribute("usuario")).thenReturn(usuario);              
        when(servicioLogin.buscarUsuario(usuario.getEmail())).thenReturn(usuario);
        when(servicioNotificacion.obtenerNotificaciones(usuario.getId())).thenReturn(List.of(new Notificacion()));
        return usuario;
    }

    private ModelAndView whenCreoVistaPerfilUsurio() {
        return controladorPerfil.perfilUsuario(request);
    }

    private void thenVistaPerfilUsuarioExitoso(ModelAndView vista) {
        assertEquals("perfilUsuario", vista.getViewName());
        assertNotNull(vista.getModel().get("usuario"));
        assertNotNull(vista.getModel().get("imagenes"));
    }

    @Test
    public void testPerfilUsuarioFallido() {
        givenExisteUsuarioNoLogueado(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenCreoVistaPerfilUsurio();
        thenVistaRedirigidaInicio(vista);
    }

    private Usuario givenExisteUsuarioNoLogueado(Long id, String email, String password) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setPassword(password);
        when(session.getAttribute("usuario")).thenReturn(null);
        return usuario;
    }

    private void thenVistaRedirigidaInicio(ModelAndView vista) {
        assertEquals("redirect:/inicio", vista.getViewName());
    }
    
    @Test
    public void testProcesarImagenExitoso() throws UsuarioExistente, DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException, UsuarioNoExistente {
        Usuario usuario = givenExisteUsuarioLogueado(1L, "admin@gmail.com", "1234abcd");
        Usuario usuarioForm = givenExisteUsuarioFormImagen("icono-perfil-3.png");
        ModelAndView vista = whenEnvioImagen(usuarioForm);
        thenImagenProcesada(usuario, usuarioForm, vista);
    }

    private Usuario givenExisteUsuarioFormImagen(String imagen) {
        Usuario usuarioForm = new Usuario();
        usuarioForm.setImagen(imagen);
        return usuarioForm;
    }

    private ModelAndView whenEnvioImagen(Usuario usuarioForm) throws UsuarioExistente, DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException, UsuarioNoExistente {
        return controladorPerfil.procesarImagen(usuarioForm, request);
    }

    private void thenImagenProcesada(Usuario usuario, Usuario usuarioForm, ModelAndView vista) {
        verify(servicioLogin, times(1)).modificarImagen(usuario, usuarioForm.getImagen());
        assertEquals("redirect:/perfilUsuario", vista.getViewName());
    }

    @Test
    public void testProcesarImagenFallido() throws UsuarioExistente, DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException, UsuarioNoExistente {
        givenExisteUsuarioNoLogueado(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenEnvioImagen(null);
        thenVistaRedirigidaInicio(vista);
    }

    @Test
    public void testFormularioEditarPerfilExitoso() {
        givenExisteUsuarioLogueado(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenCreoVistaFormularioEditarPerfil();
        thenVistaFormularioEditarPerfilExitoso(vista);
    }

    private ModelAndView whenCreoVistaFormularioEditarPerfil() {
        return controladorPerfil.formularioEditarPerfil(request);
    }

    private void thenVistaFormularioEditarPerfilExitoso(ModelAndView vista) {
        assertEquals("formularioEditarPerfil", vista.getViewName());
        assertNotNull(vista.getModel().get("usuario"));
    }

    @Test
    public void testFormularioEditarPerfilFallido() {
        givenExisteUsuarioNoLogueado(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenCreoVistaFormularioEditarPerfil();
        thenVistaRedirigidaInicio(vista);
    }

    @Test
    public void testProcesarDatosExitoso() throws UsuarioExistente, DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException, PesoMetaIncorrectoException {
        Usuario usuario = givenExisteUsuarioLogueado(1L, "admin@gmail.com", "1234abcd");
        Usuario usuarioForm = givenExisteUsuarioFormPerfil("pacolo@gmail.com");
        ModelAndView vista = whenEnvioDatosUsuario(usuarioForm);
        thenDatosUsuarioProcesados(usuario, usuarioForm, vista);
    }

    private Usuario givenExisteUsuarioFormPerfil(String email) {
        Usuario usuarioForm = new Usuario();
        usuarioForm.setEmail(email);
        return usuarioForm;
    }

    private ModelAndView whenEnvioDatosUsuario(Usuario usuarioForm) {
        return controladorPerfil.procesarDatos(usuarioForm, request);
    }

    private void thenDatosUsuarioProcesados(Usuario usuario, Usuario usuarioForm, ModelAndView vista) throws UsuarioExistente, DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException, PesoMetaIncorrectoException {
        verify(servicioLogin, times(1)).modificarUsuario(usuario, usuarioForm);
        assertEquals("redirect:/perfilUsuario", vista.getViewName());
    }

    @Test
    public void testProcesarDatosFallido() throws Exception {
        givenExisteUsuarioNoLogueado(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenEnvioDatosUsuario(null);
        thenVistaRedirigidaInicio(vista);
    }

    @Test
    public void testNotificacionesExitoso() {
        givenExisteUsuarioLogueado(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenCreoVistaNotificaciones();  
        thenVistaNotificacionesExitoso(vista);
    }

    private ModelAndView whenCreoVistaNotificaciones() {
        return controladorPerfil.notificaciones(request);       
    }

    private void thenVistaNotificacionesExitoso(ModelAndView vista) {
        assertEquals("notificaciones", vista.getViewName());
        assertNotNull(vista.getModel().get("usuario"));
        assertNotNull(vista.getModel().get("notificaciones"));       
    }

    @Test
    public void testNotificacionesFallido() {
        givenExisteUsuarioNoLogueado(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenCreoVistaNotificaciones();
        thenVistaRedirigidaInicio(vista);
    }

    @Test
    public void testEliminarNotificacionExitoso() {
        Usuario usuario = givenExisteUsuarioLogueado(1L, "admin@gmail.com", "1234abcd");
        Notificacion notificacion = givenExisteNotifiacion(1L, "Saludo", "Hola!");
        ModelAndView vista = whenEliminoNotifiacion(notificacion);
        thenNotificacionElimanada(notificacion, usuario, vista);
    }

    private Notificacion givenExisteNotifiacion(Long id, String titulo, String contenido) {
        Notificacion notificacion = new Notificacion();
        notificacion.setId(id);
        notificacion.setTitulo(titulo);
        notificacion.setContenido(contenido);
        return notificacion;
    }

    private ModelAndView whenEliminoNotifiacion(Notificacion notifiacion) {
        return controladorPerfil.eliminarNotificacion(notifiacion.getId(), request);
    }

    private void thenNotificacionElimanada(Notificacion notificacion, Usuario usuario, ModelAndView vista) {
        verify(servicioNotificacion, times(1)).eliminarNotificacion(notificacion.getId(), usuario.getId());
        assertEquals("redirect:/notificaciones", vista.getViewName());
    }

    @Test
    public void testEliminarNotificacionFallido() {
        givenExisteUsuarioNoLogueado(1L, "admin@gmail.com", "1234abcd");
        Notificacion notificacion = givenExisteNotifiacion(1L, "Saludo", "Hola!");
        ModelAndView vista = whenEliminoNotifiacion(notificacion);
        thenVistaRedirigidaInicio(vista);
    }

}
