package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Ejercicio {

    @Id
    private Integer id;
    private String nombre;
    private Integer caloriasQuemadas;
    private String intensidad;

    public Ejercicio(Integer id, String nombre, Integer caloriasQuemadas,String intensidad) {
        this.id = id;
        this.nombre = nombre;
        this.caloriasQuemadas = caloriasQuemadas;
        this.intensidad = intensidad;
    }

    public Ejercicio() {

    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCaloriasQuemadas() {
        return caloriasQuemadas;
    }

    public void setCaloriasQuemadas(Integer caloriasQuemadas) {
        this.caloriasQuemadas = caloriasQuemadas;
    }

    public String getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(String intensidad) {
        this.intensidad = intensidad;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
