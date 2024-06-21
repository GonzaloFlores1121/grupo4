package com.tallerwebi.dominio;

import java.io.File;
import java.util.List;

public interface ServicioComunidad {




    void subirPublicacion(Usuario usuario, String imagen, String mensaje);

    List<Publicacion> todasLasPublicacionesSubidas();
}
