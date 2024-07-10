package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.EjercicioInvalido;
import com.tallerwebi.dominio.excepcion.EjercicioNoExistente;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public interface ServicioEjercicio {

    List<Ejercicio> obtenerTodosLosEjercicios() throws EjercicioNoExistente;

    Ejercicio obtenerEjercicioPorCalorias(Integer calorias) throws EjercicioNoExistente;

    void guardarEjercicioUsuario(String nombre, String intensidad, Ejercicio ejercicio, Usuario usuario, Date fecha, Integer minutos) throws EjercicioInvalido;
    List<Ejercicio> obtenerEjercicioPorNombreOIntensidad(String nombre) throws EjercicioNoExistente;
    Integer calcularCaloriasQuemadas(Ejercicio ejercicio,  Integer minutos);
    List<EjercicioUsuario> obtenerEjercicioUsuarioPorFecha(Usuario usuario, LocalDate fecha);
    EjercicioUsuario buscarEjercicioUsuarioPorId(Long id);

    void eliminarEjercicioUsuario(EjercicioUsuario ejercicioUsuario, LocalDate fecha);

    Ejercicio obtenerEjercicioPorId(Long id);
}
