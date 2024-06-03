package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioColacionImpl implements ServicioColacion {

    private RepositorioColacion repositorioColacion;

    @Autowired
    public ServicioColacionImpl(RepositorioColacion repositorioColacion) {
        this.repositorioColacion = repositorioColacion;
    }

    @Override
    public void guardarColacionUsuario(Alimento alimento, Usuario usuario, TipoColacion tipoColacion, LocalDate fecha) {
        Colacion colacion= new Colacion();
        colacion.setAlimentos(alimento);
        colacion.setFecha(fecha);
        colacion.setTipo(tipoColacion);
        repositorioColacion.agregarColacion(colacion);
    }

    @Override
    public List<Alimento> obtenerAlimentosPorFechaYUsuarioYTipoColacion(LocalDate fecha, Usuario usuario, TipoColacion tipo) {
      List <Colacion> colaciones= repositorioColacion.obtenerColacionesPorFechaYUsuarioYTipo(fecha,usuario,tipo);
        return colaciones.stream().map(Colacion::getAlimentos).collect(Collectors.toList());
    }
}
