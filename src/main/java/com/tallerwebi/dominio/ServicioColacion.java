package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioColacion {
    void updateColacion(Colacion colacion);
    Colacion getColacion(Long id);
    List<Colacion> listarColaciones();
    void agregarAlimentoAColacion(Long colacionId, Long alimentoId);
}
