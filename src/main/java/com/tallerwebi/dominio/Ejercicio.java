package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Ejercicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer caloriasQuemadas;
    private String intensidad;

    public Ejercicio( String nombre, Integer caloriasQuemadas,String intensidad) {
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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
