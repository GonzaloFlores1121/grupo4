package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioCategoriaAlimento {
     
     CategoriaAlimento obtenerCategoriaPorId(Long id);
     List<CategoriaAlimento> obtenerTodasLasCategorias();
     List<CategoriaAlimento> obtenerCategoriasPorNombre(String nombre);

}
