package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.*;
import com.tallerwebi.infraestructura.*;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.module.Configuration;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServicioDatosUsuarioTest {


    private RepositorioUsuario repositorioUsuario;
    private ServicioDatosUsuario servicioUsuario;
    private ServicioLogin servicioLogin;
    private RepositorioHistorialPesoUsuario repositorioHistorialPesoUsuario;
    private ServicioCalendario servicioCalendario;
    private ServicioNotificacion servicioNotificacion;
    private RepositorioMacronutrientes repositorioMacronutrientes;
    private ServicioEjercicio servicioEjercicio;


    @BeforeEach
    public void init() {
        Session sessionMock = mock(Session.class);
        repositorioUsuario = mock(RepositorioUsuarioImpl.class);
        repositorioHistorialPesoUsuario = mock(RepositorioHistorialPesoUsuarioImpl.class);
        servicioLogin = new ServicioLoginImpl(repositorioUsuario, servicioUsuario);
        servicioUsuario = new ServicioDatosUsuarioImpl(servicioLogin, repositorioHistorialPesoUsuario, repositorioUsuario,servicioCalendario,servicioNotificacion, repositorioMacronutrientes, servicioEjercicio);
    }


    @Test
    public void IngestaCaloricaDeUsuarioHombreDe40SedentarioYTiene70kgYMide170() throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, PesoMetaIncorrectoException {
        Usuario usuario = givenTengoUnUsuario();
        Integer icr = whenSeCalculaSuIngestaCalorica(usuario);
        thenLaIngestaCaloricaEsCorrecta(icr);

    }

    private void thenLaIngestaCaloricaEsCorrecta(Integer icr) {

        assertEquals(1938, icr, 0.0);
    }

    private Integer whenSeCalculaSuIngestaCalorica(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, PesoMetaIncorrectoException {

        if (servicioLogin.validarDatos(usuario)) {
            return servicioUsuario.calcularIngestaCalorica(usuario);

        }
        throw new EdadInvalidaException();
    }


    private Usuario givenTengoUnUsuario() {
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

        assertThrows(EdadInvalidaException.class, () -> {
            whenSeCalculaSuIngestaCalorica(usuario);
        });


    }

    private Usuario givenTengoUnUsuarioConDatosIncompletos() {
        Usuario usuario = new Usuario();
        usuario.setGenero("masculino");
        usuario.setPeso(70.0);
        usuario.setAltura(170.0);
        usuario.setEmail("123@gmail.com");
        usuario.setNombre("Lucas");
        usuario.setPassword("123");
        usuario.setNivelDeActividad("sedentario");
        return usuario;
    }

    @Test
    public void SeGuardanLosMacronutrientesDelUsuario() throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, PesoMetaIncorrectoException {
        Usuario usuario = givenTengoUnUsuario();

        MacronutrientesUsuario macro = whenCalculoLosMacronutrientesDelUsuario(usuario);

        thenMeSeGuardanLosMacronutrientes(usuario, macro);
    }

    private void thenMeSeGuardanLosMacronutrientes(Usuario usuario, MacronutrientesUsuario macro) throws DatosIncorrectos {

        Integer vo1 = macro.getGrasaAConsumir();
        Integer vo2 = macro.getProteinaAConsumir();
        Integer vo3 = macro.getCarbohidratosAConsumir();
        assertNotNull(vo1);
        assertNotNull(vo2);
        assertNotNull(vo3);
    }

    private MacronutrientesUsuario whenCalculoLosMacronutrientesDelUsuario(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, PesoMetaIncorrectoException {
        servicioUsuario.calcularIngestaCalorica(usuario);
        return servicioUsuario.CalcularDistribucionDeMacronutrientes(usuario);
    }

    @Test
    public void obtenerTodoElHistorialDePeso() throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException,
            PesoIncorrectoException, UsuarioNoExistente {
        Usuario usuario = givenTengoUnUsuario();
        List<HistoriaPesoUsuario> historial = givenTengoUnHistorialDePeso(usuario);

        when(repositorioUsuario.buscarUsuario(any(), any())).thenReturn(usuario);
        when(repositorioHistorialPesoUsuario.obtenerHistorialPesoUsuario(any())).thenReturn(historial);

        List<HistoriaPesoUsuario> resultado = servicioUsuario.obtenerTodoElHistorialDePeso(usuario);


        assertEquals(historial.size(), resultado.size());
    }

    private List<HistoriaPesoUsuario> givenTengoUnHistorialDePeso(Usuario usuario) throws DatosIncorrectos {

        List<HistoriaPesoUsuario> historialPeso = new ArrayList<>();

        repositorioHistorialPesoUsuario.agregarPesoYFecha(new HistoriaPesoUsuario(66.9, usuario,new Date(2022, 04, 8)));
        repositorioHistorialPesoUsuario.agregarPesoYFecha(new HistoriaPesoUsuario(66.9, usuario,new Date(2020, 04, 8)));
   return historialPeso;

    }

    @Test
    public void actualizarPesoDeUsuario() throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException,
            PesoIncorrectoException, UsuarioNoExistente, PesoMetaIncorrectoException {
        Usuario usuario = givenTengoUnUsuario();

        when(repositorioUsuario.buscarUsuario(any(), any())).thenReturn(usuario);
        whenLeCambioDePesoAlUsuario(usuario);

        assertEquals(80.0, usuario.getPeso());
    }

    private void whenLeCambioDePesoAlUsuario(Usuario usuario) throws PesoIncorrectoException, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, UsuarioNoExistente, PesoMetaIncorrectoException {
        servicioUsuario.actualizarPeso(usuario, 80.0);
    }
}

