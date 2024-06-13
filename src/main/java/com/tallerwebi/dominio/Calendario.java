package com.tallerwebi.dominio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calendario {
    private List<EjercicioUsuario> ejercicios;
    private List<Colacion> colaciones;
    private Map<Date, Integer> caloriasConsumidasPorFecha;
    private Integer caloriasQuemadas;

    public Calendario() {
        this.ejercicios = new ArrayList<>();
        this.colaciones = new ArrayList<>();
        this.caloriasConsumidasPorFecha = new HashMap<>();
        this.caloriasQuemadas = 0;
    }

    public List<EjercicioUsuario> getEjercicios() {
        return ejercicios;
    }

    public List<Colacion> getColaciones() {
        return colaciones;
    }

    public void agregarEjercicio(EjercicioUsuario ejercicio) {
        ejercicios.add(ejercicio);

    }

    public void agregarColacion(Colacion colacion) {
        colaciones.add(colacion);

    }

    public Map<Date, Integer> getCaloriasConsumidasPorFecha() {
        return caloriasConsumidasPorFecha;
    }

    public void setCaloriasConsumidasPorFecha(Map<Date, Integer> caloriasConsumidasPorFecha) {
        this.caloriasConsumidasPorFecha = caloriasConsumidasPorFecha;
    }

    public Integer getCaloriasQuemadas() {
        return caloriasQuemadas;
    }


}

