package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.util.List;

public interface RepositorioColacion {
  void  agregarColacion(Colacion colacion);
  List<Colacion> obtenerColacionesPorFechaYUsuarioYTipo(LocalDate fecha, Usuario user, TipoColacion tipo);

    List<Colacion> obtenerTodasLasColacionesDelUsuario(Usuario user);

    List<Colacion> obtenerTodasLasColacionesDelUsuarioPorFecha(Usuario usuario, LocalDate fecha);

  Colacion obtenerColacionPorId(Long id);
}
