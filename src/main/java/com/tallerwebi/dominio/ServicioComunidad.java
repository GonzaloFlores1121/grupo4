package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.PublicacionNoExistente;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface ServicioComunidad {

    void subirPublicacion(Usuario usuario, String titulo, String contenido, MultipartFile img) throws IOException;
    void responderPublicacion(Publicacion publicacion, Usuario usuario, String contenido);
    void darLike(Publicacion publicacion, Usuario usuario);
    void quitarLike(PublicacionLike like);
    Usuario obtenerUsuarioPorId(Long id) throws UsuarioNoExistente;
    Publicacion obtenerPublicacionPorId(Long id) throws PublicacionNoExistente;
    PublicacionLike obtenerLikePorIdPublicacionYUsuario(Long idPublicacion, Long idUsuario);   
    List<Publicacion> obtenerPublicacionesSubidas();
    List<Publicacion> obtenerPublicacionesSubidasPorUsuario(Long idUsuario);
    List<Publicacion> obtenerPublicacionesSubidasPorBusqueda(String busqueda);
    Map<Long, List<Respuesta>> obtenRespuestasPorPublicacionSubida(List<Publicacion> publicaciones);
    Map<Long, Boolean> obtenerLikesPorUsuario(Long idUsuario);
    Map<Long, String> obtenerLikesPorPublicacion(List<Publicacion> publicaciones);

}
