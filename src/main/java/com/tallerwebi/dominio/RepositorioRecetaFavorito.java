package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioRecetaFavorito {
    List<RecetaFavorito> recetasFavoritos();
    void agregarRecetaFavorito(RecetaFavorito receta);
    RecetaFavorito buscarPorUsuario(Usuario usuario);
    void eliminarRecetaFavorito(RecetaFavorito receta, Usuario usuario);
    RecetaFavorito buscarPorUsuarioYReceta(Usuario usuario, Receta receta);
    List<RecetaFavorito> obtenerRecetasFavoritas();
    RecetaFavorito buscarRecetaFavorita(Long id);
}