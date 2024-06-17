package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

import java.util.List;

public interface ServicioDatosUsuario {

    Integer calcularIngestaCalorica(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException;

    Double calcularMetabolismoBasalDelUsuario(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException;

    MacronutrientesUsuario CalcularDistribucionDeMacronutrientes(Usuario usuario);

    void actualizarPeso(Usuario usuario, Double peso) throws PesoIncorrectoException, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException;

    List<HistoriaPesoUsuario> obtenerTodoElHistorialDePeso(Usuario usuario) throws UsuarioNoExistente;

    void verificarIngestaDelDia(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, UsuarioNoExistente;

}
