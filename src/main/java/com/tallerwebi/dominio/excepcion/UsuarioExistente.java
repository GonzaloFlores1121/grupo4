package com.tallerwebi.dominio.excepcion;

public class UsuarioExistente extends Exception {

    public UsuarioExistente(String elUsuarioYaExiste) {
      super(elUsuarioYaExiste);
    }
}

