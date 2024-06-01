package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;

import com.tallerwebi.dominio.excepcion.AlturaIncorrectaException;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.dominio.excepcion.EdadInvalidaException;
import com.tallerwebi.dominio.excepcion.PesoIncorrectoException;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;
import com.tallerwebi.infraestructura.ServicioLoginImpl;

public class ServicioLoginTest {

    private RepositorioConfiguracionUsuario repositorioConfiguracionUsuario = mock(RepositorioConfiguracionUsuario.class);
    private RepositorioUsuario repositorioUsuario = mock(RepositorioUsuario.class);
    private ServicioLoginImpl servicioLogin = new ServicioLoginImpl(repositorioUsuario, repositorioConfiguracionUsuario);

    @Test
    public void verificarUsuario() {
        Usuario usuario = givenExisteUsuarioLogin("admin@gmail.com", "1234abcd");
        Usuario resultado = whenBuscarUsuario(usuario.getEmail(), usuario.getPassword());
        thenUsuarioBuscadoExitoso(usuario, resultado);
    }

    private Usuario givenExisteUsuarioLogin(String email, String password) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        when(repositorioUsuario.buscarUsuario(email, password)).thenReturn(usuario);
        return usuario;
    }

    private Usuario whenBuscarUsuario(String email, String password) {
        return servicioLogin.verificarUsuario(email, password);
    }

    private void thenUsuarioBuscadoExitoso(Usuario usuario, Usuario resultado) {
        assertEquals(usuario, resultado);
        verify(repositorioUsuario).buscarUsuario(usuario.getEmail(), usuario.getPassword());
    }

    @Test
    public void testRegistrarUsuario() throws Exception {
        Usuario usuario = givenExisteUsuarioNoRegistrado("admin@gmail.com", "1234abcd", "masculino", 25, 170.0, 65.0);
        whenRegistrarUsuario(usuario);
        thenRegistroUsuarioExitoso(usuario);
    }

    private Usuario givenExisteUsuarioNoRegistrado(String email, String password, String genero, int edad, double altura, double peso) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setGenero(genero);
        usuario.setEdad(edad);
        usuario.setAltura(altura);
        usuario.setPeso(peso);
        when(repositorioUsuario.buscarPorEmail(usuario.getEmail())).thenReturn(null);
        return usuario;
    }

    private void whenRegistrarUsuario(Usuario usuario) throws UsuarioExistente, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        servicioLogin.registrarUsuario(usuario);
    }

    private void thenRegistroUsuarioExitoso(Usuario usuario) {
        verify(repositorioUsuario).guardar(usuario);
        assertEquals("icono-perfil-1.png", usuario.getImagen());
        assertNotNull(usuario.getConfiguracionUsuario());
        assertTrue(usuario.getConfiguracionUsuario().getRecibirNotificaciones());
        assertEquals("calorias", usuario.getConfiguracionUsuario().getUnidadEnergia());
        assertEquals("kilogramos", usuario.getConfiguracionUsuario().getUnidadMasa());
    }

    @Test
    public void testRegistrarUsuarioExistente() {
        Usuario usuario = givenExisteUsuarioRegistrado("admin@gmail.com", "1234abcd", "masculino", 24, 155.0, 44.0);
        whenRegistrarUsuarioException(usuario);
        thenRegistroUsuarioFallido(usuario);
    }

    private Usuario givenExisteUsuarioRegistrado(String email, String password, String genero, int edad, double altura, double peso) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setGenero(genero);
        usuario.setEdad(edad);
        usuario.setAltura(altura);
        usuario.setPeso(peso);
        when(repositorioUsuario.buscarPorEmail(usuario.getEmail())).thenReturn(usuario);
        return usuario;
    }

    private void whenRegistrarUsuarioException(Usuario usuario) {
        assertThrows(UsuarioExistente.class, () -> {
            servicioLogin.registrarUsuario(usuario);
        });
    }

    private void thenRegistroUsuarioFallido(Usuario usuario) {
        verify(repositorioUsuario, never()).guardar(usuario);
    }

    @Test
    public void testModificarUsuario() throws Exception {
        Usuario usuario = givenExisteUsuarioRegistrado("admin@gmail.com", "1234abcd", "masculino", 24, 155.0, 44.0);
        Usuario nuevosDatos = givenExisteUsuarioNoRegistrado("pacolo@gmail.com", "querty123", "masculino", 25, 158.0, 46.0);
        whenModificarUsuario(usuario, nuevosDatos);
        thenModificacionExitosa(usuario, nuevosDatos);
    }

    private void whenModificarUsuario(Usuario usuario, Usuario nuevosDatos) throws UsuarioExistente, DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException {
        servicioLogin.modificarUsuario(usuario, nuevosDatos);
    }

    private void thenModificacionExitosa(Usuario usuario, Usuario nuevosDatos) {
        verify(repositorioUsuario).modificar(usuario);
        assertEquals(nuevosDatos.getEmail(), usuario.getEmail());
        assertEquals(nuevosDatos.getPassword(), usuario.getPassword());
        assertEquals(nuevosDatos.getEdad(), usuario.getEdad());
        assertEquals(nuevosDatos.getAltura(), usuario.getAltura());
        assertEquals(nuevosDatos.getPeso(), usuario.getPeso());
    }

    @Test
    public void testModificarUsuarioExistente() {
        Usuario usuario1 = givenExisteUsuarioRegistrado("admin@gmail.com", "1234abcd", "masculino", 25, 170.0, 65.0);
        Usuario usuario2 = givenExisteUsuarioRegistrado("pacolo@gmail.com", "querty123", "masculino", 21, 155.0, 44.0);
        Usuario nuevosDatos = givenEmailRegistrado("pacolo@gmail.com", "1234abcd", "masculino", 25, 170.0, 65.0, usuario2);
        whenModificarUsuarioException(usuario1, nuevosDatos);
        thenModificacionFallida(usuario1);
    }

    private Usuario givenEmailRegistrado(String email, String password, String genero, int edad, double altura, double peso, Usuario usuario) {
        Usuario nuevosDatos = new Usuario();
        nuevosDatos.setEmail(email);
        nuevosDatos.setNombre(password);
        nuevosDatos.setGenero(genero);
        nuevosDatos.setEdad(edad);
        nuevosDatos.setAltura(altura);
        nuevosDatos.setPeso(peso);
        when(repositorioUsuario.buscarPorEmail(nuevosDatos.getEmail())).thenReturn(usuario);
        return nuevosDatos;
    }

    private void whenModificarUsuarioException(Usuario usuario, Usuario nuevosDatos) {
        assertThrows(UsuarioExistente.class, () -> {
            servicioLogin.modificarUsuario(usuario, nuevosDatos);
        });       
    }

    private void thenModificacionFallida(Usuario usuario) {
        verify(repositorioUsuario, never()).modificar(usuario);
    } 

    @Test
    public void testEliminarUsuario() {
        Usuario usuario = givenExisteUsuarioRegistrado("admin@gmail.com", "1234abcd", "masculino", 25, 155.0, 44.0);
        whenEliminarUsuario(usuario);
        thenEliminacionExitoso(usuario);        
    }

    private void whenEliminarUsuario(Usuario usuario) {
        assertDoesNotThrow(() -> {
            servicioLogin.eliminarUsuario(usuario);
        });
    }

    private void thenEliminacionExitoso(Usuario usuario) {
        verify(repositorioUsuario).eliminar(usuario);
    }

    @Test
    public void testEliminarUsuarioNoExistente() {
        Usuario usuario = givenExisteUsuarioNoRegistrado("admin@gmail.com", "1234abcd", "masculino", 25, 155.0, 44.0);
        whenEliminarUsuarioException(usuario);
        thenEliminacionFallida(usuario);
    }

    private void whenEliminarUsuarioException(Usuario usuario) {
        assertThrows(UsuarioNoExistente.class, () -> {
            servicioLogin.eliminarUsuario(usuario);
        });       
    }

    private void thenEliminacionFallida(Usuario usuario) {
        verify(repositorioUsuario, never()).eliminar(usuario);
    }

    @Test
    public void testValidarDatos() throws Exception {
        Usuario usuario = givenExisteUsuarioRegistrado("admin@gmail.com", "1234abcd", "masculino", 23, 155.0, 44.0);
        thenDatosValidados(usuario);
    }

    private void thenDatosValidados(Usuario usuario) throws DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException {
        assertTrue(servicioLogin.validarDatos(usuario));
    }

    @Test
    public void testValidarDatosIncorrectos() {
        Usuario usuario = givenExisteUsuarioNoRegistrado(null, null, "femenino", 24, 156.0, 42.0);
        thenDatosNoValidados(usuario);
    }

    private void thenDatosNoValidados(Usuario usuario) {
        assertThrows(DatosIncorrectos.class, () -> {
            servicioLogin.validarDatos(usuario);
        }); 
    } 

}
