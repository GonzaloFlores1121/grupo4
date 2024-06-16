package com.tallerwebi.dominio.excepcion;

public class AlimentoNoEncontradoException extends Throwable {
    public AlimentoNoEncontradoException(Long id) {
        super("No se pudo encontrar el alimento con id: " + id);
    }
}
