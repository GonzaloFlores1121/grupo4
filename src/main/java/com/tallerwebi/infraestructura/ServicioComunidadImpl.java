package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Publicacion;
import com.tallerwebi.dominio.RepositorioComunidad;
import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ServicioComunidadImpl implements ServicioComunidad {


    private RepositorioComunidad repositorioComunidad;
    @Autowired
    public ServicioComunidadImpl(RepositorioComunidad repositorioComunidad) {
        this.repositorioComunidad = repositorioComunidad;
    }

    @Override
    public void subirPublicacion(Usuario usuario, String imagen, String mensaje) {
        LocalDateTime fechaHoraActual = LocalDateTime.now();
        Publicacion publicacion = new Publicacion(usuario, mensaje,imagen, fechaHoraActual);
        repositorioComunidad.guardarPublicacion(publicacion);
    }

    @Override
    public List<Publicacion> todasLasPublicacionesSubidas() {
    List<Publicacion> publicaciones = new ArrayList<>();
    publicaciones=repositorioComunidad.obtenerTodasLasPublicaciones();
        publicaciones.sort((p1, p2) -> p1.getFechaHora().compareTo(p2.getFechaHora()));
   return publicaciones;
    }
}
