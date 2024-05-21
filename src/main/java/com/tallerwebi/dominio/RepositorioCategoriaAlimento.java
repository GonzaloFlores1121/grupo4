package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioCategoriaAlimento {
    List<CategoriaAlimento> obtenerTodasLasCateogorias();

    CategoriaAlimento obtenerCategoriaPorId(Long id);
}
