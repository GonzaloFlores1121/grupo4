package com.tallerwebi.presentacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.excepcion.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioNotificacion;
import com.tallerwebi.dominio.Usuario;

public class ControladorRegistroTest {

    private ServicioLogin servicioLogin = mock(ServicioLogin.class);
    private ServicioNotificacion servicioNotificacion = mock(ServicioNotificacion.class);
    private ServicioDatosUsuario servicioDatosUsuario = mock(ServicioDatosUsuario.class);
    private ControladorRegistro controladorRegistro = new ControladorRegistro(servicioLogin, servicioNotificacion,servicioDatosUsuario);
    private HttpServletRequest request = mock(HttpServletRequest.class);    
    private HttpSession session = mock(HttpSession.class);

    @BeforeEach
    public void setUp() {
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testInicio() {
        givenNoExisteVista();
        ModelAndView vista = whenCreoVistaInicio();
        thenVistaInicioExitoso("redirect:/inicio", vista);
    }

    private void givenNoExisteVista() {}

    private ModelAndView whenCreoVistaInicio() {
        return controladorRegistro.inicio();
    }

    private void thenVistaInicioExitoso(String esperado, ModelAndView vista) {
        assertEquals(esperado, vista.getViewName());
    }

    @Test
    public void testIrAInicio() {
        givenNoExisteVista();
        ModelAndView vista = whenCreoVistaIrAInicio();
        thenVistaInicioExitoso("inicio", vista);
    }

    private ModelAndView whenCreoVistaIrAInicio() {
        return controladorRegistro.irAInicio();
    }

    @Test
    public void testIrAFormulario() {
        givenNoExisteVista();
        ModelAndView vista = whenCreoVistaIrAFormulario();
        thenVistaFormularioExitoso(vista);
    }

    private ModelAndView whenCreoVistaIrAFormulario() {
        return controladorRegistro.irAFormulario();
    }

    private void thenVistaFormularioExitoso(ModelAndView vista) {
        assertEquals("formulario-registro", vista.getViewName());
        assertTrue(vista.getModel().containsKey("usuario"));
    }
 
    @Test
    public void testEnviarFormularioExitoso() throws UsuarioExistente, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, UsuarioNoExistente, PesoMetaIncorrectoException {
        Usuario usuario = givenExisteUsuarioNoRegistrado(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenCreoVistaEnviarFormulario(usuario);
        thenRegistroExitoso(usuario, vista);
    }

    private Usuario givenExisteUsuarioNoRegistrado(Long id, String email, String password) throws UsuarioExistente, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, UsuarioNoExistente, PesoMetaIncorrectoException {
        Usuario usuario = new Usuario();
        usuario.setId(id); 
        usuario.setEmail(email);
        usuario.setPassword(password);
        doNothing().when(servicioLogin).registrarUsuario(usuario);
        return usuario;
    }

    private ModelAndView whenCreoVistaEnviarFormulario(Usuario usuario) {
        return controladorRegistro.enviarFormulario(usuario, request);
    } 

    private void thenRegistroExitoso(Usuario usuario, ModelAndView vista) throws UsuarioExistente, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, UsuarioNoExistente, PesoMetaIncorrectoException {
        verify(servicioLogin).registrarUsuario(usuario);        
        assertEquals("redirect:/iniciar-sesion", vista.getViewName());
    }    
    
    @Test
    public void testEnviarFormularioUsuarioFallido() throws UsuarioExistente, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, PesoMetaIncorrectoException {
        Usuario usuario = givenExisteUsuarioRegistrado(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenCreoVistaEnviarFormulario(usuario); 
        thenVistaRegistroFaliida(vista);
    }

    private Usuario givenExisteUsuarioRegistrado(Long id, String email, String password) throws UsuarioExistente, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, PesoMetaIncorrectoException {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setPassword(password);
        doThrow(new UsuarioExistente()).when(servicioLogin).registrarUsuario(usuario);
        return usuario;
    }

    private void thenVistaRegistroFaliida(ModelAndView vista) {
        assertEquals("formulario-registro", vista.getViewName());
        assertTrue(vista.getModel().containsKey("error"));
        assertEquals("El usuario ya existe.", vista.getModel().get("error"));        
    }

    @Test
    public void testValidarLoginExitoso() {
        Usuario usuario = givenExisteUsuario(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenCreoVistaValidarLogin(usuario); 
        thenLoginExitoso(vista, usuario);
    }

    private Usuario givenExisteUsuario(Long id, String email, String password) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setPassword(password);
        when(servicioLogin.verificarUsuario(usuario.getEmail(), usuario.getPassword())).thenReturn(usuario);
        return usuario;
    }

    private ModelAndView whenCreoVistaValidarLogin(Usuario usuario) {
        return controladorRegistro.validarLogin(usuario, request);
    } 

    private void thenLoginExitoso(ModelAndView vista, Usuario usuario) {
        assertEquals("redirect:/home", vista.getViewName());
        verify(session).setAttribute("usuario", usuario);
    }

    @Test
    public void testValidarLoginFallido() {
        Usuario usuario = givenNoExisteUsuario(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenCreoVistaValidarLogin(usuario);
        thenLoginFallido(vista);
    }

    private Usuario givenNoExisteUsuario(Long id, String email, String password) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setPassword(password);
        when(servicioLogin.verificarUsuario(usuario.getEmail(), usuario.getPassword())).thenReturn(null);
        return usuario;
    }

    private void thenLoginFallido(ModelAndView vista) {
        assertEquals("iniciar-sesion", vista.getViewName());
        assertTrue(vista.getModel().containsKey("error"));
        assertEquals("Usuario o clave incorrecta", vista.getModel().get("error"));
    }
    
}
