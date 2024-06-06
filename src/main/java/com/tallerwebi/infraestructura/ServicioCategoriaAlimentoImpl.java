package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.CategoriaAlimento;
import com.tallerwebi.dominio.RepositorioCategoriaAlimento;
import com.tallerwebi.dominio.ServicioCategoriaAlimento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service("servicioCategoriaAlimento")
@Transactional
public class ServicioCategoriaAlimentoImpl implements ServicioCategoriaAlimento {

    private RepositorioCategoriaAlimento repositorioCategoriaAlimento;

    @Autowired
    public ServicioCategoriaAlimentoImpl(RepositorioCategoriaAlimento repositorioCategoriaAlimento) {
        this.repositorioCategoriaAlimento = repositorioCategoriaAlimento;
    }

    @Override
    @Transactional
    public CategoriaAlimento obtenerCategoriaPorId(Long id) {
    CategoriaAlimento categoriaAlimento = repositorioCategoriaAlimento.obtenerCategoriaPorId(id);
        if(categoriaAlimento == null){
            throw new RuntimeException("Categoria no encontrada");
        }
        return categoriaAlimento;
    }

    @Override
    public List<CategoriaAlimento> obtenerTodasLasCategorias() {
        return repositorioCategoriaAlimento.obtenerTodasLasCateogorias();
    }

    @Override
    public List<CategoriaAlimento> obtenerCategoriasPorNombre(String nombre) {
        return repositorioCategoriaAlimento.obtenerCategoriasPorNombre(nombre);
    } 

}
