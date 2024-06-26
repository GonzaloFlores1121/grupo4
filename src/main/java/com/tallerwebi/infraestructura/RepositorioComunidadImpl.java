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

    private SessionFactory sessionFactory;
    
    @Autowired
    public RepositorioComunidadImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Publicacion> obtenerTodasLasPublicaciones() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Publicacion p JOIN FETCH p.usuario ORDER BY p.fechaHora DESC", Publicacion.class).list();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Publicacion> obtenerTodasLasPublicacionesDeUnUsuario(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p FROM Publicacion p JOIN FETCH p.usuario WHERE p.usuario.id = :id ORDER BY p.fechaHora DESC", Publicacion.class)
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

    @Override
    public Publicacion consultarPublicacion(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Publicacion.class, id);
    }
    
}
