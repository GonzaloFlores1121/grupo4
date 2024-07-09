package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EjercicioUsuario;
import com.tallerwebi.dominio.RepositorioEjercicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

@Repository("repositorioEjercicioUsuario")
@Transactional
public class RepositorioEjercicioUsuarioImpl implements RepositorioEjercicioUsuario {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioEjercicioUsuarioImpl (SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void agregarEjercicio(EjercicioUsuario ejercicioUsuario) {
        Session session = sessionFactory.getCurrentSession();
        session.save(ejercicioUsuario);
    }

    @Override
    public List<EjercicioUsuario> obtenerTodosLosEjercicios(Usuario usuario) {
        Session session = sessionFactory.openSession();
        String hql = "FROM EjercicioUsuario eu WHERE eu.usuario = :usuario";
        Query<EjercicioUsuario> query = session.createQuery(hql, EjercicioUsuario.class);
        query.setParameter("usuario", usuario);
        return query.getResultList();
    }

    @Override
    public EjercicioUsuario obtenerEjercicioPorNombre(String nombre) {
        return (EjercicioUsuario) sessionFactory.getCurrentSession().createCriteria(EjercicioUsuario.class)
                .add(Restrictions.eq("nombre", nombre))
                .uniqueResult();
}

    @Override
    public List<EjercicioUsuario> obtenerEjercicioPorFecha(Usuario usuario, LocalDate fecha) {
        Session session = sessionFactory.openSession();
        String hql = "FROM EjercicioUsuario eu WHERE eu.usuario = :usuario AND eu.fecha = :fecha";
        Query<EjercicioUsuario> query = session.createQuery(hql, EjercicioUsuario.class);
        query.setParameter("usuario", usuario);
        query.setParameter("fecha", fecha);
        return query.getResultList();
    }

    @Override
    public EjercicioUsuario buscarEjercicioUsuarioPorId(Long id) {
        return (EjercicioUsuario) sessionFactory.getCurrentSession().createCriteria(EjercicioUsuario.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public void eliminarEjercicioUsuario(LocalDate fecha, EjercicioUsuario ejercicioUsuario) {
        Transaction transaction = null;
        try (Session session = sessionFactory.openSession()) {
            transaction = session.beginTransaction();

            String hql = "delete from EjercicioUsuario eu WHERE eu.fecha = :fecha AND eu.id = :id";
            Query<?> query = session.createQuery(hql);
            query.setParameter("fecha", fecha);
            query.setParameter("id", ejercicioUsuario.getId());
            query.executeUpdate();

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
    }



}
