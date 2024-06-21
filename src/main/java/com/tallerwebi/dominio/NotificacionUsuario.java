package com.tallerwebi.dominio;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class NotificacionUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaHora;
    private boolean leida;
    @ManyToOne
    private Notificacion notificacion;
    @ManyToOne
    private Usuario usuario;

    public NotificacionUsuario() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public LocalDateTime getFechaHora() {return fechaHora;}
    public void setFechaHora(LocalDateTime fechaHora) {this.fechaHora = fechaHora;}

    public Notificacion getNotificacion() {return notificacion;}
    public void setNotificacion(Notificacion notificacion) {this.notificacion = notificacion;}
    
    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

    public boolean isLeida() {return leida;}
    public void setLeida(boolean leida) {this.leida = leida;}
}
