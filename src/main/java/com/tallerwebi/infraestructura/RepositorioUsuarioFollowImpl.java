package com.tallerwebi.infraestructura;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.RepositorioUsuarioFollow;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.UsuarioFollow;

@Repository("repositorioUsuarioFollow")
@Transactional
public class RepositorioUsuarioFollowImpl implements RepositorioUsuarioFollow {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioFollowImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(UsuarioFollow follow) {
        Session session = sessionFactory.getCurrentSession();
        session.save(follow);
    }

    @Override
    public UsuarioFollow get(Long idUser, Long idFollow) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from UsuarioFollow uf where uf.usuario.id=:idUser and uf.seguidor.id=:idFollow", UsuarioFollow.class)
                                    .setParameter("idUser", idUser)               
                                    .setParameter("idFollow", idFollow)
                                    .uniqueResult();
    }

    @Override
    public List<Usuario> getAllFollows(Long idUser) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select uf.seguidor from UsuarioFollow uf where uf.usuario.id=:idUser", Usuario.class)
                        .setParameter("idUser", idUser)
                        .list();
    }

    @Override
    public List<Usuario> getAllMyFollows(Long idUser) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select uf.usuario from UsuarioFollow uf where uf.seguidor.id=:idUser", Usuario.class)
                        .setParameter("idUser", idUser)
                        .list();
    }

    @Override
    public void delete(UsuarioFollow follow) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(follow);
    }

}
