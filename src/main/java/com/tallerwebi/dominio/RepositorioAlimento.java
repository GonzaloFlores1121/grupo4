package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioAlimento {
    Alimento consultarAlimentoPorID(Long id);
    List<Alimento> consultarAlimentos();
    void update(Alimento alimento);
}
