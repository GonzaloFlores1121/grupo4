package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioComunidad {

    void guardarPublicacion(Publicacion publicacion);
    void guardarRespuesta(Respuesta respuesta);
    void guardarPublicacionRespuesta(PublicacionRespuesta publicacionRespuesta);
    void guardarPublicacionLike(PublicacionLike publicacionLike);
    void borrarPublicacionLike(PublicacionLike publicacionLike);
    Usuario obtenerUsuarioPorId(Long id);   
    Publicacion obtenerPublicacionPorId(Long id); 
    PublicacionLike obtenerPublicacionLikePorIdPublicacionYUsuario(Long idPublicacion, Long idUsuario);
    List<Publicacion> obtenerPublicaciones();
    List<Publicacion> obtenerPublicacionesPorUsuario(Long idUsuario);
    List<Publicacion> obtenerPublicacionesPorBusqueda(String busqueda);
    List<Respuesta> obtenerRespuestasPorPublicacion(Long idPublicacion);
    List<Publicacion> obtenerPublicacionLikesPorUsuario(Long idUsuario);
    List<Usuario> obtenerPublicacionLikesPorPublicacion(Long idPublicacion);

}
