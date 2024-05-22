package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioHistorialPesoUsuario {

    void agregarPesoYFecha(HistoriaPesoUsuario nuevoPesoUsuario);
    List<HistoriaPesoUsuario> obtenerHistorialPesoUsuario();
}
