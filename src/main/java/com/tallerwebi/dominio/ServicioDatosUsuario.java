package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.DatosIncorrectos;

public interface ServicioDatosUsuario {

    Boolean registrarUsuario(Usuario usuario) throws DatosIncorrectos;

    Boolean usuarioDatosCorrecto(Usuario usuario) throws DatosIncorrectos;

    Integer calcularIngestaCalorica(Usuario usuario) throws DatosIncorrectos;

    Double calcularMetabolismoBasalDelUsuario(Usuario usuario) throws DatosIncorrectos;
}
