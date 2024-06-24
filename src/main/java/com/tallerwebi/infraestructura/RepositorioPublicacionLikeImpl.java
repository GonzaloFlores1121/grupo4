package com.tallerwebi.infraestructura;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.PublicacionLike;
import com.tallerwebi.dominio.RepositorioPublicacionLike;

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
    public List<PublicacionLike> getAllUserLikes(Long idUser) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from PublicacionLike pl where pl.usuario.id=:idUser", PublicacionLike.class)
                        .setParameter("idUser", idUser)
                        .list();
    }

    @Override
    public List<PublicacionLike> getAllPublicationLikes(Long idPublication) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from PublicacionLike pl where pl.publicacion.id=:idPublication", PublicacionLike.class)
                        .setParameter("idPublication", idPublication)
                        .list();
    }

    @Override
    public void delete(PublicacionLike like) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(like);
    }

}
