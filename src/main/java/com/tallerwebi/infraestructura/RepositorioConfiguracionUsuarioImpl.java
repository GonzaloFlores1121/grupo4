package com.tallerwebi.infraestructura;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.ConfiguracionUsuario;
import com.tallerwebi.dominio.RepositorioConfiguracionUsuario;

@Repository("repositorioConfiguracionUsuario")
@Transactional
public class RepositorioConfiguracionUsuarioImpl implements RepositorioConfiguracionUsuario {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioConfiguracionUsuarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(ConfiguracionUsuario configuracionUsuario) {
        Session session = sessionFactory.getCurrentSession();
        session.save(configuracionUsuario);
    }

    @Override
    public ConfiguracionUsuario buscar(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(ConfiguracionUsuario.class, id);
    }

    @Override
    public void modificar(ConfiguracionUsuario configuracionUsuario) {
        Session session = sessionFactory.getCurrentSession();
        session.save(configuracionUsuario);
    }

}
