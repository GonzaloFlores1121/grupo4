package com.tallerwebi.infraestructura;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.RepositorioUsuarioFollow;
import com.tallerwebi.dominio.ServicioFollow;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.UsuarioFollow;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;

@Service
public class ServicioFollowImpl implements ServicioFollow {

    private RepositorioUsuario repositorioUsuario;
    private RepositorioUsuarioFollow repositorioUsuarioFollow;

    @Autowired
    public ServicioFollowImpl(RepositorioUsuario repositorioUsuario, RepositorioUsuarioFollow repositorioUsuarioFollow) {
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioUsuarioFollow = repositorioUsuarioFollow;
    }

    @Override
    public void follow(Usuario user, Usuario userFollow) {
        UsuarioFollow follow = new UsuarioFollow();
        follow.setUsuario(user);
        follow.setSeguidor(userFollow);
        repositorioUsuarioFollow.save(follow);
    }

    @Override
    public UsuarioFollow obtenerFollow(Long idUser, Long idFollow) throws UsuarioNoExistente {
        Usuario user = repositorioUsuario.buscarPorId(idUser);
        Usuario userFollow = repositorioUsuario.buscarPorId(idFollow);
        if(user == null || userFollow == null) {throw new UsuarioNoExistente();}
        return repositorioUsuarioFollow.get(idUser, idFollow);
    }

    @Override
    public List<Usuario> obtenerTodosLosFollows(Long idUser) throws UsuarioNoExistente {
        Usuario user = repositorioUsuario.buscarPorId(idUser);
        if(user == null) {throw new UsuarioNoExistente();}
        return repositorioUsuarioFollow.getAllFollows(idUser);
    }

    @Override
    public List<Usuario> obtenerTodosMisFollows(Long idUser) throws UsuarioNoExistente {
        Usuario user = repositorioUsuario.buscarPorId(idUser);
        if(user == null) {throw new UsuarioNoExistente();}
        return repositorioUsuarioFollow.getAllMyFollows(idUser);
    }

    @Override
    public void unfollow(UsuarioFollow follow) {
        repositorioUsuarioFollow.delete(follow);
    }

}
