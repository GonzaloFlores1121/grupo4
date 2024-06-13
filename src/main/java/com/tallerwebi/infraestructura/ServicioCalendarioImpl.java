package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Map<Date, Calendario> reportePorFecha = new HashMap<>();


        for (EjercicioUsuario ejercicio : ejercicios) {
            Date fecha = ejercicio.getFecha();
            Calendario calendario = reportePorFecha.computeIfAbsent(fecha, k -> new Calendario());
            calendario.agregarEjercicio(ejercicio);
        }

        for (Colacion colacion : colaciones) {
            LocalDate fecha = colacion.getFecha();
            Calendario calendario = reportePorFecha.computeIfAbsent(Date.valueOf(fecha), k -> new Calendario());
            calendario.agregarColacion(colacion);
        }
        for (Calendario calendario : reportePorFecha.values()) {
            Map<Date, Integer> caloriasPorFecha = new HashMap<>();
            for (Colacion colacion : calendario.getColaciones()) {
                LocalDate fecha = colacion.getFecha();
                Date fechaSql = Date.valueOf(fecha);
                int calorias = caloriasPorFecha.getOrDefault(fechaSql, 0);
                calorias += colacion.getAlimentos().getEnergia();
                caloriasPorFecha.put(fechaSql, calorias);
            }
            calendario.setCaloriasConsumidasPorFecha(caloriasPorFecha);
        }
        return reportePorFecha;
    }



    
    }
