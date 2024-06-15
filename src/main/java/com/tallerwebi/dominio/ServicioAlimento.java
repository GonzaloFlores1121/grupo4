package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioAlimento {

    Alimento obtenerAlimentosPorId(Long id);
    List<Alimento> listarAlimentos();
    List<Alimento> listarAlimentosPorCategoriaYNombre(Long idCategoria, String nombre);
    void actualizarAlimento(Alimento alimento);
}
