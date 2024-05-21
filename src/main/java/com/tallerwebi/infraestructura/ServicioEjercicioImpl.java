package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EjercicioNoExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioEjercicioImpl implements ServicioEjercicio {

    private RepositorioEjercicio ejercicioRepositorio;
    private RepositorioEjercicioUsuario repositorioEjercicioUsuario;
    @Autowired
    public ServicioEjercicioImpl(RepositorioEjercicio ejercicioRepositorio, RepositorioEjercicioUsuario ejercicioRepositorioUsuario){
        this.ejercicioRepositorio = ejercicioRepositorio;
        this.repositorioEjercicioUsuario = ejercicioRepositorioUsuario;
    }

    public List<Ejercicio> obtenerTodosLosEjercicios() throws EjercicioNoExistente {
    if (ejercicioRepositorio.obtenerTodosLosEjercicios() == null){
        throw new EjercicioNoExistente();
    }else {
        return ejercicioRepositorio.obtenerTodosLosEjercicios();
        }
    }

    @Override
    public boolean guardarEjercicio(EjercicioUsuario ejercicioUsuario) {
        if (ejercicioUsuario.getNombre() == null || ejercicioUsuario.getMinutos() == null ||
                ejercicioUsuario.getFecha() == null  || ejercicioUsuario.getIntensidad() == null ||
                ejercicioUsuario.getEjercicio() == null || ejercicioUsuario.getUsuario() == null) {
            // Si algún campo está vacío, devolver false
            return false;
        } else {
            // Guardar en el repositorio si todos los campos están presentes
            repositorioEjercicioUsuario.agregarEjercicio(ejercicioUsuario);
            return true;
        }
    }

    @Override
    public List<Ejercicio> obtenerEjercicioPorNombreOIntensidad(String caracteristica) throws EjercicioNoExistente {
        if (ejercicioRepositorio.obtenerEjercicioPorNombreOIntensidad(caracteristica).isEmpty()) {
            throw new EjercicioNoExistente();
        } else {
            return ejercicioRepositorio.obtenerEjercicioPorNombreOIntensidad(caracteristica);
        }
    }
}


