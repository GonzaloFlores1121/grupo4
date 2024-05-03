package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.infraestructura.ServicioDatosUsuarioImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

public class ServicioFormularioRegistroTest {


    private ServicioDatosUsuario servicioUsuario = new ServicioDatosUsuarioImpl();

    @Test
    public void elUsuarioNoPuedeRegistrarseSiSuPesoEsMenorACero() {

        Usuario usuario = givenTengoUnUsuarioConPesoInvalido();


        boolean registroDenegado = thenRegistroDenegado(usuario);


        assertTrue(registroDenegado);
    }

    private Usuario givenTengoUnUsuarioConPesoInvalido() {
        Usuario usuario = new Usuario();
        String email = "ejemplo@example.com";
        String password = "contraseña123";
        Double peso = 0.0;
        Double altura = 170.0;
        Integer edad = 25;
        String sexo = "femenino"; // Cambiar según necesites
        String nivelActividad = "sedentario"; // Cambiar según necesites

        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setPeso(peso);
        usuario.setAltura(altura);
        usuario.setGenero(sexo);
        usuario.setNivelDeActividad(nivelActividad);
        usuario.setEdad(67);

        return usuario;
    }

    private boolean thenRegistroDenegado(Usuario usuario) {
        try {
            servicioUsuario.registrarUsuario(usuario);
            return false; // El registro debería haber sido denegado
        } catch (DatosIncorrectos e) {
            return true; // El registro fue denegado correctamente
        }
    }
    @Test
    public void elUsuarioPuedeRegistrarseSiTodosLosDatosSonCorrectos() {

        Usuario usuario = givenTengoUnUsuarioConDatosValidos();


        boolean registroAceptado = thenRegistroAceptado(usuario);


        assertTrue(registroAceptado);
    }

    private Usuario givenTengoUnUsuarioConDatosValidos() {
        Usuario usuario = new Usuario();
        String email = "ejemplo@example.com";
        String password = "contraseña123";
        Double peso = 70.0; // Peso válido
        Double altura = 170.0;
        String sexo = "femenino";
        Integer edad = 25;
        String nivelActividad = "sedentario";

        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setPeso(peso);
        usuario.setAltura(altura);
        usuario.setGenero(sexo);
        usuario.setNivelDeActividad(nivelActividad);
        usuario.setEdad(36);

        return usuario;
    }

    private boolean thenRegistroAceptado(Usuario usuario) {
        try {
            servicioUsuario.registrarUsuario(usuario);
            return true; // El registro fue aceptado correctamente
        } catch (DatosIncorrectos e) {
            return false; // El registro no debería haber sido denegado
        }
    }
    @Test
    public void elUsuarioNoPuedeRegistrarseSiSuEdadEsMenorADieciOcho() {

        Usuario usuario = givenTengoUnUsuarioMenorDeEdad();


        boolean registroDenegado = thenRegistroDenegado(usuario);


        assertTrue(registroDenegado);
    }

    private Usuario givenTengoUnUsuarioMenorDeEdad() {
        Usuario usuario = new Usuario();
        String email = "ejemplo@example.com";
        String password = "contraseña123";
        Double peso = 70.0; // Peso válido
        Double altura = 170.0;
        String sexo = "femenino";
        Integer edad = 3;
        String nivelActividad = "sedentario";

        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setPeso(peso);
        usuario.setAltura(altura);
        usuario.setGenero(sexo);
        usuario.setNivelDeActividad(nivelActividad);
        usuario.setEdad(edad);

        return usuario;
    }
}