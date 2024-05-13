package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EjercicioUsuario;
import com.tallerwebi.dominio.RepositorioEjercicioUsuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

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
}
