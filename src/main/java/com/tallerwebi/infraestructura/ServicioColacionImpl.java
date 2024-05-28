package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ServicioColacionImpl implements ServicioColacion {
    private RepositorioColacion repositorioColacion;
    private RepositorioAlimento repositorioAlimento;

    @Autowired
    public ServicioColacionImpl(RepositorioColacion repositorioColacion, RepositorioAlimento repositorioAlimento) {
        this.repositorioColacion = repositorioColacion;
        this.repositorioAlimento = repositorioAlimento;
    }

    @Override
    public void updateColacion(Colacion colacion) {
        repositorioColacion.update(colacion);
    }

    @Override
    public Colacion getColacion(Long id) {
        return repositorioColacion.buscarPorId(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Colacion> listarColaciones() {
        List<Colacion> colaciones = repositorioColacion.listar();

        for (Colacion colacion : colaciones) {
            colacion.getAlimentos().size();
        }
        return colaciones;
    }

    @Override
    @Transactional
    public void agregarAlimentoAColacion(Long colacionId, Long alimentoId) {
        Colacion colacion = repositorioColacion.buscarPorId(colacionId);
        Alimento alimento = repositorioAlimento.consultarAlimentoPorID(alimentoId);
        if (colacion != null && alimento != null) {
            alimento.setColacion(colacion);
            repositorioAlimento.update(alimento);

        }
    }

}
