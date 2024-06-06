package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository("repositorioRecetaFavorito")
@Transactional
public class RepositorioRecetaFavoritoImpl implements RepositorioRecetaFavorito {

    private final SessionFactory sessionFactory;

    public RepositorioRecetaFavoritoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<RecetaFavorito> recetasFavoritos() {
        Session session = sessionFactory.openSession();
        String hql = "FROM RecetaFavorito";
        Query<RecetaFavorito> query = session.createQuery(hql, RecetaFavorito.class);
        return query.getResultList();
    }

    @Override
    public void agregarRecetaFavorito(RecetaFavorito receta) {
        sessionFactory.getCurrentSession().save(receta);
    }


    @Override
    public RecetaFavorito buscarPorUsuario(Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        RecetaFavorito recetaFavorito = (RecetaFavorito) session.createQuery(
                "SELECT r FROM RecetaFavorito r WHERE r.usuario = :usuario").setParameter("usuario", usuario).uniqueResult();
        return recetaFavorito;
    }

    @Override
    public void eliminarRecetaFavorito(RecetaFavorito recetaFavorito, Usuario usuario) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "DELETE FROM RecetaFavorito rf WHERE rf.id = :id AND rf.usuario.id = :usuarioId";
        int result = session.createQuery(hql)
                .setParameter("id", recetaFavorito.getId())
                .setParameter("usuarioId", usuario.getId())
                .executeUpdate();

        if (result == 0) {
            throw new IllegalStateException("Failed to delete favorite recipe with id: " + recetaFavorito.getId() + " for user: " + usuario.getId());
        }
    }

    @Override
    public RecetaFavorito buscarPorUsuarioYReceta(Usuario usuario, Receta receta) {
        Session session = sessionFactory.getCurrentSession();
        RecetaFavorito recetaFavorito = (RecetaFavorito) session.createQuery(
                        "SELECT r FROM RecetaFavorito r JOIN r.recetasFavoritas rec WHERE r.usuario = :usuario AND rec = :receta")
                .setParameter("usuario", usuario)
                .setParameter("receta", receta)
                .uniqueResult();
        return recetaFavorito;
    }

    public List<RecetaFavorito> obtenerRecetasFavoritas() {
        Session session = sessionFactory.openSession();
        String hql = "FROM RecetaFavorito";
        Query<RecetaFavorito> query = session.createQuery(hql, RecetaFavorito.class);
        return query.getResultList();
    }

    @Override
    public RecetaFavorito buscarRecetaFavorita(Long id) {
        Session session = sessionFactory.getCurrentSession();
        RecetaFavorito recetaFavorito = (RecetaFavorito) session.createQuery(
                        "SELECT r FROM RecetaFavorito r JOIN r.recetasFavoritas rec WHERE r.id = :id")
                .setParameter("id", id)
                .uniqueResult();
        return recetaFavorito;
    }
}