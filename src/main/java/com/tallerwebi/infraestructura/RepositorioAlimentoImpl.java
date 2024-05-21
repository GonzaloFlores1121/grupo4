package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.RepositorioAlimento;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

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
}
