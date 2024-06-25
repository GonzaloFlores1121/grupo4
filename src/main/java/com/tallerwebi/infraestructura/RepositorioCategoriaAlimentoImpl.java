package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.CategoriaAlimento;
import com.tallerwebi.dominio.RepositorioCategoriaAlimento;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("repositorioCategoriaAlimento")
@Transactional
public class RepositorioCategoriaAlimentoImpl implements RepositorioCategoriaAlimento {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioCategoriaAlimentoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public CategoriaAlimento obtenerCategoriaPorId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(CategoriaAlimento.class, id);
    }

    @Override
    public List<CategoriaAlimento> obtenerTodasLasCateogorias() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from CategoriaAlimento", CategoriaAlimento.class).list();
    }

    @Override
    public List<CategoriaAlimento> obtenerCategoriasPorNombre(String nombre) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from CategoriaAlimento where LOWER(nombre) like LOWER(:nombre)", CategoriaAlimento.class).setParameter("nombre", "%"+nombre+"%").list();
    } 

}
