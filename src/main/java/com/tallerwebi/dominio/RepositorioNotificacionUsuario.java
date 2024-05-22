package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioNotificacionUsuario {

    void guardar(NotificacionUsuario notificacionUsuario);
    NotificacionUsuario buscar(Long idNotificacion, Long idUsuario);
    List<NotificacionUsuario> buscarTodos();
    List<Notificacion> buscarPorUsuario(Usuario usuario);
    void borrar(NotificacionUsuario notificacionUsuario);

}
