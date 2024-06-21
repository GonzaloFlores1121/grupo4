package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioUsuario {

    Usuario buscarUsuario(String email, String password);
    void guardar(Usuario usuario);
    void modificar(Usuario usuario);
    void eliminar(Usuario usuario);
    Usuario buscarPorId(Long id);
    Usuario buscarPorEmail(String email);
    List<Usuario> obtenerTodos();
    void agregarPesoInicial(Double peso,Usuario usuario);
}

