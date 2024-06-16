package com.tallerwebi.dominio.excepcion;

public class CategoriaAlimentoNoEncontradaException extends Throwable {
    public CategoriaAlimentoNoEncontradaException(Long id) {
        super("No se pudo encontrar la categoria con el id "+id);
    }
}
