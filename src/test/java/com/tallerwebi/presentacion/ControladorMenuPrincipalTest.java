package com.tallerwebi.presentacion;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;

public class ControladorMenuPrincipalTest {

  /*  //siMiUsuarioNoTieneLOsDatosVuelveAlInicio

    private ControladorHome controladorMenu;
    private ControladorRegistro controladorRegistro;
    private ServicioDatosUsuario servicioDatosUsuarioMock;

    @BeforeEach
    public void init() {
        servicioDatosUsuarioMock = mock(ServicioDatosUsuario.class);
        controladorMenu = new ControladorHome(servicioDatosUsuarioMock);
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
        usuario.setNombre("Tomas");


        // Configurar los parámetros de la solicitud para simular los datos del formulario
        solicitud.setParameter("email", usuario.getEmail());
        solicitud.setParameter("password", usuario.getPassword());
        solicitud.setParameter("genero", usuario.getGenero());
        solicitud.setParameter("nivelDeActividad", usuario.getNivelDeActividad());
        solicitud.setParameter("peso", String.valueOf(usuario.getPeso()));
        solicitud.setParameter("altura", String.valueOf(usuario.getAltura()));
        solicitud.setParameter("edad", String.valueOf(usuario.getEdad()));
        solicitud.setParameter("nombre", String.valueOf(usuario.getNombre()));

        controladorRegistro.enviarFormulario(usuario, solicitud);

    }
*/

}
