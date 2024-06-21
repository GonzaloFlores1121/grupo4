package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

import java.util.List;

public interface ServicioDatosUsuario {

    Integer calcularIngestaCalorica(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException;

    Double calcularMetabolismoBasalDelUsuario(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException;

    MacronutrientesUsuario CalcularDistribucionDeMacronutrientes(Usuario usuario);

    Double pesoDisminuidoALaFecha(Usuario usuario);

    Double pesoGanadoALaFecha(Usuario usuario);

    Double CantidadDePesoFaltanteParaLLegarALaMeta(Usuario usuario);

    void seAlcanzoMeta(Usuario usuario, Double pesoActualizado) throws UsuarioNoExistente;

    void actualizarPeso(Usuario usuario, Double peso) throws PesoIncorrectoException, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, UsuarioNoExistente;

    List<HistoriaPesoUsuario> obtenerTodoElHistorialDePeso(Usuario usuario) throws UsuarioNoExistente;

    void verificarIngestaDelDia(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, UsuarioNoExistente, EjercicioNoExistente;

    void ingresarPesoInicial(Double peso,Usuario usuario);

    Double obtenerPesoActual(Usuario usuario);

    Double obtenerPesoInicial(Usuario usuario);
}
