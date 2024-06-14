package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EjercicioInvalido;
import com.tallerwebi.dominio.excepcion.EjercicioNoExistente;

import java.sql.Date;
import java.util.List;

public interface ServicioEjercicio {

    List<Ejercicio> obtenerTodosLosEjercicios() throws EjercicioNoExistente;
    void guardarEjercicioUsuario(String nombre, String intensidad, Ejercicio ejercicio, Usuario usuario, Date fecha, Integer minutos) throws EjercicioInvalido;
    List<Ejercicio> obtenerEjercicioPorNombreOIntensidad(String nombre) throws EjercicioNoExistente;
    void calcularCaloriasQuemadas(Usuario usuario,EjercicioUsuario ejercicioUsuario);
}
