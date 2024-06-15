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
    private String intensidad;
    private Integer caloriasQuemadasPorHora;

    public Ejercicio( String nombre,String intensidad, Integer caloriasQuemadasPorHora) {
        this.nombre = nombre;
        this.intensidad = intensidad;
        this.caloriasQuemadasPorHora=caloriasQuemadasPorHora;
    }

    public Ejercicio() {

    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public Integer getCaloriasQuemadasPorHora() {
        return caloriasQuemadasPorHora;
    }

    public void setCaloriasQuemadasPorHora( Integer caloriasQuemadasPorHora) {
        this.caloriasQuemadasPorHora=caloriasQuemadasPorHora;
    }
}
