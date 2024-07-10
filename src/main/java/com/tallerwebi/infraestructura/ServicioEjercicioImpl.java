package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.EjercicioInvalido;
import com.tallerwebi.dominio.excepcion.EjercicioNoExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Comparator;
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
    public Integer calcularCaloriasQuemadas(Ejercicio ejercicio, Integer minutos) {
        return (ejercicio.getCaloriasQuemadasPorHora() / 60) * minutos;
    }

    @Override
    public List<EjercicioUsuario> obtenerEjercicioUsuarioPorFecha(Usuario usuario, LocalDate fecha) {
        return repositorioEjercicioUsuario.obtenerEjercicioPorFecha(usuario, fecha);
    }

    @Override
    public EjercicioUsuario buscarEjercicioUsuarioPorId(Long id) {
        return repositorioEjercicioUsuario.buscarEjercicioUsuarioPorId(id);
    }

    @Override
    public void eliminarEjercicioUsuario(EjercicioUsuario ejercicioUsuario,LocalDate fecha) {
         repositorioEjercicioUsuario.eliminarEjercicioUsuario(fecha,ejercicioUsuario);
    }

    @Override
    public Ejercicio obtenerEjercicioPorId(Long id) {
        return ejercicioRepositorio.obtenerEjercicioPorId(id);
    }

    @Override
    public Ejercicio obtenerEjercicioPorCalorias(Integer calorias) throws EjercicioNoExistente {
        List<Ejercicio> ejercicios = obtenerTodosLosEjercicios();

        for (Ejercicio ejercicio : ejercicios) {
            if (ejercicio.getCaloriasQuemadasPorHora() >= calorias) {
                return ejercicio;
            }
        }
        return ejercicios.stream()
                .min(Comparator.comparingInt(e -> Math.abs(e.getCaloriasQuemadasPorHora() - calorias)))
                .orElse(null);
    }

    @Override
    public void guardarEjercicioUsuario(String nombre, String intensidad, Ejercicio ejercicio, Usuario usuario, Date fecha, Integer minutos) throws EjercicioInvalido {

        EjercicioUsuario ejercicioUsuario = new EjercicioUsuario();
        Integer calorias = calcularCaloriasQuemadas(ejercicio, minutos);
        ejercicioUsuario.setCaloriasQuemadas(calorias);
        ejercicioUsuario.setNombre(ejercicio.getNombre());
        ejercicioUsuario.setIntensidad(intensidad);
        ejercicioUsuario.setEjercicio(ejercicio);
        ejercicioUsuario.setUsuario(usuario);
        ejercicioUsuario.setFecha(fecha.toLocalDate());
        ejercicioUsuario.setMinutos(minutos);
        if (ejercicioUsuario.getNombre() == null || ejercicioUsuario.getMinutos() == null ||
                ejercicioUsuario.getFecha() == null || ejercicioUsuario.getIntensidad() == null ||
                ejercicioUsuario.getEjercicio() == null || ejercicioUsuario.getUsuario() == null) {
            throw new EjercicioInvalido("Ejercicio invalido");
        } else {

            repositorioEjercicioUsuario.agregarEjercicio(ejercicioUsuario);

        }
    }
}



