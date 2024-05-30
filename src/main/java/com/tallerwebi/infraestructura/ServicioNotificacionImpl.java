package com.tallerwebi.infraestructura;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.NotificacionUsuario;
import com.tallerwebi.dominio.RepositorioNotificacion;
import com.tallerwebi.dominio.RepositorioNotificacionUsuario;
import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioNotificacion;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;

@Service
public class ServicioNotificacionImpl implements ServicioNotificacion {

    private RepositorioNotificacion repositorioNotificacion;
    private RepositorioNotificacionUsuario repositorioNotificacionUsuario;
    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioNotificacionImpl(RepositorioNotificacion repositorioNotificacion, RepositorioNotificacionUsuario repositorioNotificacionUsuario, RepositorioUsuario repositorioUsuario) {
        this.repositorioNotificacion = repositorioNotificacion;
        this.repositorioNotificacionUsuario = repositorioNotificacionUsuario;
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public Notificacion crearNotificacion(String titulo, String contenido) {
        Notificacion notificacion = new Notificacion();
        notificacion.setTitulo(titulo);
        notificacion.setContenido(contenido);
        repositorioNotificacion.save(notificacion);
        return notificacion;
    }

    @Override
    public void enviarNotificacion(Notificacion notificacion, LocalDateTime fechaHora, String email) throws UsuarioNoExistente{
        Usuario usuario = repositorioUsuario.buscar(email);
        if(usuario != null) {
            if(usuario.getConfiguracionUsuario().getRecibirNotificaciones()) {
                NotificacionUsuario notificacionUsuario = new NotificacionUsuario();
                notificacionUsuario.setNotificacion(notificacion);
                notificacionUsuario.setUsuario(usuario);
                notificacionUsuario.setFechaHora(fechaHora);
                repositorioNotificacionUsuario.save(notificacionUsuario); 
            }
        }else {
            throw new  UsuarioNoExistente();
        }
    }

    @Override
    public void enviarNotificaciones(Notificacion notificacion, LocalDateTime fechaHora) {
        List<Usuario> usuarios = repositorioUsuario.buscarTodos();
        for (Usuario usuario : usuarios) {
            if(usuario.getConfiguracionUsuario().getRecibirNotificaciones()) {
                NotificacionUsuario notificacionUsuario = new NotificacionUsuario();
                notificacionUsuario.setNotificacion(notificacion);
                notificacionUsuario.setUsuario(usuario);
                notificacionUsuario.setFechaHora(fechaHora);
                repositorioNotificacionUsuario.save(notificacionUsuario);                
            }
        }
    }

    @Override
    public void eliminarNotificacion(Long idNotificacion, Long idUsuario) {
        NotificacionUsuario notificacionUsuario = repositorioNotificacionUsuario.get(idNotificacion, idUsuario);
        repositorioNotificacionUsuario.delete(notificacionUsuario);
    }

    @Override
    public void eliminarNotificaciones(LocalDateTime fechaHora) {
        List<NotificacionUsuario> notificacioneUsuarios = repositorioNotificacionUsuario.getAll();
        for (NotificacionUsuario notificacionUsuario : notificacioneUsuarios) {
            if(notificacionUsuario.getFechaHora().isBefore(fechaHora)) {
                repositorioNotificacionUsuario.delete(notificacionUsuario);
            }
        }
    }

    @Override
    public List<Notificacion> obtenerNotificaciones(Long idUsuario) {
        return repositorioNotificacionUsuario.getAllNotification(idUsuario);
    }

}
