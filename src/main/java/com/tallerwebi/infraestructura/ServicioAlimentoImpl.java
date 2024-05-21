package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Alimento;
import com.tallerwebi.dominio.RepositorioAlimento;
import com.tallerwebi.dominio.ServicioAlimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
}
