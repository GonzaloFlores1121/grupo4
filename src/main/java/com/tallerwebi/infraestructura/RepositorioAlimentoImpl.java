package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.RepositorioAlimento;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("repositorioAlimento")
@Transactional
public class RepositorioAlimentoImpl implements RepositorioAlimento {

    private final SessionFactory sessionFactory;

    public RepositorioAlimentoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Alimento consultarAlimentoPorID(Long id) {
        Session sesion= sessionFactory.getCurrentSession();
        return sesion.get(Alimento.class, id);
    }

    @Override
    public void update(Alimento alimento) {
        sessionFactory.getCurrentSession().update(alimento);
    }

    @Override
    public List<Alimento> consultarAlimentos() {
        return sessionFactory.getCurrentSession().createQuery("from Alimento", Alimento.class).list();
    }
}
