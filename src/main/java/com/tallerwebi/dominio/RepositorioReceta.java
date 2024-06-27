package com.tallerwebi.dominio;

import javax.transaction.Transactional;
import java.util.List;

public interface RepositorioReceta {
    List<Receta> obtenerTodasLasRecetas();
    Receta consultarReceta(Long id);
}
