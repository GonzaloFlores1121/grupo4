package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.HistoriaPesoUsuario;
import com.tallerwebi.dominio.RepositorioHistorialPesoUsuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("RepositorioHistorialPesoUsuario")
public class RepositorioHistorialPesoUsuarioImpl implements RepositorioHistorialPesoUsuario {
    SessionFactory sessionFactory;

    public RepositorioHistorialPesoUsuarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void agregarPesoYFecha(HistoriaPesoUsuario nuevoPeso) {
        Session session=sessionFactory.getCurrentSession();
        session.save(nuevoPeso);
    }

    @Override
    public List<HistoriaPesoUsuario> obtenerHistorialPesoUsuario() {
        Session session = sessionFactory.openSession();
        String sql = "FROM HistoriaPesoUsuario ";
        Query<HistoriaPesoUsuario> query = session.createQuery(sql, HistoriaPesoUsuario.class);
        return query.getResultList();
    }
}
