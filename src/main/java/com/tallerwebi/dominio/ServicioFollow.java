package com.tallerwebi.dominio;

import java.util.List;

import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;

public interface ServicioFollow {

    void follow(Usuario user, Usuario userFollow);

    UsuarioFollow obtenerFollow(Long idUser, Long idFollow) throws UsuarioNoExistente;

    List<Usuario> obtenerTodosLosFollows(Long idUser) throws UsuarioNoExistente;

    List<Usuario> obtenerTodosMisFollows(Long idUser) throws UsuarioNoExistente;

    void unfollow(UsuarioFollow follow);

}
