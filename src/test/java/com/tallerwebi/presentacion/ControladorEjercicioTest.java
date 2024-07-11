package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ControladorEjercicioTest {

    private ControladorEjercicio controladorEjercicio;
    private RepositorioEjercicio repositorioEjercicio;
    private RepositorioEjercicioUsuario repositorioEjercicioUsuario;
    private RepositorioUsuario repositorioUsuario;
    private ServicioEjercicio servicioEjercicio;
    private ServicioLogin servicioLogin;

    @BeforeEach
    public void setUp() {
        // Inicializa tus mocks o instancias de las clases necesarias
        servicioLogin=mock(ServicioLogin.class);
        repositorioEjercicio = mock(RepositorioEjercicio.class);
        repositorioEjercicioUsuario = mock(RepositorioEjercicioUsuario.class);
        repositorioUsuario = mock(RepositorioUsuario.class);
        servicioEjercicio = mock(ServicioEjercicio.class);

        // Crea una instancia de ControladorEjercicio con los argumentos necesarios
        controladorEjercicio = new ControladorEjercicio(repositorioEjercicio, repositorioEjercicioUsuario, repositorioUsuario, servicioEjercicio);
    }
    /*@Test
    public void agregoUnEjercicioExitosamente() throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, UsuarioExistente, EjercicioInvalido, EjercicioNoExistente {

        Usuario usuario = givenTengoUnUsuario();


        servicioEjercicio.guardarEjercicioUsuario(anyString(),  anyString(),  any(Ejercicio.class),  any(Usuario.class),  any(Date.class),  anyInt()  );


        HttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("usuario", usuario);

        controladorEjercicio.guardarEjercicio(  Long.valueOf(1), "alta",  new Date(2020,04,12),  Integer.valueOf(30), request);

        // Verificar si se pasa el mensaje correcto al modelo
        // assertEquals("El ejercicio se ha guardado correctamente.", modelAndView.getModel().get("mensaje"));
    }
*/
    private Usuario givenTengoUnUsuario() throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, UsuarioExistente, PesoMetaIncorrectoException {

        String email = "ejemplo@example.com";
        String password = "contraseña123";
        Double peso = 70.0;
        Double altura = 170.0;
        Integer edad = 18;
        String sexo = "femenino";
        String nivelActividad = "sedentario";
        String nombre = "Tomas";

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setPeso(peso);
        usuario.setAltura(altura);
        usuario.setGenero(sexo);
        usuario.setNivelDeActividad(nivelActividad);
        usuario.setEdad(edad);
        usuario.setNombre(nombre);
        servicioLogin.registrarUsuario(usuario);
        return usuario;
    }

    @Test
    public void seBuscaUnEjercicioPorNombreOIntensidad() throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, UsuarioExistente, EjercicioInvalido, EjercicioNoExistente, PesoMetaIncorrectoException {

        Usuario usuario = givenTengoUnUsuario();
        List<Ejercicio> ejercicios = new ArrayList<>();


        when(servicioEjercicio.obtenerEjercicioPorNombreOIntensidad(anyString())).thenReturn(ejercicios);


        HttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("usuario", usuario);


        ModelAndView modelAndView = controladorEjercicio.buscarEjercicio("testEjercicio", request);


        assertEquals(ejercicios, modelAndView.getModel().get("listaEjercicios"));
        assertEquals(usuario, modelAndView.getModel().get("usuario"));
    }
   
}
