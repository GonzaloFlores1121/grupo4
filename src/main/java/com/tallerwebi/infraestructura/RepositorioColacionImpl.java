package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository
@Transactional
public class RepositorioColacionImpl implements RepositorioColacion {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioColacionImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void agregarColacion(Colacion colacion) {
        Session session = sessionFactory.getCurrentSession();
        session.save(colacion);
    }

    @Override
    public List<Colacion> obtenerColacionesPorFechaYUsuarioYTipo(LocalDate fecha, Usuario user, TipoColacion tipo) {
        return sessionFactory.getCurrentSession().createCriteria(Colacion.class)
                .createAlias("alimentos", "alimento")
                .createAlias("usuario", "usuario")
                .add(Restrictions.eq("fecha", fecha))
                .add(Restrictions.eq("usuario.id", user.getId()))
                .add(Restrictions.eq("tipo", tipo)).list();
    }

    @Override
    public List<Colacion> obtenerTodasLasColacionesDelUsuario(Usuario usuario) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Colacion c WHERE c.usuario = :usuario";
        Query<Colacion> query = session.createQuery(hql, Colacion.class);
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }

    @Override
    public List<Colacion> obtenerTodasLasColacionesDelUsuarioPorFecha(Usuario usuario, LocalDate fecha) {
        Session session = sessionFactory.openSession();
        String hql = "FROM Colacion c WHERE c.usuario = :usuario AND c.fecha=:fecha";
        Query<Colacion> query = session.createQuery(hql, Colacion.class);
        query.setParameter("usuario", usuario);
        query.setParameter("fecha", fecha);
        return query.getResultList();
    }

    @Override
    public Colacion obtenerColacionPorId(Long id) {
        Session session = sessionFactory.openSession();
        return (Colacion) sessionFactory.getCurrentSession().createCriteria(Colacion.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

}
