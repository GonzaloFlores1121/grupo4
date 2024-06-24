package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioUsuarioFollow {

    void save(UsuarioFollow follow);

    UsuarioFollow get(Long idUser, Long idFollow);

    List<Usuario> getAllFollows(Long idUser);

    List<Usuario> getAllMyFollows(Long idUser);

    void delete(UsuarioFollow follow);

}
