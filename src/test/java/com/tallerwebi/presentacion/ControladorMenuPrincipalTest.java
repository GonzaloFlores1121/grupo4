package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.DatosLogin;
import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.IsEqualIgnoringCase.equalToIgnoringCase;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorMenuPrincipalTest {

    //siMiUsuarioNoTieneLOsDatosVuelveAlInicio

    private ControladorMenuPrincipal controladorMenu;
    private ControladorRegistro controladorRegistro;
    private ServicioDatosUsuario servicioDatosUsuarioMock;

    @BeforeEach
    public void init() {
        servicioDatosUsuarioMock = mock(ServicioDatosUsuario.class);
        controladorMenu = new ControladorMenuPrincipal(servicioDatosUsuarioMock);
        controladorRegistro = new ControladorRegistro(servicioDatosUsuarioMock);
    }

    @Test
    public void siMiUsuarioTieneTodosLosDatosSeMeLlevaAMiDiario() throws DatosIncorrectos {
        MockHttpServletRequest solicitud= new MockHttpServletRequest();
        givenTengoUnUsuarioRegistrado(solicitud);

        Usuario usuario=whenSeBuscaAlUsuario();

        assertThat(thenSeCalculanLosDatosYMeLLevaALaVistaMiDiario(usuario,solicitud).getViewName(), equalToIgnoringCase("miDiario"));

    }

    private ModelAndView thenSeCalculanLosDatosYMeLLevaALaVistaMiDiario( Usuario usuario,MockHttpServletRequest solicitud) throws DatosIncorrectos {
      return controladorMenu.irAMiDiario(solicitud);

    }

    private Usuario whenSeBuscaAlUsuario() {
        HttpSession session = mock(HttpSession.class);
        when(session.getAttribute("usuario")).thenReturn(new Usuario());
        return (Usuario) session.getAttribute("usuario");
    }

    private void givenTengoUnUsuarioRegistrado(MockHttpServletRequest solicitud ) throws DatosIncorrectos {
        Usuario usuario = new Usuario();
        usuario.setEmail("datos@example.com");
        usuario.setPassword("password123");
        usuario.setGenero("masculino");
        usuario.setNivelDeActividad("activo");
        usuario.setPeso(75.5);
        usuario.setAltura(175.0);
        usuario.setEdad(30);


        // Configurar los parámetros de la solicitud para simular los datos del formulario
        solicitud.setParameter("email", usuario.getEmail());
        solicitud.setParameter("password", usuario.getPassword());
        solicitud.setParameter("genero", usuario.getGenero());
        solicitud.setParameter("nivelDeActividad", usuario.getNivelDeActividad());
        solicitud.setParameter("peso", String.valueOf(usuario.getPeso()));
        solicitud.setParameter("altura", String.valueOf(usuario.getAltura()));
        solicitud.setParameter("edad", String.valueOf(usuario.getEdad()));


        controladorRegistro.enviarFormulario(usuario, solicitud);

    }

}
