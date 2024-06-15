package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EjercicioInvalido;
import com.tallerwebi.dominio.excepcion.EjercicioNoExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class ServicioEjercicioImpl implements ServicioEjercicio {

    private RepositorioEjercicio ejercicioRepositorio;
    private RepositorioEjercicioUsuario repositorioEjercicioUsuario;

    @Autowired
    public ServicioEjercicioImpl(RepositorioEjercicio ejercicioRepositorio, RepositorioEjercicioUsuario ejercicioRepositorioUsuario) {
        this.ejercicioRepositorio = ejercicioRepositorio;
        this.repositorioEjercicioUsuario = ejercicioRepositorioUsuario;
    }

    public List<Ejercicio> obtenerTodosLosEjercicios() throws EjercicioNoExistente {
        if (ejercicioRepositorio.obtenerTodosLosEjercicios() == null) {
            throw new EjercicioNoExistente();
        } else {
            return ejercicioRepositorio.obtenerTodosLosEjercicios();
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




    @Override
    public void guardarEjercicioUsuario(String nombre, String intensidad, Ejercicio ejercicio, Usuario usuario, Date fecha, Integer minutos) throws EjercicioInvalido {

        EjercicioUsuario ejercicioUsuario = new EjercicioUsuario();
        ejercicioUsuario.setNombre(ejercicio.getNombre());
        ejercicioUsuario.setIntensidad(intensidad);
        ejercicioUsuario.setEjercicio(ejercicio);
        ejercicioUsuario.setUsuario(usuario);
        ejercicioUsuario.setFecha(fecha);
        ejercicioUsuario.setMinutos(minutos);
        if (ejercicioUsuario.getNombre() == null || ejercicioUsuario.getMinutos() == null ||
                ejercicioUsuario.getFecha() == null  || ejercicioUsuario.getIntensidad() == null ||
                ejercicioUsuario.getEjercicio() == null || ejercicioUsuario.getUsuario() == null) {
            throw new EjercicioInvalido("Ejercicio invalido");
        } else {

            repositorioEjercicioUsuario.agregarEjercicio(ejercicioUsuario);

        }
    }

}



