package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.sql.Date;
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
    public Colacion obtenerUnaColacionUnica(Alimento alimento, Usuario usuario, TipoColacion tipoColacion, LocalDate fecha) {
        Session session = sessionFactory.getCurrentSession();
        // Buscar la colación existente
        Criteria criteria = session.createCriteria(Colacion.class);
        criteria.add(Restrictions.eq("alimentos", alimento));
        criteria.add(Restrictions.eq("usuario", usuario));
        criteria.add(Restrictions.eq("tipo", tipoColacion));
        criteria.add(Restrictions.eq("fecha", fecha));
        Colacion colacionExistente = (Colacion) criteria.uniqueResult();

        return  colacionExistente;

    }


    @Override
    public void eliminarColacion(Alimento alimento, Usuario usuario, TipoColacion tipoColacion, LocalDate fecha) {
        Session session = sessionFactory.getCurrentSession();

       Colacion colacionExistente= obtenerUnaColacionUnica(alimento, usuario, tipoColacion, fecha);

        if (colacionExistente != null) {
            session.delete(colacionExistente);
        }
    }

    @Override
    public void update(Colacion colacion) {
        Session session = sessionFactory.getCurrentSession();
        session.update(colacion);
    }

    @Override
    public Colacion obtenerColacionPorAlimento(Alimento alimento) {
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Colacion.class);
        criteria.add(Restrictions.eq("alimentos", alimento));
        return (Colacion) criteria.uniqueResult();
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
