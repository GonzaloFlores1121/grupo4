package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioComunidad {
      List<Publicacion> obtenerTodasLasPublicaciones();
    void guardarPublicacion(Publicacion publicacion);
}
