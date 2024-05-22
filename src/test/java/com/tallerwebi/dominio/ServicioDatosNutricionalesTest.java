package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.AlturaIncorrectaException;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.dominio.excepcion.EdadInvalidaException;
import com.tallerwebi.dominio.excepcion.PesoIncorrectoException;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.infraestructura.RepositorioConfiguracionUsuarioImpl;
import com.tallerwebi.infraestructura.ServicioDatosUsuarioImpl;
import com.tallerwebi.infraestructura.ServicioLoginImpl;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ServicioDatosNutricionalesTest {

    private SessionFactory sessionFactory;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioConfiguracionUsuario repositorioConfiguracionUsuario;
    private ServicioDatosUsuario servicioUsuario ;
    private ServicioLogin servicioLogin;
    private RepositorioHistorialPesoUsuario repositorioHistorialPesoUsuario;

    @BeforeEach
    public void init() {
        sessionFactory = mock(SessionFactory.class);
        repositorioUsuario = new RepositorioUsuarioImpl(sessionFactory);
        repositorioConfiguracionUsuario = new RepositorioConfiguracionUsuarioImpl(sessionFactory); 
        servicioLogin = new ServicioLoginImpl(repositorioUsuario, repositorioConfiguracionUsuario);
        servicioUsuario = new ServicioDatosUsuarioImpl(servicioLogin,repositorioHistorialPesoUsuario,repositorioUsuario);
    }


    @Test
    public void IngestaCaloricaDeUsuarioHombreDe40SedentarioYTiene70kgYMide170() throws  DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        Usuario usuario = givenTengoUnUsuario();
        Integer icr = whenSeCalculaSuIngestaCalorica(usuario);
        thenLaIngestaCaloricaEsCorrecta(icr );

    }

    private void thenLaIngestaCaloricaEsCorrecta(Integer icr) {

        assertEquals(1938, icr,0.0);
    }

    private Integer whenSeCalculaSuIngestaCalorica(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        // Verifica si los datos del usuario son válidos antes de calcular la ingesta calórica
        if (servicioLogin.usuarioDatosCorrecto(usuario)) {
            return servicioUsuario.calcularIngestaCalorica(usuario);

        }
        throw new DatosIncorrectos("Datos incorrectos del usuario");
    }


        private Usuario givenTengoUnUsuario () {
            Usuario usuario = new Usuario();
            usuario.setEdad(40);
            usuario.setGenero("masculino");
            usuario.setPeso(70.0);
            usuario.setAltura(170.0);
            usuario.setNivelDeActividad("sedentario");
            usuario.setPassword("123");
            usuario.setEmail("123@gmail.com");
            usuario.setNombre("Lucas");
            return usuario;

        }

    @Test
    public void NoSePuedeCalcularLaIngestaCaloricaSiElUsuarioNoEstaRegistradoConSusDatos() throws DatosIncorrectos {
        Usuario usuario = givenTengoUnUsuarioConDatosIncompletos();

        assertThrows(DatosIncorrectos.class, () -> {
            whenSeCalculaSuIngestaCalorica(usuario);
        });


    }

    private Usuario givenTengoUnUsuarioConDatosIncompletos() {
        Usuario usuario = new Usuario();
        usuario.setGenero("masculino");
        usuario.setPeso(70.0);
        usuario.setAltura(170.0);
        usuario.setNivelDeActividad("sedentario");
        return usuario;
    }

    @Test
    public void SeGuardanLosMacronutrientesDelUsuario() throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        Usuario usuario = givenTengoUnUsuario();

        MacronutrientesUsuario macro=whenCalculoLosMacronutrientesDelUsuario(usuario);

        thenMeSeGuardanLosMacronutrientes(usuario,macro);
    }

    private void thenMeSeGuardanLosMacronutrientes(Usuario usuario, MacronutrientesUsuario macro) throws DatosIncorrectos {

        Integer vo1 = macro.getGrasaAConsumir();
        Integer vo2 = macro.getProteinaAConsumir();
        Integer vo3 = macro.getCarbohidratosAConsumir();
        assertNotNull(vo1);
        assertNotNull(vo2);
        assertNotNull(vo3);
    }

    private MacronutrientesUsuario whenCalculoLosMacronutrientesDelUsuario(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        servicioUsuario.calcularIngestaCalorica(usuario);
        return servicioUsuario.CalcularDistribucionDeMacronutrientes(usuario);
    }

}

