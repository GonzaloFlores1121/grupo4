package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.DatosLogin;
import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class ControladorFormularioRegistroTest {
    private ControladorRegistro controladorRegistro;

    private ServicioDatosUsuario servicioDatosUsuarioMock;

    @BeforeEach
    public void init(){
        servicioDatosUsuarioMock=mock(ServicioDatosUsuario.class);
        controladorRegistro = new ControladorRegistro(servicioDatosUsuarioMock);
    }

    @Test
    public void seRegistraAlUsuario() throws DatosIncorrectos {
        DatosLogin datosRegistro = whenRegistroAlUsuario();

        when(servicioDatosUsuarioMock.registrarUsuario(any(Usuario.class))).thenReturn(true);

        Boolean usuarioRegistrado = thenElUsuarioSeRegistro(datosRegistro);

        assertEquals(true, usuarioRegistrado);
    }

    private Boolean thenElUsuarioSeRegistro(DatosLogin datosRegistro) throws DatosIncorrectos {
        HttpServletRequest requestMock = mock(HttpServletRequest.class);

        ModelAndView modelAndView = controladorRegistro.enviarFormulario(datosRegistro, requestMock);

        String vistaDestino = modelAndView.getViewName();

        return "redirect:/menuprincipal".equals(vistaDestino);
    }

    private DatosLogin whenRegistroAlUsuario() {
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


}


