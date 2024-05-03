package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.DatosIncorrectos;

public interface ServicioDatosUsuario {

    Boolean registrarUsuario(Usuario usuario) throws DatosIncorrectos;

}
