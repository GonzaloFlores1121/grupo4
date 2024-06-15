package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.RepositorioAlimento;

import com.tallerwebi.dominio.ServicioAlimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioAlimento")
@Transactional
public class ServicioAlimentoImpl implements ServicioAlimento {
    
    private RepositorioAlimento repositorioAlimento;

    @Autowired
    public ServicioAlimentoImpl(RepositorioAlimento repositorioAlimento) {
        this.repositorioAlimento = repositorioAlimento;
    }

    @Override
    public Alimento obtenerAlimentosPorId(Long id) {
        return repositorioAlimento.consultarAlimentoPorID(id);
    }

    @Override
    public List<Alimento> listarAlimentos() {
        return repositorioAlimento.consultarAlimentos();
    }

    @Override
    public List<Alimento> listarAlimentosPorCategoriaYNombre(Long idCategoria, String nombre) {
        return repositorioAlimento.consultarAlimentosPorCategoriaYNombre(idCategoria, nombre);
    }

    @Override
    public void actualizarAlimento(Alimento alimento) {
        repositorioAlimento.update(alimento);
    }

}

