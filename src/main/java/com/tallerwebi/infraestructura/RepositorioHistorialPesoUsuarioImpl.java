package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.HistoriaPesoUsuario;
import com.tallerwebi.dominio.RepositorioHistorialPesoUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.util.List;

@Repository("RepositorioHistoriaPesoUsuario")
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
    public List<HistoriaPesoUsuario> obtenerHistorialPesoUsuario(Usuario usuario) {
        Session session = sessionFactory.openSession();
        String sql = "FROM HistoriaPesoUsuario WHERE usuario.id = :usuarioId";
        Query<HistoriaPesoUsuario> query = session.createQuery(sql, HistoriaPesoUsuario.class);
        query.setParameter("usuarioId", usuario.getId());
        return query.getResultList();
    }
    @Override
    public HistoriaPesoUsuario obtenerHistorialPesoUsuarioParaUnaFecha(Date fecha) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM HistoriaPesoUsuario WHERE fecha = :fecha";
        Query<HistoriaPesoUsuario> query = session.createQuery(hql, HistoriaPesoUsuario.class);
        query.setParameter("fecha", fecha);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void modificarPeso(Double peso, Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "UPDATE Usuario SET peso = :peso WHERE email = :email";
        session.createQuery(hql)
                .setParameter("peso", peso)
                .setParameter("email", usuario.getEmail())
                .executeUpdate();
    }

    @Override
    public void actualizarMiPesoAgregado(HistoriaPesoUsuario historial) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "UPDATE HistoriaPesoUsuario SET peso = :peso WHERE id = :id";
        session.createQuery(sql)
                .setParameter("peso", historial.getPeso())
                .setParameter("id", historial.getId())
                .executeUpdate();
    }

    @Override
    public void actualizarMiIcr(Usuario usuario, Integer icr) {
        Session session = sessionFactory.getCurrentSession();
        String sql = "UPDATE Usuario SET ingestaCalorica = :icr WHERE id = :id";
        session.createQuery(sql)
                .setParameter("icr", usuario.getIngestaCalorica())
                .setParameter("id", usuario.getId())
                .executeUpdate();
    }
}
