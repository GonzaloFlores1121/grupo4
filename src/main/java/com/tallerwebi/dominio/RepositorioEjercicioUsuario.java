package com.tallerwebi.dominio;

import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface RepositorioEjercicioUsuario {

    void agregarEjercicio(EjercicioUsuario ejercicio);
    EjercicioUsuario obtenerEjercicioPorNombre(String nombre);
    List<EjercicioUsuario> obtenerTodosLosEjercicios(Usuario usuario);
    List<EjercicioUsuario> obtenerEjercicioPorFecha(Usuario usuario, LocalDate fecha);
    EjercicioUsuario buscarEjercicioUsuarioPorId(Long id);
    void eliminarEjercicioUsuario(LocalDate fecha, EjercicioUsuario ejercicioUsuario);
}
