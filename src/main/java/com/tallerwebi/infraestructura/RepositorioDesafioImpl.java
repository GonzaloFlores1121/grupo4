package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Desafio;
import com.tallerwebi.dominio.RepositorioDesafio;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository("repositorioDesafio")
@Transactional
public class RepositorioDesafioImpl implements RepositorioDesafio {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Desafio guardarDesafio(Desafio desafio) {
        if (desafio.getId() == null) {
            entityManager.persist(desafio);
        } else {
            entityManager.merge(desafio);
        }
        return desafio;
    }

    @Override
    public Desafio obtenerDesafioPorId(Long id) {
        return entityManager.find(Desafio.class, id);
    }

    @Override
    public void eliminarDesafio(Long id) {
        Desafio desafio = obtenerDesafioPorId(id);
        if (desafio != null) {
            entityManager.remove(desafio);
        }
    }

    @Override
    public List<Desafio> obtenerTodosDesafios() {
        TypedQuery<Desafio> query = entityManager.createQuery("SELECT d FROM Desafio d", Desafio.class);
        return query.getResultList();
    }
}