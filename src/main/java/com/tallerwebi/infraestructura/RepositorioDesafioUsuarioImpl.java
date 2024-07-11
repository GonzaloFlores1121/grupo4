package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.DesafioUsuario;
import com.tallerwebi.dominio.RepositorioDesafioUsuario;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository("repositorioDesafioUsuario")
@Transactional
public class RepositorioDesafioUsuarioImpl implements RepositorioDesafioUsuario {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public DesafioUsuario guardarDesafioUsuario( DesafioUsuario desafioUsuario) {
        if (desafioUsuario.getId() == null) {
            entityManager.persist(desafioUsuario);
        } else {
            entityManager.merge(desafioUsuario);
        }
        return desafioUsuario;
    }

    @Override
    public DesafioUsuario obtenerDesafioUsuarioPorId(Long id) {
        return entityManager.find(DesafioUsuario.class, id);
    }
    @Override
    public DesafioUsuario obtenerDesafioUsuarioPorUsuarioYDesafio(Long usuarioId, Long desafioId) {
        TypedQuery<DesafioUsuario> query = entityManager.createQuery(
                "SELECT du FROM DesafioUsuario du WHERE du.usuario.id = :usuarioId AND du.desafio.id = :desafioId", DesafioUsuario.class);
        query.setParameter("usuarioId", usuarioId);
        query.setParameter("desafioId", desafioId);
        return query.getSingleResult();
    }

    @Override
    public List<DesafioUsuario> obtenerDesafiosPorUsuario(Long usuarioId) {
        TypedQuery<DesafioUsuario> query = entityManager.createQuery(
                "SELECT du FROM DesafioUsuario du WHERE du.usuario.id = :usuarioId", DesafioUsuario.class);
        query.setParameter("usuarioId", usuarioId);
        return query.getResultList();
    }
}