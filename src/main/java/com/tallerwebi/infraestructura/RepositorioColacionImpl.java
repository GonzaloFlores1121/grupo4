package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.Colacion;
import com.tallerwebi.dominio.RepositorioColacion;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("repositorioColacion")
@Transactional
public class RepositorioColacionImpl implements RepositorioColacion {
    private final SessionFactory sessionFactory;

    public RepositorioColacionImpl(SessionFactory sessionFactory) { this.sessionFactory = sessionFactory; }


    @Override
    public void update(Colacion colacion) {
        sessionFactory.getCurrentSession().update(colacion);
    }

    @Override
    public Colacion buscarPorId(Long id) {
        Session sesion= sessionFactory.getCurrentSession();
        return sesion.get(Colacion.class, id);
    }

    @Override
    public List<Colacion> listar() {
        return sessionFactory.getCurrentSession().createQuery("from Colacion", Colacion.class).list();
    }
}
