package com.tallerwebi.presentacion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.ServicioNotificacion;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.AlturaIncorrectaException;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.dominio.excepcion.EdadInvalidaException;
import com.tallerwebi.dominio.excepcion.PesoIncorrectoException;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;

public class ControladorRegistroTest {

    @Mock
    private ServicioLogin servicioLogin;

    @Mock
    private ServicioNotificacion servicioNotificacion;

    @InjectMocks
    private ControladorRegistro controladorRegistro;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        when(request.getSession()).thenReturn(session);
    }

    @Test
    public void testInicio() {
        givenNoExisteVista();
        ModelAndView vista = whenCreoVistaInicio();
        thenVistaExitoso("redirect:/inicio", vista);
    }

    private void givenNoExisteVista() {}

    private ModelAndView whenCreoVistaInicio() {
        return controladorRegistro.inicio();
    }

    private void thenVistaExitoso(String esperado, ModelAndView vista) {
        assertEquals(esperado, vista.getViewName());
    }

    @Test
    public void testIrAInicio() {
        givenNoExisteVista();
        ModelAndView vista = whenCreoVistaIrAInicio();
        thenVistaExitoso("inicio", vista);
    }

    private ModelAndView whenCreoVistaIrAInicio() {
        return controladorRegistro.irAInicio();
    }

    @Test
    public void testIrAFormulario() {
        givenNoExisteVista();
        ModelAndView vista = whenCreoVistaIrAFormulario();
        thenVistaYModeloExitoso("formulario-registro", vista);
    }

    private ModelAndView whenCreoVistaIrAFormulario() {
        return controladorRegistro.irAFormulario();
    }

    private void thenVistaYModeloExitoso(String esperado, ModelAndView vista) {
        assertEquals(esperado, vista.getViewName());
        assertTrue(vista.getModelMap().containsAttribute("usuario"));
    }
 
    @Test
    public void testEnviarFormularioExitoso() throws Exception {
        Usuario usuario = givenExisteUsuarioNoRegistrado(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenCreoVistaEnviarFormulario(usuario, request);
        thenVistaRegistroExitoso(usuario, "redirect:/iniciar-sesion", vista);
    }

    private Usuario givenExisteUsuarioNoRegistrado(Long id, String email, String password) throws UsuarioExistente, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, UsuarioNoExistente {
        Usuario usuario = new Usuario();
        usuario.setId(id); 
        usuario.setEmail(email);
        usuario.setPassword(password);
        doNothing().when(servicioLogin).registrarUsuario(usuario);
        return usuario;
    }

    private ModelAndView whenCreoVistaEnviarFormulario(Usuario usuario, HttpServletRequest request) {
        return controladorRegistro.enviarFormulario(usuario, request);
    } 

    private void thenVistaRegistroExitoso(Usuario usuario, String esperado, ModelAndView vista) throws UsuarioExistente, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, UsuarioNoExistente {
        verify(servicioLogin).registrarUsuario(usuario);        
        assertEquals(esperado, vista.getViewName());
    }    

    
    @Test
    public void testEnviarFormularioUsuarioExistente() throws UsuarioExistente, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException{
        Usuario usuario = givenExisteUsuarioRegistrado(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenCreoVistaEnviarFormulario(usuario, request); 
        thenVistaRegistroFaliida("formulario-registro", vista, "El usuario ya existe.");
    }

    private Usuario givenExisteUsuarioRegistrado(Long id, String email, String password) throws UsuarioExistente, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setPassword(password);
        doThrow(new UsuarioExistente()).when(servicioLogin).registrarUsuario(usuario);
        return usuario;
    }

    private void thenVistaRegistroFaliida(String esperado, ModelAndView vista, String error) {
        assertEquals(esperado, vista.getViewName());
        assertTrue(vista.getModelMap().containsAttribute("error"));
        assertEquals(error, vista.getModelMap().get("error"));        
    }

    @Test
    public void testValidarLoginExitoso() throws Exception {
        Usuario usuario = givenExisteUsuario(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenCreoVistaValidarLogin(usuario, request); 
        thenLoginExitoso("redirect:/home", vista, usuario);

    }

    private Usuario givenExisteUsuario(Long id, String email, String password) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setPassword(password);
        when(servicioLogin.verificarUsuario(usuario.getEmail(), usuario.getPassword())).thenReturn(usuario);
        return usuario;
    }

    private ModelAndView whenCreoVistaValidarLogin(Usuario usuario, HttpServletRequest request) {
        return controladorRegistro.validarLogin(usuario, request);
    } 

    private void thenLoginExitoso(String esperado, ModelAndView vista, Usuario usuario) {
        assertEquals(esperado, vista.getViewName());
        verify(session).setAttribute("usuario", usuario);
    }

    @Test
    public void testValidarLoginFallido() throws Exception {
        Usuario usuario = givenNoExisteUsuario(1L, "admin@gmail.com", "1234abcd");
        ModelAndView vista = whenCreoVistaValidarLogin(usuario, request);
        thenLoginFallido("iniciar-sesion", vista, "Usuario o clave incorrecta");
    }

    private Usuario givenNoExisteUsuario(Long id, String email, String password) {
        Usuario usuario = new Usuario();
        usuario.setId(id);
        usuario.setEmail(email);
        usuario.setPassword(password);
        when(servicioLogin.verificarUsuario(usuario.getEmail(), usuario.getPassword())).thenReturn(null);
        return usuario;
    }

    private void thenLoginFallido(String esperado, ModelAndView vista, String error) {
        assertEquals(esperado, vista.getViewName());
        assertTrue(vista.getModelMap().containsAttribute("error"));
        assertEquals(error, vista.getModelMap().get("error"));
    }
    
}
