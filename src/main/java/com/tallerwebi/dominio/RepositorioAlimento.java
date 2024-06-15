package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioAlimento {

    Alimento consultarAlimentoPorID(Long id);
    void update(Alimento alimento);
    void save(Alimento alimento);
    List<Alimento> consultarAlimentos();
    List<Alimento> consultarAlimentosPorCategoriaYNombre(Long idCategoria, String nombre);

}
