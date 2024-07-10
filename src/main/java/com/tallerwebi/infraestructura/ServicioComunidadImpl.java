package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Publicacion;
import com.tallerwebi.dominio.PublicacionLike;
import com.tallerwebi.dominio.PublicacionRespuesta;
import com.tallerwebi.dominio.RepositorioComunidad;
import com.tallerwebi.dominio.Respuesta;
import com.tallerwebi.dominio.ServicioComunidad;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.PublicacionNoExistente;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ServicioComunidadImpl implements ServicioComunidad {

    private RepositorioComunidad repositorioComunidad;

    @Autowired
    public ServicioComunidadImpl(RepositorioComunidad repositorioComunidad) {
        this.repositorioComunidad = repositorioComunidad;
    }

    @Override
    public void subirPublicacion(Usuario usuario, String titulo, String contenido, MultipartFile img) throws IOException {
        Publicacion publicacion = new Publicacion();
        publicacion.setUsuario(usuario);
        publicacion.setTitulo(titulo);
        publicacion.setContenido(contenido);
        publicacion.setFechaCreacion(LocalDateTime.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        publicacion.setFechaFormateada(publicacion.getFechaCreacion().format(formatter));
        String directorioImagenes = "src/main/webapp/resources/core/img/publicaciones";
        Files.createDirectories(Paths.get(directorioImagenes));
        String nombreImagen = LocalDateTime.now().toString().replaceAll("[:.]", "-") + "_" + img.getOriginalFilename();
        String rutaCompleta = Paths.get(directorioImagenes, nombreImagen).toString();
        img.transferTo(new File(rutaCompleta));
        publicacion.setImg(nombreImagen);
        repositorioComunidad.guardarPublicacion(publicacion);
    }

    @Override
    public void responderPublicacion(Publicacion publicacion, Usuario usuario, String contenido) {
        Respuesta respuesta = new Respuesta();
        respuesta.setUsuario(usuario);
        respuesta.setContenido(contenido);
        respuesta.setFechaCreacion(LocalDateTime.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        respuesta.setFechaFormateada(respuesta.getFechaCreacion().format(formatter));
        repositorioComunidad.guardarRespuesta(respuesta);
        PublicacionRespuesta publicacionRespuesta = new PublicacionRespuesta();
        publicacionRespuesta.setPublicacion(publicacion);
        publicacionRespuesta.setRespuesta(respuesta);
        repositorioComunidad.guardarPublicacionRespuesta(publicacionRespuesta);
    }

    @Override
    public void darLike(Publicacion publicacion, Usuario usuario) {
        PublicacionLike like = new PublicacionLike();
        like.setPublicacion(publicacion);
        like.setUsuario(usuario);
        like.setFechaCreacion(LocalDateTime.now());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        like.setFechaFormateada(like.getFechaCreacion().format(formatter));
        repositorioComunidad.guardarPublicacionLike(like);
    }

    @Override
    public void quitarLike(PublicacionLike like) {
        repositorioComunidad.borrarPublicacionLike(like);
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) throws UsuarioNoExistente {
        Usuario usuario = repositorioComunidad.obtenerUsuarioPorId(id);
        if (usuario == null) {throw new UsuarioNoExistente();}
        return usuario;
    }

    @Override
    public Publicacion obtenerPublicacionPorId(Long id) throws PublicacionNoExistente {
        Publicacion publicacion = repositorioComunidad.obtenerPublicacionPorId(id);
        if (publicacion == null) {throw new PublicacionNoExistente();}
        return publicacion;
    }

    @Override
    public PublicacionLike obtenerLikePorIdPublicacionYUsuario(Long idPublicacion, Long idUsuario) {
        return repositorioComunidad.obtenerPublicacionLikePorIdPublicacionYUsuario(idPublicacion, idUsuario);
    }

    @Override
    public List<Publicacion> obtenerPublicacionesSubidas() {
        List<Publicacion> publicaciones = repositorioComunidad.obtenerPublicaciones();
        return publicaciones;
    }

    @Override
    public List<Publicacion> obtenerPublicacionesSubidasPorUsuario(Long idUsuario) {
        List<Publicacion> publicaciones = repositorioComunidad.obtenerPublicacionesPorUsuario(idUsuario);
        return publicaciones;
    }

    @Override
    public List<Publicacion> obtenerPublicacionesSubidasPorBusqueda(String busqueda) {
        List<Publicacion> publicaciones = repositorioComunidad.obtenerPublicacionesPorBusqueda(busqueda);
        return publicaciones;
    }

    @Override
    public Map<Long, List<Respuesta>> obtenRespuestasPorPublicacionSubida(List<Publicacion> publicaciones) {
        Map<Long, List<Respuesta>> respuestasPorPublicacion = new HashMap<>();
        for (Publicacion publicacion : publicaciones) {
            List<Respuesta> respuestas = repositorioComunidad.obtenerRespuestasPorPublicacion(publicacion.getId());
            respuestasPorPublicacion.put(publicacion.getId(), respuestas);
        }
        return respuestasPorPublicacion;
    }

    @Override
    public Map<Long, Boolean> obtenerLikesPorUsuario(Long idUsuario) {
        return repositorioComunidad.obtenerPublicacionLikesPorUsuario(idUsuario).stream()
            .collect(Collectors.toMap(Publicacion::getId, publicacion -> true));
    }

    @Override
    public Map<Long, String> obtenerLikesPorPublicacion(List<Publicacion> publicaciones) {
        return publicaciones.stream()
            .collect(Collectors.toMap(
                Publicacion::getId,
                publicacion -> repositorioComunidad.obtenerPublicacionLikesPorPublicacion(publicacion.getId()).stream()
                    .map(Usuario::getNombre)
                    .collect(Collectors.joining("<br>"))
            ));
    }
    
}
