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

    private Alimento crearNuevoAlimentoSeteandoValoresFormulario(Alimento alimento, int cantidad, String nombre) {
        Alimento alimentoNuevo = new Alimento();
        alimentoNuevo.setNombre(nombre);
        alimentoNuevo.setBaseAzucar(alimento.getAzucar());
        alimentoNuevo.setBaseCarbohidratos(alimento.getCarbohidratos());
        alimentoNuevo.setBaseColesterol(alimento.getColesterol());
        alimentoNuevo.setBaseCalorias(alimento.getCalorias());
        alimentoNuevo.setBaseEnergia(alimento.getEnergia());
        alimentoNuevo.setBaseFibra(alimento.getFibra());
        alimentoNuevo.setBaseGrasa(alimento.getGrasa());
        alimentoNuevo.setBaseGrasaMonoinsaturada(alimento.getGrasaMonoinsaturada());
        alimentoNuevo.setBaseGrasaPoliinsaturada(alimento.getGrasaPoliinsaturada());
        alimentoNuevo.setBaseGrasaSaturada(alimento.getGrasaSaturada());
        alimentoNuevo.setImagen(alimento.getImagen());
        alimentoNuevo.setBasePotasio(alimento.getPotasio());
        alimentoNuevo.setBaseProteina(alimento.getProteina());
        alimentoNuevo.setBaseSodio(alimento.getSodio());
        alimentoNuevo.setCantidad(cantidad);
        alimentoNuevo.actualizarValoresNutricionalesPorCantidad();
        alimentoNuevo.setEsPersonalizado(true);

        return alimentoNuevo;
    }
}
