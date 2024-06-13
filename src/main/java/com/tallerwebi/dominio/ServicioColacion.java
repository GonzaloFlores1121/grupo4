package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.util.List;

public interface ServicioColacion {

    void guardarColacionUsuario(Alimento alimento, Usuario usuario, TipoColacion tipoColacion, LocalDate fecha) throws Exception;
    List<Alimento> obtenerAlimentosPorFechaYUsuarioYTipoColacion(LocalDate fecha, Usuario usuario, TipoColacion tipo);

    void eliminarColacionUsuario(Alimento alimento, Usuario usuario, TipoColacion value, LocalDate localDate);
}
