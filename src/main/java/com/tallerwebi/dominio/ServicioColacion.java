package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.util.List;

public interface ServicioColacion {

    void guardarColacionUsuario(Alimento alimento, Usuario usuario, int cantidad, TipoColacion tipoColacion, LocalDate fecha, String nombre) throws Exception;
    List<Alimento> obtenerAlimentosPorFechaYUsuarioYTipoColacion(LocalDate fecha, Usuario usuario, TipoColacion tipo);
    void actualizarColacion(Colacion colacion);
    void eliminarColacionUsuario(Alimento alimento, Usuario usuario, TipoColacion value, LocalDate localDate);

    Colacion obtenerColacionPorAlimento(Alimento alimento);
}
