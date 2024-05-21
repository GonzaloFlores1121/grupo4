package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioEjercicio {

    List<Ejercicio> obtenerTodosLosEjercicios();
    Ejercicio obtenerEjercicioPorId(Long id);
    List<Ejercicio> obtenerEjercicioPorNombreOIntensidad(String caracteristica);

}
