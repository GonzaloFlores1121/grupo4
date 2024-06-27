package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioReceta;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
@Repository("repositorioReceta")
@Transactional
public class RepositorioRecetaImpl implements RepositorioReceta {
    private final SessionFactory sessionFactory;

    public RepositorioRecetaImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Receta> obtenerTodasLasRecetas() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Receta", Receta.class).list();
    }

    @Override
    public Receta consultarReceta(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Receta.class, id);
    }
}
