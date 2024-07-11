package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

public interface ServicioLogin {

    Usuario verificarUsuario(String email, String password);

    void registrarUsuario(Usuario usuario) throws UsuarioExistente, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, PesoMetaIncorrectoException;
    
    Usuario buscarUsuario(String email);
    
    void modificarImagen(Usuario usuario, String imagen);
    
    void modificarUsuario(Usuario usuario, Usuario nuevosDatos) throws UsuarioExistente, DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException, PesoMetaIncorrectoException;
    
    Boolean validarDatos(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, PesoMetaIncorrectoException;

}
