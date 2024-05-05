package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.infraestructura.ServicioDatosUsuarioImpl;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ServicioDatosNutricionalesTest {

    private ServicioDatosUsuario servicioUsuario = new ServicioDatosUsuarioImpl();

    @Test
    public void IngestaCaloricaDeUsuarioHombreDe40SedentarioYTiene70kgYMide170() throws  DatosIncorrectos {
        Usuario usuario = givenTengoUnUsuario();
        Integer icr = whenSeCalculaSuIngestaCalorica(usuario);
        thenLaIngestaCaloricaEsCorrecta(icr );

    }

    private void thenLaIngestaCaloricaEsCorrecta(Integer icr) {

        assertEquals(1938, icr,0.0);
    }

    private Integer whenSeCalculaSuIngestaCalorica(Usuario usuario) throws  DatosIncorrectos {
        // Verifica si los datos del usuario son válidos antes de calcular la ingesta calórica
        if (!servicioUsuario.usuarioDatosCorrecto(usuario)) {
            throw new DatosIncorrectos("Datos incorrectos del usuario");
        }
        return servicioUsuario.calcularIngestaCalorica(usuario);
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

}

