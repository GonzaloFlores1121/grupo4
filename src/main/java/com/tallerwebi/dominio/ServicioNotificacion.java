package com.tallerwebi.dominio;

import java.time.LocalDateTime;
import java.util.List;

import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;

public interface ServicioNotificacion {

    Notificacion crearNotificacion(String titulo, String contenido);
    NotificacionUsuario crearNotificacionUsuario(Notificacion notificacion, Usuario usuario, LocalDateTime fechaHora);
    void enviarNotificacion(String titulo, String contenido, LocalDateTime fechaHora, Long idUsuario) throws UsuarioNoExistente;
    void enviarNotificaciones(String titulo, String contenido, LocalDateTime fechaHora);
    void eliminarNotificacion(Long idNotificacion, Long idUsuario);
    void eliminarNotificaciones(LocalDateTime fechaHora);

    List<NotificacionUsuario> obtenerNotificacionesNoLeidas(Long idUsuario);

    List<Notificacion> obtenerNotificaciones(Long idUsuario);

}
