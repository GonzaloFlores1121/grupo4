package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.servlet.http.HttpServletRequest;

import java.sql.Date;

import static org.mockito.ArgumentMatchers.any;
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
    @Test
    public void agregoUnEjercicioExitosamente() throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, UsuarioExistente {
        // Simular el registro del usuario
        Usuario usuario = givenTengoUnUsuario();

        // Simular la llamada al método guardarEjercicio del servicio
        when(servicioEjercicio.guardarEjercicio(any(EjercicioUsuario.class))).thenReturn(true);

        // Simular los datos del usuario en el request
        HttpServletRequest request = new MockHttpServletRequest();
        request.getSession().setAttribute("usuario", usuario);

        // Llamar al método del controlador que queremos probar
        controladorEjercicio.guardarEjercicio(  Long.valueOf(1), "alta",  new Date(2020,04,12),  Integer.valueOf(30), request);

        // Verificar si se pasa el mensaje correcto al modelo
        // assertEquals("El ejercicio se ha guardado correctamente.", modelAndView.getModel().get("mensaje"));
    }

    private Usuario givenTengoUnUsuario() throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, UsuarioExistente {

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
        servicioLogin.registrar(usuario);
        return usuario;
    }

}
