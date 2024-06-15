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
    private RepositorioAlimento repositorioAlimento;
    @Autowired
    public ServicioColacionImpl(RepositorioColacion repositorioColacion, RepositorioAlimento repositorioAlimento) {
        this.repositorioColacion = repositorioColacion;
        this.repositorioAlimento= repositorioAlimento;
    }

    @Override
    public void guardarColacionUsuario(Alimento alimento, Usuario usuario, int cantidad, TipoColacion tipoColacion, LocalDate fecha, String nombre) throws Exception {
     Alimento alimentoNuevo= crearNuevoAlimentoSeteandoValoresFormulario(alimento,cantidad,nombre);
      repositorioAlimento.save(alimentoNuevo);

        Colacion colacion = new Colacion();
        colacion.setAlimentos(alimentoNuevo);
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

    @Override
    public void actualizarColacion(Colacion colacion) {
        repositorioColacion.update(colacion);
    }

    @Override
    public void eliminarColacionUsuario(Alimento alimento, Usuario usuario, TipoColacion tipoColacion, LocalDate fecha) {

        repositorioColacion.eliminarColacion(alimento,usuario,tipoColacion,fecha);
    }

    @Override
    public Colacion obtenerColacionPorAlimento(Alimento alimento) {
        Colacion colacion = repositorioColacion.obtenerColacionPorAlimento(alimento);
        if (colacion == null) {
            throw new IllegalArgumentException("No se encontró la colación para el alimento especificado");
        }
        return colacion;
    }

    private Alimento crearNuevoAlimentoSeteandoValoresFormulario(Alimento alimento, int cantidad,String nombre) {
        Alimento alimentoNuevo = new Alimento();
        alimentoNuevo.setNombre(nombre);
        alimentoNuevo.setAzucar(alimento.getAzucar());
        alimentoNuevo.setCarbohidratos(alimento.getCarbohidratos());
        alimentoNuevo.setColesterol(alimento.getColesterol());
        alimentoNuevo.setCalorias(alimento.getCalorias());
        alimentoNuevo.setCantidad(cantidad);
        alimentoNuevo.setEnergia(alimento.getEnergia());
        alimentoNuevo.setFibra(alimento.getFibra());
        alimentoNuevo.setGrasa(alimento.getGrasa());
        alimentoNuevo.setGrasaMonoinsaturada(alimento.getGrasaMonoinsaturada());
        alimentoNuevo.setGrasaPoliinsaturada(alimento.getGrasaPoliinsaturada());
        alimentoNuevo.setGrasaSaturada(alimento.getGrasaSaturada());
        alimentoNuevo.setImagen(alimento.getImagen());
        alimentoNuevo.setPotasio(alimento.getPotasio());
        alimentoNuevo.setProteina(alimento.getProteina());
        alimentoNuevo.setSodio(alimento.getSodio());
        alimentoNuevo.actualizarValoresNutricionalesPorCantidad();
        alimentoNuevo.setEsPersonalizado(true);


        return alimentoNuevo;
    }
}
