package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.PublicacionNoExistente;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        return publicaciones;
    }

    @Override
    public List<Publicacion> todasLasPublicacionesSubidasPorUnUsuario(Long id) {
        List<Publicacion> publicacionesUsuario = new ArrayList<>();
        publicacionesUsuario=repositorioComunidad.obtenerTodasLasPublicacionesDeUnUsuario(id);
        return publicacionesUsuario;
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario obtenerUsuarioPorId(Long id) throws UsuarioNoExistente {
        Usuario usuario = repositorioComunidad.consultarUsuario(id);
        if (usuario == null) {
            throw new UsuarioNoExistente();
        }
        return usuario;
    }

    @Override
    public Publicacion obtenerPublicacionPorId(Long id) throws PublicacionNoExistente {
        Publicacion publicacion = repositorioComunidad.consultarPublicacion(id);
        if(publicacion == null) {throw new PublicacionNoExistente();}
        return publicacion;
    }
}
