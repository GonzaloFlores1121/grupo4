package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioDesafioImpl implements ServicioDesafio {
    private RepositorioDesafio repositorioDesafio;
    private RepositorioUsuario repositorioUsuario;
    private RepositorioDesafioUsuario repositorioDesafioUsuario;

    @Autowired
    public ServicioDesafioImpl(RepositorioDesafio repositorioDesafio, RepositorioUsuario repositorioUsuario, RepositorioDesafioUsuario repositorioDesafioUsuario) {
        this.repositorioDesafio = repositorioDesafio;
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioDesafioUsuario = repositorioDesafioUsuario;
    }

    @Override
    public List<Desafio> obtenerTodosDesafios() {
        return repositorioDesafio.obtenerTodosDesafios();
    }

    @Override
    public Desafio guardarDesafio(Desafio desafio) {
        return repositorioDesafio.guardarDesafio(desafio);
    }

    @Override
    public Desafio obtenerDesafioPorId(Long id) {
        return repositorioDesafio.obtenerDesafioPorId(id);
    }

    @Override
    public void eliminarDesafio(Long id) {
        repositorioDesafio.eliminarDesafio(id);
    }

    @Override
    public void unirseADesafio(Long usuarioId, Long desafioId) {
        Usuario usuario = repositorioUsuario.buscarPorId(usuarioId);
        Desafio desafio = repositorioDesafio.obtenerDesafioPorId(desafioId);
        if (usuario != null && desafio != null) {
            DesafioUsuario desafioUsuario = new DesafioUsuario();
            desafioUsuario.setUsuario(usuario);
            desafioUsuario.setDesafio(desafio);
            desafioUsuario.setCompletado(false);
            LocalDate fecha = LocalDate.now();
            desafioUsuario.setFechaInicio(fecha);
            repositorioDesafioUsuario.guardarDesafioUsuario(desafioUsuario);

        }
    }

    @Override
    public void completarDesafio(Long usuarioId, Long desafioId) {
        DesafioUsuario desafioUsuario = repositorioDesafioUsuario.obtenerDesafioUsuarioPorUsuarioYDesafio(usuarioId, desafioId);
        if (desafioUsuario != null) {
            desafioUsuario.setCompletado(true);
            LocalDate fecha = LocalDate.now();
            desafioUsuario.setFechaFin(fecha);
            repositorioDesafioUsuario.guardarDesafioUsuario(desafioUsuario);
            Usuario usuario = repositorioUsuario.buscarPorId(usuarioId);
            repositorioUsuario.guardar(usuario);
        }
    }

    @Override
    public List<DesafioUsuario> obtenerDesafiosPorUsuario(Long usuarioId) {
        return repositorioDesafioUsuario.obtenerDesafiosPorUsuario(usuarioId);
    }

    public List<Desafio> obtenerDesafiosEnCurso(Long usuarioId) {
        List<DesafioUsuario> desafiosUsuario = repositorioDesafioUsuario.obtenerDesafiosPorUsuario(usuarioId);
        List<Desafio> desafiosEnCurso = new ArrayList<>();

        for (DesafioUsuario du : desafiosUsuario) {
            if (!du.isCompletado()) {
                desafiosEnCurso.add(du.getDesafio());
            }
        }
        return desafiosEnCurso;
    }

    @Override
    public List<Desafio> obtenerDesafiosCompletados(Long usuarioId) {
        List<DesafioUsuario> desafiosUsuario = repositorioDesafioUsuario.obtenerDesafiosPorUsuario(usuarioId);
        List<Desafio> desafiosCompletados = new ArrayList<>();

        for (DesafioUsuario du : desafiosUsuario) {
            if (du.isCompletado()) {
                desafiosCompletados.add(du.getDesafio());
            }
        }
        return desafiosCompletados;
    }

}