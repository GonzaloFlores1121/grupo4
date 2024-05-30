package com.tallerwebi.infraestructura;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.RepositorioNotificacion;

@Repository("repositorioNotificacion")
@Transactional
public class RepositorioNotificacionImpl implements RepositorioNotificacion {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioNotificacionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(Notificacion notificacion) {
        Session session = sessionFactory.getCurrentSession();
        session.save(notificacion);
    }

    @Override
    public Notificacion get(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Notificacion.class, id);
    }

    @Override
    public List<Notificacion> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Notificacion", Notificacion.class).list();
    }

    @Override
    public void update(Notificacion notificacion) {
        Session session = sessionFactory.getCurrentSession();
        session.update(notificacion);
    }

    @Override
    public void delete(Notificacion notificacion) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(notificacion);
    }

}
