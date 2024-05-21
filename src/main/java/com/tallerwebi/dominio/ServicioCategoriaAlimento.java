package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioCategoriaAlimento {
     List<CategoriaAlimento> obtenerTodasLasCategorias();

     CategoriaAlimento obtenerCategoriaPorId(Long id);
}
