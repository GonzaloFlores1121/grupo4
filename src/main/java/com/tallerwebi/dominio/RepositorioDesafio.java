package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioDesafio{
    Desafio guardarDesafio(Desafio desafio);
    Desafio obtenerDesafioPorId(Long id);
    void eliminarDesafio(Long id);
    List<Desafio> obtenerTodosDesafios();
}