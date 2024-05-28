package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioColacion {
    void update(Colacion colacion);
    Colacion buscarPorId(Long id);
    List<Colacion> listar();
}
