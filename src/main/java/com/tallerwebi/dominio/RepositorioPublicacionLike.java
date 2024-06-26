package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPublicacionLike {

    void save(PublicacionLike like);

    PublicacionLike getLike(Long idPublication, Long idUser);

    List<Publicacion> getAllUserLikes(Long idUser);

    List<Usuario> getAllPublicationLikes(Long idPublication);

    void delete(PublicacionLike like);

}
