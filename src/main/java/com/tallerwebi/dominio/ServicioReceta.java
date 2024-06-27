package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.RecetaNoEncontradaException;
import com.tallerwebi.dominio.excepcion.RecetaYaAgregadaException;
import com.tallerwebi.dominio.excepcion.RecetaYaEliminadaException;

import javax.transaction.Transactional;
import java.util.List;

public interface ServicioReceta {
    List<Receta> obtenerTodasLasRecetas();
    Receta obtenerRecetaPorId(Long id) throws RecetaNoEncontradaException;
    RecetaFavorito obtenerRecetaFavoritaPorId(Long id) throws RecetaNoEncontradaException;
    void agregarRecetaFavorita(Usuario usuario, Receta recetaAAgregar);
    List<RecetaFavorito> obtenerRecetasFavoritas(Usuario usuario);
    void eliminarReceta(Usuario usuario, RecetaFavorito receta) throws RecetaNoEncontradaException;
}