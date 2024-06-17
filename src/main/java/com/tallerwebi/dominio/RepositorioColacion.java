package com.tallerwebi.dominio;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface RepositorioColacion {
  void  agregarColacion(Colacion colacion);
  List<Colacion> obtenerColacionesPorFechaYUsuarioYTipo(LocalDate fecha, Usuario user, TipoColacion tipo);
     Colacion obtenerUnaColacionUnica(Alimento alimento, Usuario usuario, TipoColacion tipoColacion, LocalDate fecha);
    void eliminarColacion(Alimento alimento, Usuario usuario, TipoColacion tipoColacion, LocalDate fecha);


    void update(Colacion colacion);

    Colacion obtenerColacionPorAlimento(Alimento alimento);

    List<Colacion> obtenerTodasLasColacionesDelUsuario(Usuario user);

    List<Colacion> obtenerTodasLasColacionesDelUsuarioPorFecha(Usuario usuario, LocalDate fecha);

  Colacion obtenerColacionPorId(Long id);

}
