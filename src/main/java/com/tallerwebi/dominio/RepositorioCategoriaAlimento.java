package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioCategoriaAlimento {
    
    CategoriaAlimento obtenerCategoriaPorId(Long id);
    List<CategoriaAlimento> obtenerTodasLasCateogorias();
    List<CategoriaAlimento> obtenerCategoriasPorNombre(String nombre);

}
