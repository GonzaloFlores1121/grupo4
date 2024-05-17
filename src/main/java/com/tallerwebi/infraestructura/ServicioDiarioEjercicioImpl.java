package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.EjercicioUsuario;
import com.tallerwebi.dominio.RepositorioEjercicioUsuario;
import com.tallerwebi.dominio.ServicioDiarioEjercicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ServicioDiarioEjercicioImpl implements ServicioDiarioEjercicio {

    private RepositorioEjercicioUsuario repositorioEjercicioUsuario;

    @Autowired
    public ServicioDiarioEjercicioImpl( RepositorioEjercicioUsuario ejercicioRepositorioUsuario){

        this.repositorioEjercicioUsuario = ejercicioRepositorioUsuario;
    }


    @Override
    public List<EjercicioUsuario> ObtenerEjerciciosDeHoy() {
        return List.of();
    }
}
