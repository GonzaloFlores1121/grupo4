package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioDesafioUsuario {
    DesafioUsuario guardarDesafioUsuario(DesafioUsuario desafioUsuario);
    DesafioUsuario obtenerDesafioUsuarioPorId(Long id);
    DesafioUsuario obtenerDesafioUsuarioPorUsuarioYDesafio(Long usuarioId, Long desafioId);
    List<DesafioUsuario> obtenerDesafiosPorUsuario(Long usuarioId);
}
