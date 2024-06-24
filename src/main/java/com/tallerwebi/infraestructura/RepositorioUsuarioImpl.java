package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioUsuarioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Usuario buscarUsuario(String email, String password) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Usuario u where email=:email and password=:password", Usuario.class)
                                    .setParameter("email", email)
                                    .setParameter("password", password)
                                    .uniqueResult();
    }

    @Override
    public void guardar(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        session.save(usuario);
    }

    @Override
    public void modificar(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        session.update(usuario);
    }    

    @Override
    public void eliminar(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(usuario);
    }

    @Override
    public Usuario buscarPorId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Usuario.class, id);
    }

    @Override
    public Usuario buscarPorEmail(String email) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Usuario u where email=:email", Usuario.class).setParameter("email", email).uniqueResult();
    }

    @Override
    public List<Usuario> obtenerTodos() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Usuario", Usuario.class).list();
    }

    @Override
    public void agregarPesoInicial(Double peso, Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();

           session.update(usuario);

       }
    }


