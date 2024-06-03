package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Colacion;
import com.tallerwebi.dominio.RepositorioColacion;
import com.tallerwebi.dominio.TipoColacion;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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
        Session session = sessionFactory.getCurrentSession();
        Criteria criteria = session.createCriteria(Colacion.class, "colacion");

        criteria.createAlias("colacion.alimentos", "alimento");
        criteria.createAlias("colacion.usuario", "usuario");

        criteria.add(Restrictions.eq("colacion.fecha", fecha));
        criteria.add(Restrictions.eq("usuario.id", user.getId()));
        criteria.add(Restrictions.eq("colacion.tipo", tipo));

        return criteria.list();
    }
}
