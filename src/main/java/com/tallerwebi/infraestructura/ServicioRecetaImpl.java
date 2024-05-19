package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Receta;
import com.tallerwebi.dominio.RepositorioReceta;
import com.tallerwebi.dominio.ServicioReceta;
import com.tallerwebi.dominio.excepcion.RecetaNoEncontradaException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service("servicioReceta")
@Transactional
public class ServicioRecetaImpl implements ServicioReceta {

    private RepositorioReceta repositorioRecetas;

    @Autowired
    public ServicioRecetaImpl(RepositorioReceta repositorioRecetas) {
        this.repositorioRecetas = repositorioRecetas;
    }

    @Override
    public List<Receta> obtenerTodasLasRecetas() {
        return repositorioRecetas.obtenerTodasLasRecetas();
    }

    @Override
    public Receta obtenerRecetaPorId(Long id) throws RecetaNoEncontradaException {
        Receta receta = repositorioRecetas.consultarReceta(id);
        if (receta == null) {
            throw new RecetaNoEncontradaException();
        }
    return receta;
    }

}
