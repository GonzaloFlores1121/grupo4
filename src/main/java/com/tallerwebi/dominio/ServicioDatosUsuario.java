package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

import java.util.Date;
import java.util.List;

public interface ServicioDatosUsuario {

    Integer calcularIngestaCalorica(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException;

    Double calcularMetabolismoBasalDelUsuario(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException;

    MacronutrientesUsuario CalcularDistribucionDeMacronutrientes(Usuario usuario);

    void agregarNuevoPeso(Double peso) throws PesoIncorrectoException;

    List<HistoriaPesoUsuario> obtenerTodoElHistorialDePeso(Usuario usuario) throws UsuarioNoExistente;

}
