package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioPago;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository("repositorioPago")
@Transactional
public class RepositorioPagoImpl implements RepositorioPago {
    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioPagoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Usuario obtenerUsuario(String email) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Usuario u where email=:email", Usuario.class).setParameter("email", email).uniqueResult();
    }

    @Override
    public void save(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        session.save(usuario);
    }
}
