package com.tallerwebi.dominio;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface RepositorioComunidad {
    List<Publicacion> obtenerTodasLasPublicaciones();

    @Transactional(readOnly = true)
    List<Publicacion> obtenerTodasLasPublicacionesDeUnUsuario(Long id);

    void guardarPublicacion(Publicacion publicacion);
    Usuario consultarUsuario(Long id);
}
