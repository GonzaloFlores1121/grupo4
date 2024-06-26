package com.tallerwebi.infraestructura;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.Publicacion;
import com.tallerwebi.dominio.PublicacionLike;
import com.tallerwebi.dominio.RepositorioPublicacionLike;
import com.tallerwebi.dominio.Usuario;

@Repository("repositorioPublicacionLike")
@Transactional
public class RepositorioPublicacionLikeImpl implements RepositorioPublicacionLike {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioPublicacionLikeImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(PublicacionLike like) {
        Session session = sessionFactory.getCurrentSession(); 
        session.save(like);
    }

    @Override
    public PublicacionLike getLike(Long idPublication, Long idUser) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from PublicacionLike pl where pl.publicacion.id=:idPublication and pl.usuario.id=:idUser", PublicacionLike.class)
                .setParameter("idPublication", idPublication)
                .setParameter("idUser", idUser)
                .uniqueResult();
    }

    @Override
    public List<Publicacion> getAllUserLikes(Long idUser) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select pl.publicacion from PublicacionLike pl where pl.usuario.id=:idUser", Publicacion.class)
                        .setParameter("idUser", idUser)
                        .list();
    }

    @Override
    public List<Usuario> getAllPublicationLikes(Long idPublication) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select pl.usuario from PublicacionLike pl where pl.publicacion.id=:idPublication", Usuario.class)
                        .setParameter("idPublication", idPublication)
                        .list();
    }

    @Override
    public void delete(PublicacionLike like) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(like);
    }

}
