package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.DatosLogin;
import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ControladorFormularioRegistroTest {
    private ControladorRegistro controladorRegistro;
    private ControladorMenuPrincipal controladorMenu;
    private ServicioDatosUsuario servicioDatosUsuarioMock;

    //cuando registro al user me lleva al menuPrincipal

    @BeforeEach
    public void init() {
        servicioDatosUsuarioMock = mock(ServicioDatosUsuario.class);
        controladorRegistro = new ControladorRegistro(servicioDatosUsuarioMock);
        controladorMenu = new ControladorMenuPrincipal(servicioDatosUsuarioMock);
    }

    @Test
    public void seRegistraAlUsuario() throws DatosIncorrectos {
        // Given
        Usuario usuario = givenDatosDeRegistro();

        HttpServletRequest solicitudRegistro = mock(HttpServletRequest.class);
        HttpSession sessionMock = mock(HttpSession.class);
        //comportamiento esperado del objeto simulado
        when(solicitudRegistro.getSession()).thenReturn(sessionMock);

        // When
        whenRegistroAlUsuario(usuario, solicitudRegistro);

        // Then
        thenUsuarioSeRegistroCorrectamente( sessionMock, usuario);
    }

    private Usuario givenDatosDeRegistro() {
        String email = "ejemplo@example.com";
        String password = "contraseña123";
        Double peso = 70.0;
        Double altura = 170.0;
        Integer edad = 18;
        String sexo = "femenino";
        String nivelActividad = "sedentario";

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setPeso(peso);
        usuario.setAltura(altura);
        usuario.setGenero(sexo);
        usuario.setNivelDeActividad(nivelActividad);
        usuario.setEdad(edad);

        return usuario;
    }

    private void whenRegistroAlUsuario(Usuario usuario, HttpServletRequest requestMock) throws DatosIncorrectos {
        // Lógica para registrar al usuario
        when(servicioDatosUsuarioMock.registrarUsuario(any(Usuario.class))).thenReturn(true);//escenario en el que se registra el usuario con exitow :)

       controladorRegistro.enviarFormulario(usuario, requestMock);


    }

    private void thenUsuarioSeRegistroCorrectamente( HttpSession sessionMock,Usuario usuario) {

        verify(sessionMock).setAttribute(eq("usuario"), any(Usuario.class));

        // Capturar el usuario que se pasó al método setAttribute
        ArgumentCaptor<Usuario> usuarioCaptor = ArgumentCaptor.forClass(Usuario.class);
        verify(sessionMock).setAttribute(eq("usuario"), usuarioCaptor.capture());
        Usuario usuarioReg = usuarioCaptor.getValue();


        assertEquals(usuario.getEmail(), usuarioReg.getEmail());
    }

}