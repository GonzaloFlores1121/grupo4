package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.excepcion.PublicacionNoExistente;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;

public interface ServicioLike {

    void like(Publicacion publicacion, Usuario usuario);

    List<PublicacionLike> obtenerTodosLosLikePorUsuario(Long idUser) throws UsuarioNoExistente;
    
    List<PublicacionLike> obtenerTodosLosLikesPorPublicacion(Long idPublicaction) throws PublicacionNoExistente;

    void unlike(PublicacionLike like);

}
