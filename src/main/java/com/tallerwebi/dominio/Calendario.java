package com.tallerwebi.dominio;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Calendario {
    private List<EjercicioUsuario> ejercicios;
    private List<Colacion> colaciones;
    private Integer caloriasConsumidas;
    private Integer caloriasQuemadas;
    private Integer caloriasNetas;

    public Calendario() {
        this.ejercicios = new ArrayList<>();
        this.colaciones = new ArrayList<>();
        this.caloriasConsumidas = 0;
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
        int calorias = ejercicio.getCaloriasQuemadas();
        caloriasQuemadas+= calorias;

    }

    public void agregarColacion(Colacion colacion) {
        colaciones.add(colacion);
        int calorias = colacion.getAlimentos().getEnergia();
        caloriasConsumidas += calorias;

    }

    public Integer getCaloriasConsumidas() {
        return caloriasConsumidas;
    }

    public void setCaloriasConsumidas(Integer caloriasConsumidas) {
        this.caloriasConsumidas = caloriasConsumidas;
    }

    public void setCaloriasQuemadas(Integer caloriasQuemadas) {
        this.caloriasQuemadas = caloriasQuemadas;
    }

    public Integer getCaloriasQuemadas() {
        return caloriasQuemadas;
    }

    public Integer getCaloriasNetas() {
        caloriasNetas=caloriasConsumidas-caloriasQuemadas;
        return caloriasNetas;
    }

    public void setCaloriasNetas(Integer caloriasNetas) {this.caloriasNetas = caloriasNetas;}

}

