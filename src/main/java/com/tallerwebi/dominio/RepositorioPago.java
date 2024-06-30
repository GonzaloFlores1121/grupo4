package com.tallerwebi.dominio;

public interface RepositorioPago {
    Usuario obtenerUsuario(String email);
    void save(Usuario usuario);
}
