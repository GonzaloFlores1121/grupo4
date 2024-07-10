package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

import java.time.LocalDate;
import java.util.List;

public interface ServicioColacion {

    void guardarColacionUsuario(Alimento alimento, Usuario usuario, int cantidad, TipoColacion tipoColacion, LocalDate fecha, String nombre) throws Exception;
    List<Alimento> obtenerAlimentosPorFechaYUsuarioYTipoColacion(LocalDate fecha, Usuario usuario, TipoColacion tipo);
    void actualizarColacion(Colacion colacion);
    void eliminarColacionUsuario(Alimento alimento, Usuario usuario, TipoColacion value, LocalDate localDate);

    Colacion obtenerColacionPorAlimento(Alimento alimento);
    List<Colacion> obtenerTodasLasColacionesDelUsuario(Usuario usuario);
    List<Colacion> obtenerColacionesDelUsuarioPOrFecha(Usuario usuario,LocalDate fecha) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, UsuarioNoExistente, PesoIncorrectoException, EjercicioNoExistente;
    Colacion obtenerColacionPorId(Long id);

    Integer obtenerCaloriasTotalesDeAlimentosPorUsuarioYFecha(Usuario usuario, LocalDate fecha) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, UsuarioNoExistente, PesoIncorrectoException, EjercicioNoExistente;
}
