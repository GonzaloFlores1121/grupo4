package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public void guardarColacionUsuario(Alimento alimento, Usuario usuario, TipoColacion tipoColacion, LocalDate fecha) throws Exception {
        Colacion colacion= new Colacion();
        colacion.setAlimentos(alimento);
        colacion.setFecha(fecha);
        colacion.setTipo(tipoColacion);
        colacion.setUsuario(usuario);

        if(colacion.getAlimentos() == null) {
            throw new Exception("El alimento es nulo");
        }
        if(colacion.getUsuario() == null) {
            throw new Exception("El usuario es nulo");
        }
        if(colacion.getFecha() == null) {
            throw new Exception("La fecha es nula");
        }
        if(colacion.getTipo() == null) {
            throw new Exception("El tipo de colación es nulo");
        }

        repositorioColacion.agregarColacion(colacion);
    }

    @Override
    public List<Alimento> obtenerAlimentosPorFechaYUsuarioYTipoColacion(LocalDate fecha, Usuario usuario, TipoColacion tipo) {
      List <Colacion> colaciones= repositorioColacion.obtenerColacionesPorFechaYUsuarioYTipo(fecha,usuario,tipo);
        if (colaciones != null) {
            return colaciones.stream().map(Colacion::getAlimentos).collect(Collectors.toList());
        } else {

            return new ArrayList<>();
        }
    }
}
