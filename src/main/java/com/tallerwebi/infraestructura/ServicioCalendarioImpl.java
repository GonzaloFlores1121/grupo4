package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class ServicioCalendarioImpl implements ServicioCalendario {

    private final RepositorioColacion repositorioColacion;
    private RepositorioEjercicioUsuario repositorioEjercicioUsuario;


    @Autowired
    public ServicioCalendarioImpl(RepositorioEjercicioUsuario ejercicioRepositorioUsuario, RepositorioColacion repositorioColacion){

        this.repositorioEjercicioUsuario = ejercicioRepositorioUsuario;
        this.repositorioColacion = repositorioColacion;
    }


    @Override
    public Map<Date, Calendario> obtenerFechasCalendario(Usuario usuario) {
        List<EjercicioUsuario> ejercicios = repositorioEjercicioUsuario.obtenerTodosLosEjercicios(usuario);
        List<Colacion> colaciones = repositorioColacion.obtenerTodasLasColacionesDelUsuario(usuario);
        Map<Date, Calendario> reportePorFecha = new TreeMap<>(Comparator.reverseOrder());


        for (EjercicioUsuario ejercicio : ejercicios) {
            LocalDate  fecha = ejercicio.getFecha();
            Calendario calendario = reportePorFecha.computeIfAbsent(Date.valueOf(fecha), k -> new Calendario());
            calendario.agregarEjercicio(ejercicio);
        }

        for (Colacion colacion : colaciones) {
            LocalDate fecha = colacion.getFecha();
            Calendario calendario = reportePorFecha.computeIfAbsent(Date.valueOf(fecha), k -> new Calendario());
            calendario.agregarColacion(colacion);
        }

        return reportePorFecha;
    }

    @Override
    public Integer mostrarIngestaCaloricaDelDia(Usuario usuario, Date fecha) {
        List<Colacion> colaciones = repositorioColacion.obtenerTodasLasColacionesDelUsuarioPorFecha(usuario, fecha.toLocalDate());
        Integer ingestaCalorica = 0;

        for (Colacion colacion : colaciones) {

            Integer cantidad = colacion.getCantidad();


            ingestaCalorica +=  colacion.getAlimentos().getEnergia();
        }

        return ingestaCalorica;
    }

}
