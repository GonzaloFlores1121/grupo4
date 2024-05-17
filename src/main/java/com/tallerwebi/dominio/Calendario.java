package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class Calendario {

    private List<EjercicioUsuario> ejercicios;


    public Calendario() {
        this.ejercicios = new ArrayList<>();

    }

    public List<EjercicioUsuario> getEjercicios() {
        return ejercicios;
    }


    public void agregarEjercicio(EjercicioUsuario ejercicio) {

            ejercicios.add(ejercicio);

    }

}
