package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;

import java.io.File;
import java.util.List;

public interface ServicioComunidad {




    void subirPublicacion(Usuario usuario, String imagen, String mensaje);

    List<Publicacion> todasLasPublicacionesSubidas();

    List<Publicacion> todasLasPublicacionesSubidasPorUnUsuario(Long id);

    Usuario obtenerUsuarioPorId(Long id) throws UsuarioNoExistente;
}
