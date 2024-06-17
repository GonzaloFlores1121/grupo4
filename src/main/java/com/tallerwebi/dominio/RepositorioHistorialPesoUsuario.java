package com.tallerwebi.dominio;

import java.sql.Date;
import java.util.List;

public interface RepositorioHistorialPesoUsuario {

    void agregarPesoYFecha(HistoriaPesoUsuario nuevoPesoUsuario);

    List<HistoriaPesoUsuario> obtenerHistorialPesoUsuario(Usuario usuario);

    HistoriaPesoUsuario obtenerHistorialPesoUsuarioParaUnaFecha(Date fecha);

    void modificarPeso(Double peso, Usuario usuario);

    void actualizarMiPesoAgregado(HistoriaPesoUsuario historialPesoUsuario);

    void actualizarMiIcr(Usuario usuario, Integer icr);
}
