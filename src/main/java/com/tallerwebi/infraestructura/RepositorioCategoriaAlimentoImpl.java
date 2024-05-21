package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.CategoriaAlimento;
import com.tallerwebi.dominio.RepositorioCategoriaAlimento;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("repositorioCategoriaAlimento")
@Transactional
public class RepositorioCategoriaAlimentoImpl implements RepositorioCategoriaAlimento {

    private final SessionFactory sessionFactory;

    public RepositorioCategoriaAlimentoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Override
    public List<CategoriaAlimento> obtenerTodasLasCateogorias() {
       Session sesion= sessionFactory.getCurrentSession();
        return sesion.createQuery("from CategoriaAlimento", CategoriaAlimento.class).list();
    }

    @Override
    public CategoriaAlimento obtenerCategoriaPorId(Long id) {
        Session sesion= sessionFactory.getCurrentSession();
    return sesion.get(CategoriaAlimento.class, id);
    }
}
