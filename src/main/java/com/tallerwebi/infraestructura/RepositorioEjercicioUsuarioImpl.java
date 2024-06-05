package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.EjercicioUsuario;
import com.tallerwebi.dominio.RepositorioEjercicioUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
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


}
