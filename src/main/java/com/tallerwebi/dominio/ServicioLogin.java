package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;

public interface ServicioLogin {

    void registrarUsuario(Usuario usuario) throws UsuarioExistente, DatosIncorrectos;

    Usuario consultarUsuario(String email, String password);

    Boolean usuarioDatosCorrecto(Usuario usuario) throws DatosIncorrectos;



}
