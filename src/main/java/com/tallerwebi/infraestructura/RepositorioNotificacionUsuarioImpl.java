package com.tallerwebi.infraestructura;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.NotificacionUsuario;
import com.tallerwebi.dominio.RepositorioNotificacionUsuario;

@Repository("repositorioNotificacionUsuario")
@Transactional
public class RepositorioNotificacionUsuarioImpl implements RepositorioNotificacionUsuario {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioNotificacionUsuarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void save(NotificacionUsuario notificacionUsuario) {
        Session session = sessionFactory.getCurrentSession();
        session.save(notificacionUsuario);
    }

    @Override
    public NotificacionUsuario get(Long idNotificacion, Long idUsuario) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from NotificacionUsuario nu where nu.notificacion.id=:idNotificacion and nu.usuario.id=:idUsuario", NotificacionUsuario.class)
                                    .setParameter("idNotificacion", idNotificacion)
                                    .setParameter("idUsuario", idUsuario)
                                    .uniqueResult();
    }

    @Override
    public List<NotificacionUsuario> getAll() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from NotificacionUsuario", NotificacionUsuario.class).list();
    }

    @Override
    public List<Notificacion> getAllNotification(Long idUsuario) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select notificacion from NotificacionUsuario nu where nu.usuario.id=:idUsuario", Notificacion.class).setParameter("idUsuario", idUsuario).list();
    }

    @Override
    public void update(NotificacionUsuario notificacionUsuario) {
        Session session = sessionFactory.getCurrentSession();
        session.update(notificacionUsuario);
    }

    @Override
    public void delete(NotificacionUsuario notificacionUsuario) {
        Session session = sessionFactory.getCurrentSession();
        session.delete(notificacionUsuario);
    }

    @Override
    public List<NotificacionUsuario> getNotificacionesNoLeidas(Long idUsuario) {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select notificacion from NotificacionUsuario nu where nu.usuario.id=:idUsuario AND nu.leida=false ", NotificacionUsuario.class).setParameter("idUsuario", idUsuario).list();
    }

}
