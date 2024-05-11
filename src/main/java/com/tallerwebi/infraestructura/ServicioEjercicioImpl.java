package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.RepositorioEjercicio;
import com.tallerwebi.dominio.ServicioEjercicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioEjercicioImpl implements ServicioEjercicio {

    private RepositorioEjercicio ejercicioRepositorio;
    @Autowired
    public ServicioEjercicioImpl(RepositorioEjercicio ejercicioRepositorio){this.ejercicioRepositorio = ejercicioRepositorio;}

    public List<Ejercicio> obtenerTodosLosEjercicios() {
        return ejercicioRepositorio.obtenerTodosLosEjercicios();
    }
}
