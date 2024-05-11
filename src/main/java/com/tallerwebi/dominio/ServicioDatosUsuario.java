package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.DatosIncorrectos;

public interface ServicioDatosUsuario {


    Integer calcularIngestaCalorica(Usuario usuario) throws DatosIncorrectos;

    Double calcularMetabolismoBasalDelUsuario(Usuario usuario) throws DatosIncorrectos;

    MacronutrientesUsuario CalcularDistribucionDeMacronutrientes(Usuario usuario);
}
