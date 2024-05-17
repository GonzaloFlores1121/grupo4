package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServicioCalendarioImpl implements ServicioCalendario {

    private RepositorioEjercicioUsuario repositorioEjercicioUsuario;

    @Autowired
    public ServicioCalendarioImpl( RepositorioEjercicioUsuario ejercicioRepositorioUsuario){

        this.repositorioEjercicioUsuario = ejercicioRepositorioUsuario;
    }


    @Override
    public Map<Date, Calendario> obtenerFechasCalendario() {
       List<EjercicioUsuario>ejercicios=repositorioEjercicioUsuario.obtenerTodosLosEjercicios();
        Map<Date, Calendario> reportePorFecha = new HashMap<>();

        for (EjercicioUsuario ejercicio: ejercicios){

           Date fecha= ejercicio.getFecha();
           Calendario calendario=reportePorFecha.computeIfAbsent(fecha, k -> new Calendario());
           calendario.agregarEjercicio(ejercicio);
        }
        return reportePorFecha;
    }
    
    }
