package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.servlet.http.HttpServletRequest;
import org.hibernate.Session;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioEjercicioTest {

    private SessionFactory sessionFactory;
    private  RepositorioUsuario repositorioUsuario;
    private  RepositorioEjercicio repositorioEjercicio;
    private  RepositorioEjercicioUsuario repositorioMock;
    private ServicioEjercicio servicioEjercicio;


    @BeforeEach
    public void init() {
        sessionFactory = mock(SessionFactory.class);
        repositorioUsuario = new RepositorioUsuarioImpl(sessionFactory);
        repositorioEjercicio = new RepositorioEjercicioImpl(sessionFactory);
         repositorioMock = mock(RepositorioEjercicioUsuario.class);
        servicioEjercicio=new ServicioEjercicioImpl(repositorioEjercicio,repositorioMock);

    }
    @Test
    public void guardarEjercicio_CamposValidos_DeberiaRetornarTrue() {

        EjercicioUsuario ejercicioValido = givenTengoUnEjercicioUsuario();

        // Ejecutar el método y verificar el resultado
        boolean resultado = servicioEjercicio.guardarEjercicio(ejercicioValido);
        assertTrue(resultado);
    }

  /*  @Test
    public void guardarEjercicio_CamposInvalidos_DeberiaRetornarFalse() {
        // Configurar el entorno de prueba
        RepositorioEjercicioUsuario repositorioMock = mock(RepositorioEjercicioUsuario.class);
        ServicioEjercicioImpl servicio = new ServicioEjercicioImpl(null, repositorioMock);
        EjercicioUsuario ejercicioInvalido = whenAgregoElEjercicioUsuario();

        // Ejecutar el método y verificar el resultado
        boolean resultado = servicio.guardarEjercicio(ejercicioInvalido);
        assertFalse(resultado);
    }*/

    private EjercicioUsuario givenTengoUnEjercicioUsuario() {
        EjercicioUsuario ejercicioUsuario = new EjercicioUsuario();
        Usuario usuario = new Usuario();
        Ejercicio ejercicio=new Ejercicio();

        ejercicioUsuario.setNombre("Ejercicio1");
        ejercicioUsuario.setMinutos(30);
        ejercicioUsuario.setFecha(new Date(2020, 4, 17));
        ejercicioUsuario.setIntensidad("Alta");
        ejercicioUsuario.setEjercicio(ejercicio);
        ejercicioUsuario.setUsuario(usuario);
    return ejercicioUsuario;
    }




}
