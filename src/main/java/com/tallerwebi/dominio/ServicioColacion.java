package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.util.List;

public interface ServicioColacion {

    void guardarColacionUsuario(Alimento alimento, Usuario usuario, TipoColacion tipoColacion, LocalDate fecha) throws Exception;
    List<Alimento> obtenerAlimentosPorFechaYUsuarioYTipoColacion(LocalDate fecha, Usuario usuario, TipoColacion tipo);
    List<Colacion> obtenerTodasLasColacionesDelUsuario(Usuario usuario);
    List<Colacion> obtenerColacionesDelUsuarioPOrFecha(Usuario usuario,LocalDate fecha);
    Colacion obtenerColacionPorId(Long id);
}
