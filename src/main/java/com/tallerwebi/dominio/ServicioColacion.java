package com.tallerwebi.dominio;

import java.time.LocalDate;
import java.util.List;

public interface ServicioColacion {

    void guardarColacionUsuarioDewsdeDiarioAlimentos(Alimento alimento, Usuario usuario, int cantidad, TipoColacion tipoColacion, LocalDate fecha, String nombre) throws Exception;

    void guardarColacionUsuario(Alimento alimento, Usuario usuario, int cantidad, TipoColacion tipoColacion, LocalDate fecha, String nombre) throws Exception;
    List<Alimento> obtenerAlimentosPorFechaYUsuarioYTipoColacion(LocalDate fecha, Usuario usuario, TipoColacion tipo);
    void actualizarColacion(Colacion colacion);

    List<Alimento> listarALimentosRecientementeConsumidosPorElUsuario(Usuario usuario);

    void eliminarColacionUsuario(Alimento alimento, Usuario usuario, TipoColacion value, LocalDate localDate);

    Colacion obtenerColacionPorAlimento(Alimento alimento);
    List<Colacion> obtenerTodasLasColacionesDelUsuario(Usuario usuario);
    List<Colacion> obtenerColacionesDelUsuarioPOrFecha(Usuario usuario,LocalDate fecha);
    Colacion obtenerColacionPorId(Long id);

    Integer obtenerCaloriasTotalesDeAlimentosPorUsuarioYFecha(Usuario usuario, LocalDate fecha);
}
