package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioNotificacion {

    void save(Notificacion notificacion);
    Notificacion get(Long id);
    List<Notificacion> getAll();
    void update(Notificacion notificacion);
    void delete(Notificacion notificacion);

}
