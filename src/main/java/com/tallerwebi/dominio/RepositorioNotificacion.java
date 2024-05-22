package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioNotificacion {

    void guardar(Notificacion notificacion);
    Notificacion buscar(Long id);
    List<Notificacion> buscarTodos();
    void modificar(Notificacion notificacion);
    void borrar(Notificacion notificacion);

}
