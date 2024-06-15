package com.tallerwebi.dominio;
import com.tallerwebi.infraestructura.ServicioColacionImpl;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class ServicioColacionImplTest {
 /*   @Test
    public void testGuardarColacionUsuario() throws Exception {
        RepositorioColacion repositorioColacion = Mockito.mock(RepositorioColacion.class);

        ServicioColacionImpl servicioColacion = new ServicioColacionImpl(repositorioColacion);
    int cantidad=1;

        Alimento alimento = new Alimento();
        alimento.setId(1L);
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        TipoColacion tipoColacion = TipoColacion.DESAYUNO;
        LocalDate fecha = LocalDate.now();
String nombre="asd";
        servicioColacion.guardarColacionUsuario(alimento, usuario,cantidad, tipoColacion ,fecha, nombre);


        Mockito.verify(repositorioColacion, times(1)).agregarColacion(any(Colacion.class));
    }
    @Test
    public void testObtenerAlimentosPorFechaYUsuarioYTipoColacionDesayuno() {
        RepositorioColacion repositorioColacion = Mockito.mock(RepositorioColacion.class);

        ServicioColacionImpl servicioColacion = new ServicioColacionImpl(repositorioColacion);

        Alimento alimento = new Alimento();
        alimento.setId(1L);
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        TipoColacion tipoColacion = TipoColacion.DESAYUNO;
        LocalDate fecha = LocalDate.now();

        Colacion colacion = new Colacion();
        colacion.setAlimentos(alimento);
        colacion.setUsuario(usuario);
        colacion.setTipo(tipoColacion);
        colacion.setFecha(fecha);

        when(repositorioColacion.obtenerColacionesPorFechaYUsuarioYTipo(fecha, usuario, tipoColacion)).thenReturn(Arrays.asList(colacion));

        List<Alimento> alimentos = servicioColacion.obtenerAlimentosPorFechaYUsuarioYTipoColacion(fecha, usuario, tipoColacion);

        for (Alimento a : alimentos) {
            System.out.println(alimento.getId());
        }
        assertEquals(1, alimentos.size());
        assertEquals(alimento.getId(), alimentos.get(0).getId());

    }
    */
}


