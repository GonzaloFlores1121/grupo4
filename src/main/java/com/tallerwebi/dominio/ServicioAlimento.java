package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioAlimento {
    Alimento obtenerAlimentosPorId(Long id);
    List<Alimento> listarAlimentos();
}
