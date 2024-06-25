package com.tallerwebi.infraestructura;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.Publicacion;
import com.tallerwebi.dominio.PublicacionLike;
import com.tallerwebi.dominio.RepositorioComunidad;
import com.tallerwebi.dominio.RepositorioPublicacionLike;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioLike;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.PublicacionNoExistente;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;

@Service
public class ServicioLikeImpl implements ServicioLike {

    private RepositorioComunidad repositorioComunidad;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioPublicacionLike repositorioPublicacionLike;

    @Autowired
    public ServicioLikeImpl(RepositorioComunidad repositorioComunidad, RepositorioUsuario repositorioUsuario, RepositorioPublicacionLike repositorioPublicacionLike) {
        this.repositorioComunidad = repositorioComunidad;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioPublicacionLike = repositorioPublicacionLike;
    }

    @Override
    public void like(Publicacion publicacion, Usuario usuario) {
        PublicacionLike like = new PublicacionLike();
        like.setPublicacion(publicacion);
        like.setUsuario(usuario);
        repositorioPublicacionLike.save(like);
    }

    @Override
    public PublicacionLike obtenerLike(Long idPublication, Long idUser) throws UsuarioNoExistente, PublicacionNoExistente {
        Usuario user = repositorioComunidad.consultarUsuario(idUser);
        if(user == null) {throw new UsuarioNoExistente();}
        Publicacion publication = repositorioComunidad.consultarPublicacion(idPublication);
        if(publication == null) {throw new PublicacionNoExistente();}
        return repositorioPublicacionLike.getLike(idPublication, idUser);
    }

    @Override
    public List<PublicacionLike> obtenerTodosLosLikePorUsuario(Long idUser) throws UsuarioNoExistente {
        Usuario user = repositorioUsuario.buscarPorId(idUser);
        if(user == null) {throw new UsuarioNoExistente();}
        return repositorioPublicacionLike.getAllUserLikes(idUser);
    }

    @Override
    public List<PublicacionLike> obtenerTodosLosLikesPorPublicacion(Long idPublicaction) throws PublicacionNoExistente {
        Publicacion publication = repositorioComunidad.consultarPublicacion(idPublicaction);
        if(publication == null) {throw new PublicacionNoExistente();}
        return repositorioPublicacionLike.getAllPublicationLikes(idPublicaction);
    }

    @Override
    public void unlike(PublicacionLike like) {
        repositorioPublicacionLike.delete(like);
    }

}
