package com.tallerwebi.infraestructura;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.Publicacion;
import com.tallerwebi.dominio.PublicacionLike;
import com.tallerwebi.dominio.PublicacionRespuesta;
import com.tallerwebi.dominio.RepositorioComunidad;
import com.tallerwebi.dominio.Respuesta;
import com.tallerwebi.dominio.Usuario;

import java.util.List;

@Repository("repositorioComunidad")
public class RepositorioComunidadImpl implements RepositorioComunidad {

    private SessionFactory sessionFactory;
    
    @Autowired
    public RepositorioComunidadImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardarPublicacion(Publicacion publicacion) {
        Session session = sessionFactory.getCurrentSession();
        session.save(publicacion);
    }

    @Override
    public void guardarRespuesta(Respuesta respuesta) {
        Session session = sessionFactory.getCurrentSession();
        session.save(respuesta);
    }

    @Override
    public void guardarPublicacionRespuesta(PublicacionRespuesta publicacionRespuesta) {
        Session session = sessionFactory.getCurrentSession();
        session.save(publicacionRespuesta);
    }

    @Override
    public void guardarPublicacionLike(PublicacionLike publicacionLike) {
        Session session = sessionFactory.getCurrentSession(); 
        session.save(publicacionLike);
    }

    @Override
    public void borrarPublicacionLike(PublicacionLike publicacionLike) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(publicacionLike);
    }

    @Override
    public Usuario obtenerUsuarioPorId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Usuario.class, id);
    }

    @Override
    public Publicacion obtenerPublicacionPorId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Publicacion.class, id);
    }

    @Override
    public PublicacionLike obtenerPublicacionLikePorIdPublicacionYUsuario(Long idPublicacion, Long idUsuario) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM PublicacionLike pl WHERE pl.publicacion.id = :idPublicacion AND pl.usuario.id = :idUsuario", PublicacionLike.class)
                .setParameter("idPublicacion", idPublicacion)
                .setParameter("idUsuario", idUsuario)
                .uniqueResult();
    }

    @Override
    public List<Publicacion> obtenerPublicaciones() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Publicacion p ORDER BY p.fechaCreacion DESC", Publicacion.class).list();
    }

    @Override
    public List<Publicacion> obtenerPublicacionesPorUsuario(Long idUsuario) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Publicacion p WHERE p.usuario.id = :idUsuario ORDER BY p.fechaCreacion DESC", Publicacion.class)
                        .setParameter("idUsuario", idUsuario)
                        .list();
    }

    @Override
    public List<Publicacion> obtenerPublicacionesPorBusqueda(String busqueda) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("FROM Publicacion p WHERE LOWER(p.titulo) LIKE LOWER(:busqueda) ORDER BY p.fechaCreacion DESC", Publicacion.class)
                        .setParameter("busqueda", "%"+busqueda+"%")
                        .list();
    }

    @Override
    public List<Respuesta> obtenerRespuestasPorPublicacion(Long idPublicacion) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT pr.respuesta FROM PublicacionRespuesta pr WHERE pr.publicacion.id = :idPublicacion ORDER BY pr.respuesta.fechaCreacion ASC", Respuesta.class)
                        .setParameter("idPublicacion", idPublicacion)
                        .list();
    }

    @Override
    public List<Publicacion> obtenerPublicacionLikesPorUsuario(Long idUsuario) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT pl.publicacion FROM PublicacionLike pl WHERE pl.usuario.id = :idUsuario", Publicacion.class)
                        .setParameter("idUsuario", idUsuario)
                        .list();
    }

    @Override
    public List<Usuario> obtenerPublicacionLikesPorPublicacion(Long idPublicacion) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT pl.usuario FROM PublicacionLike pl WHERE pl.publicacion.id = :idPublicacion", Usuario.class)
                        .setParameter("idPublicacion", idPublicacion)
                        .list();
    }

}
