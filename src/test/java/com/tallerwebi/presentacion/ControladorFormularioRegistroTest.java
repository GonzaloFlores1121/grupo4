package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.DatosLogin;
import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    @BeforeEach
    public void init() {
        servicioDatosUsuarioMock = mock(ServicioDatosUsuario.class);
        controladorRegistro = new ControladorRegistro(servicioDatosUsuarioMock);
        controladorMenu = new ControladorMenuPrincipal(servicioDatosUsuarioMock);
    }

    @Test
    public void seRegistraAlUsuario() throws DatosIncorrectos {
        // Given
        DatosLogin datosRegistro = givenDatosDeRegistro();

        HttpServletRequest solicitudRegistro = mock(HttpServletRequest.class);
        HttpSession sessionMock = mock(HttpSession.class);
        //comportamiento esperado del objeto simulado
        when(solicitudRegistro.getSession()).thenReturn(sessionMock);

        // When
        Boolean usuarioRegistrado = whenRegistroAlUsuario(datosRegistro, solicitudRegistro);

        // Then
        thenUsuarioSeRegistroCorrectamente(usuarioRegistrado, sessionMock);
    }

    private DatosLogin givenDatosDeRegistro() {
        String email = "ejemplo@example.com";
        String password = "contraseña123";
        Double peso = 70.0;
        Double altura = 170.0;
        Integer edad = 18;
        String sexo = "femenino"; // Cambiar según necesites
        String nivelActividad = "sedentario"; // Cambiar según necesites

        DatosLogin datosLogin = new DatosLogin();
        datosLogin.setEmail(email);
        datosLogin.setPassword(password);
        datosLogin.setPeso(peso);
        datosLogin.setAltura(altura);
        datosLogin.setSexo(sexo);
        datosLogin.setNivelDeActividad(nivelActividad);
        datosLogin.setEdad(edad);

        return datosLogin;
    }

    private Boolean whenRegistroAlUsuario(DatosLogin datosRegistro, HttpServletRequest requestMock) throws DatosIncorrectos {
        // Lógica para registrar al usuario
        when(servicioDatosUsuarioMock.registrarUsuario(any(Usuario.class))).thenReturn(true);//escenario en el que se registra el usuario con exitow :)

        ModelAndView modelAndView = controladorRegistro.enviarFormulario(datosRegistro, requestMock);
        String vistaDestino = modelAndView.getViewName();
        return "redirect:/menuprincipal".equals(vistaDestino);
    }

    private void thenUsuarioSeRegistroCorrectamente(Boolean usuarioRegistrado, HttpSession sessionMock) {

        verify(sessionMock).setAttribute(eq("usuario"), any(Usuario.class));
    }


}