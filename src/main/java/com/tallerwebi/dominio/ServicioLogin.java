package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

public interface ServicioLogin {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws UsuarioExistente, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException;
    Boolean usuarioDatosCorrecto(Usuario usuario) throws DatosIncorrectos;
    void modificarPerfil(Usuario usuario, String nuevoEmail) throws UsuarioExistente, DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException;
    Usuario buscar(String email);

}
