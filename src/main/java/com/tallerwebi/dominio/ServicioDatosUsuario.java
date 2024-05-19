package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.AlturaIncorrectaException;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.dominio.excepcion.EdadInvalidaException;
import com.tallerwebi.dominio.excepcion.PesoIncorrectoException;

public interface ServicioDatosUsuario {


    Integer calcularIngestaCalorica(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException;

    Double calcularMetabolismoBasalDelUsuario(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException;

    MacronutrientesUsuario CalcularDistribucionDeMacronutrientes(Usuario usuario);
}
