package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.infraestructura.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioEjercicioTest {

    private SessionFactory sessionFactory;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioEjercicio repositorioEjercicio;
    private RepositorioEjercicioUsuario repositorioMock;
    private ServicioEjercicio servicioEjercicio;


    @BeforeEach
    public void init() {
        sessionFactory = mock(SessionFactory.class);
        repositorioUsuario = new RepositorioUsuarioImpl(sessionFactory);
        repositorioEjercicio = new RepositorioEjercicioImpl(sessionFactory);
        repositorioMock = mock(RepositorioEjercicioUsuario.class);
        servicioEjercicio = new ServicioEjercicioImpl(repositorioEjercicio, repositorioMock);

    }

    @Test
    public void guardarEjercicio_CamposValidos_DeberiaRetornarTrue() throws EjercicioInvalido  {
        // Crear un usuario para asociar con el ejercicio
        Usuario usuario = givenTengoUnUsuario();

        // Guardar un ejercicio utilizando el servicio y obtener el ejercicio guardado
        EjercicioUsuario ejercicioGuardado = givenGuardoUnEjercicioUsuario(usuario);

        // Configurar el repositorio simulado para retornar el ejercicio guardado
        when(repositorioMock.obtenerEjercicioPorNombre("Ejercicio1")).thenReturn(ejercicioGuardado);

        // Consultar la BD para verificar si el ejercicio se guardó correctamente
        EjercicioUsuario ejercicioEnBD = repositorioMock.obtenerEjercicioPorNombre(ejercicioGuardado.getNombre());

        // Verificar si el ejercicio en la BD es igual al ejercicio guardado
        assertEquals(ejercicioGuardado, ejercicioEnBD);
    }

    @Test
    public void guardarEjercicio_CamposInvalidos_DeberiaRetornarFalse() throws EjercicioInvalido {
        Usuario usuario = givenTengoUnUsuario();

        // Guardar un ejercicio utilizando el servicio y obtener el ejercicio guardado
        EjercicioUsuario ejercicioGuardado = givenGuardoUnEjercicioUsuario(usuario);

        // Configurar el repositorio simulado para lanzar una excepción al intentar obtener el ejercicio por nombre
        when(repositorioMock.obtenerEjercicioPorNombre(anyString())).thenAnswer(invocation -> {
            throw new EjercicioInvalido("Mensaje de error");
        });

        // Intentar obtener el ejercicio por nombre, esto debería lanzar una excepción
        assertThrows(EjercicioInvalido.class, () -> {
            repositorioMock.obtenerEjercicioPorNombre(ejercicioGuardado.getNombre());
        });
    }
  private Usuario givenTengoUnUsuario() {

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

      return usuario;
  }
    private EjercicioUsuario givenGuardoUnEjercicioUsuario(Usuario usuario) throws EjercicioInvalido {
        EjercicioUsuario ejercicioUsuario = new EjercicioUsuario();
        Ejercicio ejercicio = new Ejercicio("Correr", "Alta", 100);

        ejercicioUsuario.setNombre("Ejercicio1");
        ejercicioUsuario.setMinutos(30);
        ejercicioUsuario.setFecha(new Date(2020, 4, 17).toLocalDate());
        ejercicioUsuario.setIntensidad("Alta");
        ejercicioUsuario.setEjercicio(ejercicio);
        ejercicioUsuario.setUsuario(usuario);
        servicioEjercicio.guardarEjercicioUsuario("Ejercicio1", "Alta", ejercicio, usuario, new Date(2020, 4, 17), 23);
        return ejercicioUsuario;
    }

    private EjercicioUsuario givenGuardoUnEjercicioUsuarioInvalido(Usuario usuario) throws EjercicioInvalido {
        EjercicioUsuario ejercicioUsuario = new EjercicioUsuario();
        Ejercicio ejercicio = new Ejercicio("Correr", "Alta", 100);

        ejercicioUsuario.setNombre(" ");
        ejercicioUsuario.setMinutos(30);
        ejercicioUsuario.setFecha(new Date(2020, 4, 17).toLocalDate());
        ejercicioUsuario.setIntensidad("Alta");
        ejercicioUsuario.setEjercicio(ejercicio);
        ejercicioUsuario.setUsuario(usuario);
        servicioEjercicio.guardarEjercicioUsuario("Ejercicio1", "Alta", ejercicio, usuario, new Date(2020, 4, 17), 23);
        return ejercicioUsuario;
    }
}