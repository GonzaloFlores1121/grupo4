package com.tallerwebi.dominio;

import java.time.LocalDateTime;
import java.util.List;

import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;

public interface ServicioNotificacion {

    Notificacion crearNotificacion(String titulo, String contenido);
    void enviarNotificacion(Notificacion notificacion, LocalDateTime fechaHora, String email) throws UsuarioNoExistente;
    void enviarNotificaciones(Notificacion notificacion, LocalDateTime fechaHora);
    void eliminarNotificacion(Long idNotificacion, Long idUsuario);
    void eliminarNotificaciones(LocalDateTime fechaHora);
    List<Notificacion> obtenerNotificacionesPorUsuario(Usuario usuario);

}
