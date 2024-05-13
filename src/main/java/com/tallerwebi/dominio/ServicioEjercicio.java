package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioEjercicio {

    List<Ejercicio> obtenerTodosLosEjercicios();

    boolean guardarEjercicio(EjercicioUsuario ejercicioUsuario);
}
