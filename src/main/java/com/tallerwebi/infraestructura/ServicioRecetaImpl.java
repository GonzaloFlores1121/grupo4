package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.RecetaNoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("servicioReceta")
@Transactional
public class ServicioRecetaImpl implements ServicioReceta {

    private RepositorioReceta repositorioRecetas;
    private RepositorioRecetaFavorito repositorioRecetaFavorito;

    @Autowired
    public ServicioRecetaImpl(RepositorioReceta repositorioRecetas, RepositorioRecetaFavorito repositorioRecetaFavorito) {
        this.repositorioRecetas = repositorioRecetas;
        this.repositorioRecetaFavorito = repositorioRecetaFavorito;
    }

    @Override
    public List<Receta> obtenerTodasLasRecetas() {
        return repositorioRecetas.obtenerTodasLasRecetas();
    }

    @Override
    public Receta obtenerRecetaPorId(Long id) throws RecetaNoEncontradaException {
        Receta receta = repositorioRecetas.consultarReceta(id);
        if (receta == null) {
            throw new RecetaNoEncontradaException();
        }
        return receta;
    }

    @Override
    public void agregarRecetaFavorita(Usuario usuario, Receta recetaAAgregar) {
        RecetaFavorito recetaFavorito = repositorioRecetaFavorito.buscarPorUsuarioYReceta(usuario, recetaAAgregar);

        if (recetaFavorito == null) {
            recetaFavorito = new RecetaFavorito();
            recetaFavorito.setUsuario(usuario);
            recetaFavorito.setRecetasFavoritas(recetaAAgregar);
            repositorioRecetaFavorito.agregarRecetaFavorito(recetaFavorito);
        } else {
            repositorioRecetaFavorito.eliminarRecetaFavorito(recetaFavorito);
        }
    }
}
