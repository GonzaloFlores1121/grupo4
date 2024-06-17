package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.RepositorioEjercicio;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("repositorioEjercicio")
@Transactional
public class RepositorioEjercicioImpl implements RepositorioEjercicio {

    private final SessionFactory sessionFactory;


    public RepositorioEjercicioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<Ejercicio> obtenerTodosLosEjercicios() {
       Session session = sessionFactory.openSession();
            String hql = "FROM Ejercicio";
            Query<Ejercicio> query = session.createQuery(hql, Ejercicio.class);
            return query.getResultList();

    }

    @Override
    public Ejercicio obtenerEjercicioPorId(Long id) {
       return (Ejercicio) sessionFactory.getCurrentSession().createCriteria(Ejercicio.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }


    @Override
    public List obtenerEjercicioPorNombreOIntensidad(String caracteristica) {
        return sessionFactory.getCurrentSession().createCriteria(Ejercicio.class)
                .add(Restrictions.or(
                        Restrictions.ilike("nombre", caracteristica),
                        Restrictions.ilike("intensidad", caracteristica)
                ))
                .list();
    }




}


