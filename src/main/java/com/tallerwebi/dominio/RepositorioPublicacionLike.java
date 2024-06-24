package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPublicacionLike {

    void save(PublicacionLike like);

    List<PublicacionLike> getAllUserLikes(Long idUser);

    List<PublicacionLike> getAllPublicationLikes(Long idPublication);

    void delete(PublicacionLike like);

}
