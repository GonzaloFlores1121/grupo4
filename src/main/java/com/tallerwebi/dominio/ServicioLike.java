package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.excepcion.PublicacionNoExistente;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;

public interface ServicioLike {

    void like(Publicacion publicacion, Usuario usuario);

    PublicacionLike obtenerLike(Long idPublication, Long idUser) throws UsuarioNoExistente, PublicacionNoExistente;

    List<Publicacion> obtenerTodosLosLikePorUsuario(Long idUser) throws UsuarioNoExistente;
    
    List<Usuario> obtenerTodosLosLikesPorPublicacion(Long idPublicaction) throws PublicacionNoExistente;

    void unlike(PublicacionLike like);

}
