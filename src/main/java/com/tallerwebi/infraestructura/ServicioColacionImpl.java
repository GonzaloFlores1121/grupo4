package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ServicioColacionImpl implements ServicioColacion {

    private RepositorioColacion repositorioColacion;
    private RepositorioAlimento repositorioAlimento;
    private ServicioDatosUsuario servicioDatosUsuario;
    @Autowired
    public ServicioColacionImpl(RepositorioColacion repositorioColacion, RepositorioAlimento repositorioAlimento, ServicioDatosUsuario servicioDatosUsuario) {
        this.repositorioColacion = repositorioColacion;
        this.repositorioAlimento= repositorioAlimento;
        this.servicioDatosUsuario = servicioDatosUsuario;
    }

    @Override
    public void guardarColacionUsuarioDewsdeDiarioAlimentos(Alimento alimento, Usuario usuario, int cantidad, TipoColacion tipoColacion, LocalDate fecha, String nombre) throws Exception {
        Alimento alimentoNuevo = new Alimento();
        alimentoNuevo.setNombre(nombre);
        alimentoNuevo.setBaseAzucar(alimento.getBaseAzucar());
        alimentoNuevo.setBaseCarbohidratos(alimento.getBaseCarbohidratos());
        alimentoNuevo.setBaseColesterol(alimento.getBaseColesterol());
        alimentoNuevo.setBaseCalorias(alimento.getBaseCalorias());
        alimentoNuevo.setBaseEnergia(alimento.getBaseEnergia());
        alimentoNuevo.setBaseFibra(alimento.getBaseFibra());
        alimentoNuevo.setBaseGrasa(alimento.getBaseGrasa());
        alimentoNuevo.setBaseGrasaMonoinsaturada(alimento.getBaseGrasaMonoinsaturada());
        alimentoNuevo.setBaseGrasaPoliinsaturada(alimento.getBaseGrasaPoliinsaturada());
        alimentoNuevo.setBaseGrasaSaturada(alimento.getBaseGrasaSaturada());
        alimentoNuevo.setImagen(alimento.getImagen());
        alimentoNuevo.setBasePotasio(alimento.getBasePotasio());
        alimentoNuevo.setBaseProteina(alimento.getBaseProteina());
        alimentoNuevo.setBaseSodio(alimento.getBaseSodio());
        alimentoNuevo.setCantidad(cantidad);
        alimentoNuevo.actualizarValoresNutricionalesPorCantidad();
        alimentoNuevo.setEsPersonalizado(true);
        alimentoNuevo.setAlimentoOriginal(alimento.getAlimentoOriginal());
        repositorioAlimento.save(alimentoNuevo);

        Colacion colacion = new Colacion();
        colacion.setAlimentos(alimentoNuevo);
        colacion.setFecha(fecha);
        colacion.setTipo(tipoColacion);
        colacion.setUsuario(usuario);
        colacion.setCantidad(cantidad);

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
        alimento.incrementarVecesConsumido();
        repositorioAlimento.update(alimento);
        servicioDatosUsuario.verificarIngestaDelDia(usuario);
        repositorioColacion.agregarColacion(colacion);
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
        colacion.setCantidad(cantidad);

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
        alimento.incrementarVecesConsumido();
        repositorioAlimento.update(alimento);
        servicioDatosUsuario.verificarIngestaDelDia(usuario);
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
    public List<Alimento> listarALimentosRecientementeConsumidosPorElUsuario(Usuario usuario) {

        return   repositorioColacion.obtenerAlimentosRecientementeConsumidos(usuario);
    }

    @Override
    public void eliminarColacionUsuario(Alimento alimentoCopia, Usuario usuario, TipoColacion tipoColacion, LocalDate fecha) {
    Alimento original=alimentoCopia.getAlimentoOriginal();
    original.decrementarVecesConsumido();
    repositorioAlimento.update(original);
    alimentoCopia.setAlimentoOriginal(null);

   repositorioAlimento.update(alimentoCopia);
        repositorioColacion.eliminarColacion(alimentoCopia,usuario,tipoColacion,fecha);
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
        alimentoNuevo.setBaseAzucar(alimento.getBaseAzucar());
        alimentoNuevo.setBaseCarbohidratos(alimento.getBaseCarbohidratos());
        alimentoNuevo.setBaseColesterol(alimento.getBaseColesterol());
        alimentoNuevo.setBaseCalorias(alimento.getBaseCalorias());
        alimentoNuevo.setBaseEnergia(alimento.getBaseEnergia());
        alimentoNuevo.setBaseFibra(alimento.getBaseFibra());
        alimentoNuevo.setBaseGrasa(alimento.getBaseGrasa());
        alimentoNuevo.setBaseGrasaMonoinsaturada(alimento.getBaseGrasaMonoinsaturada());
        alimentoNuevo.setBaseGrasaPoliinsaturada(alimento.getBaseGrasaPoliinsaturada());
        alimentoNuevo.setBaseGrasaSaturada(alimento.getBaseGrasaSaturada());
        alimentoNuevo.setImagen(alimento.getImagen());
        alimentoNuevo.setBasePotasio(alimento.getBasePotasio());
        alimentoNuevo.setBaseProteina(alimento.getBaseProteina());
        alimentoNuevo.setBaseSodio(alimento.getBaseSodio());
        alimentoNuevo.setCantidad(cantidad);
        alimentoNuevo.actualizarValoresNutricionalesPorCantidad();
        alimentoNuevo.setEsPersonalizado(true);
        alimentoNuevo.setAlimentoOriginal(alimento);

        return alimentoNuevo;
    }
    public Colacion obtenerColacionPorId(Long id) {
       Colacion colacion= repositorioColacion.obtenerColacionPorId(id);
        return colacion;
    }

    @Override
    public Integer obtenerCaloriasTotalesDeAlimentosPorUsuarioYFecha(Usuario usuario, LocalDate fecha) {
        List <Colacion> colaciones= obtenerColacionesDelUsuarioPOrFecha(usuario, fecha);
        Integer caloriasTotales=0;
        if(colaciones ==null){
            return caloriasTotales;
        }

         for (Colacion c: colaciones){
           caloriasTotales += c.getAlimentos().getCalorias();
        }

        return caloriasTotales;
    }

    @Override
    public List<Colacion> obtenerTodasLasColacionesDelUsuario(Usuario usuario) {
        List <Colacion> colaciones= repositorioColacion.obtenerTodasLasColacionesDelUsuario(usuario);
        return colaciones;
    }

    @Override
    public List<Colacion> obtenerColacionesDelUsuarioPOrFecha(Usuario usuario, LocalDate fecha) {
        // Convertir LocalDate a Date
        Date fechaSql = Date.valueOf(fecha);

        List<Colacion> colaciones = repositorioColacion.obtenerTodasLasColacionesDelUsuarioPorFecha(usuario, fechaSql.toLocalDate());
        return colaciones;
    }


}
