package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioNotificacionUsuario {

    void save(NotificacionUsuario notificacionUsuario);
    NotificacionUsuario get(Long idNotificacion, Long idUsuario);
    List<NotificacionUsuario> getAll();
    List<Notificacion> getAllNotification(Long idUsuario);
    void update(NotificacionUsuario notificacionUsuario);
    void delete(NotificacionUsuario notificacionUsuario);
    List<NotificacionUsuario> getNotificacionesNoLeidas(Long idUsuario);
}
