package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.infraestructura.ServicioDatosUsuarioImpl;
import com.tallerwebi.infraestructura.ServicioLoginImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioFormularioRegistroTest {

    private SessionFactory sessionFactory;
    private  RepositorioUsuario repositorioUsuario;
    private ServicioDatosUsuario servicioUsuario ;
    private ServicioLogin servicioLogin;

    @BeforeEach
    public void init() {
       repositorioUsuario=mock(RepositorioUsuarioImpl.class);
       servicioUsuario=mock(ServicioDatosUsuarioImpl.class);
       servicioLogin=mock(ServicioLoginImpl.class);
    }

    @Test
    public void elUsuarioNoPuedeRegistrarseSiSuPesoEsMenorACero() throws DatosIncorrectos, UsuarioExistente, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, PesoMetaIncorrectoException {
        Usuario usuario = givenTengoUnUsuarioConPesoInvalido();

        assertTrue(thenRegistroDenegado(usuario));
    }

    private Usuario givenTengoUnUsuarioConPesoInvalido() {
        Usuario usuario = new Usuario();
        usuario.setEmail("ejemplo@example.com");
        usuario.setPassword("contraseña123");
        usuario.setPeso(0.0);
        usuario.setAltura(170.0);
        usuario.setGenero("femenino");
        usuario.setNivelDeActividad("sedentario");
        usuario.setEdad(67);
        usuario.setNombre("Tomas");

        return usuario;
    }

    private Boolean thenRegistroDenegado(Usuario usuario) throws DatosIncorrectos, UsuarioExistente, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, PesoMetaIncorrectoException {
       if(!servicioLogin.validarDatos(usuario)){
          return true;
       }
        return false;
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
        String nombre = "Tomas";

        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setPeso(peso);
        usuario.setAltura(altura);
        usuario.setGenero(sexo);
        usuario.setNivelDeActividad(nivelActividad);
        usuario.setEdad(36);
        usuario.setNombre("Tomas");

        return usuario;
    }

    private boolean thenRegistroAceptado(Usuario usuario) {
        try {
            servicioLogin.registrarUsuario(usuario);
            return true;
        } catch (DatosIncorrectos e) {
            return false;
        } catch (UsuarioExistente e) {
            throw new RuntimeException(e);
        } catch (AlturaIncorrectaException e) {
            throw new RuntimeException(e);
        } catch (EdadInvalidaException e) {
            throw new RuntimeException(e);
        } catch (PesoIncorrectoException e) {
            throw new RuntimeException(e);
        } catch (PesoMetaIncorrectoException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void elUsuarioNoPuedeRegistrarseSiSuEdadEsMenorADieciOcho() throws DatosIncorrectos, UsuarioExistente, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, PesoMetaIncorrectoException {

        Usuario usuario = givenTengoUnUsuarioMenorDeEdad();


        assertTrue(thenRegistroDenegado(usuario));
    }

    private Usuario givenTengoUnUsuarioMenorDeEdad() {
        Usuario usuario = new Usuario();
        String email = "ejemplo@example.com";
        String password = "contraseña123";
        Double peso = 70.0;
        Double altura = 170.0;
        String sexo = "femenino";
        Integer edad = 3;
        String nivelActividad = "sedentario";
        String nombre = "Tomas";

        usuario.setEmail(email);
        usuario.setPassword(password);
        usuario.setPeso(peso);
        usuario.setAltura(altura);
        usuario.setGenero(sexo);
        usuario.setNivelDeActividad(nivelActividad);
        usuario.setEdad(edad);
        usuario.setNombre("Tomas");

        return usuario;
    }
}
