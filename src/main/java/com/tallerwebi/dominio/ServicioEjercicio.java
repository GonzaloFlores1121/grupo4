package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EjercicioNoExistente;

import java.util.List;

public interface ServicioEjercicio {

    List<Ejercicio> obtenerTodosLosEjercicios() throws EjercicioNoExistente;
    boolean guardarEjercicio(EjercicioUsuario ejercicioUsuario);
    List<Ejercicio> obtenerEjercicioPorNombreOIntensidad(String nombre) throws EjercicioNoExistente;

}
