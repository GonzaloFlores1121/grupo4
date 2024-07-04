package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioDesafio {
    List<Desafio> obtenerTodosDesafios();

    Desafio guardarDesafio(Desafio desafio);

    Desafio obtenerDesafioPorId(Long id);

    void eliminarDesafio(Long id);

    void unirseADesafio(Long usuarioId, Long desafioId);

    void completarDesafio(Long usuarioId, Long desafioId);

    List<DesafioUsuario> obtenerDesafiosPorUsuario(Long usuarioId);

    List<Desafio> obtenerDesafiosEnCurso(Long usuarioIo);

    List<Desafio> obtenerDesafiosCompletados(Long usuarioId);
}