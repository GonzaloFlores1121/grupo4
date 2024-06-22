package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository("repositorioComunidad")
public class RepositorioComunidadImpl implements RepositorioComunidad {

SessionFactory sessionFactory;
    @Autowired
public RepositorioComunidadImpl(SessionFactory sessionFactory) {
    this.sessionFactory = sessionFactory;
}

    @Override
    @Transactional(readOnly = true)
    public List<Publicacion> obtenerTodasLasPublicaciones() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Publicacion p JOIN FETCH p.usuario", Publicacion.class).list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Publicacion> obtenerTodasLasPublicacionesDeUnUsuario(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Publicacion p JOIN FETCH p.usuario WHERE p.usuario.id = :id", Publicacion.class)
                .setParameter("id", id)
                .list();
    }
    @Transactional
    @Override
    public void guardarPublicacion(Publicacion publicacion) {
        Session session = sessionFactory.getCurrentSession();
        session.save(publicacion);
    }

    @Override
    public Usuario consultarUsuario(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Usuario.class, id);
    }
}
