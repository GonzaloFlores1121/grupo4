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
        Session session = sessionFactory.getCurrentSession();
        session.update(alimento);
    }
    public void save(Alimento alimento) {
        Session session = sessionFactory.getCurrentSession();
        session.save(alimento);
    }

    @Override
    public List<Alimento> consultarAlimentos() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Alimento", Alimento.class).list();
    }

    @Override
    public List<Alimento> consultarAlimentosPorCategoriaYNombre(Long idCategoria, String nombre) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Alimento al where al.categoria.id=:idCategoria and LOWER(al.nombre) like LOWER(:nombre) and al.esPersonalizado = false", Alimento.class)
                .setParameter("idCategoria", idCategoria)
                .setParameter("nombre", "%"+nombre+"%")
                .list();
    }
    
}
