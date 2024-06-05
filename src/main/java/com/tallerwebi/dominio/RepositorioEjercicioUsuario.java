package com.tallerwebi.dominio;

import org.springframework.stereotype.Repository;

import java.util.List;

public interface RepositorioEjercicioUsuario {

    void agregarEjercicio(EjercicioUsuario ejercicio);
    EjercicioUsuario obtenerEjercicioPorNombre(String nombre);
    List<EjercicioUsuario> obtenerTodosLosEjercicios(Usuario usuario);

}
