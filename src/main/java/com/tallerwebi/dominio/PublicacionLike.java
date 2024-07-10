package com.tallerwebi.dominio;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PublicacionLike implements Comparable<PublicacionLike>  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime fechaCreacion;
    private String fechaFormateada;
    @ManyToOne
    private Publicacion publicacion;
    @ManyToOne
    private Usuario usuario;

    public PublicacionLike() {}

    @Override
    public int compareTo(PublicacionLike otraPublicacionLike) {return otraPublicacionLike.getFechaCreacion().compareTo(this.fechaCreacion);}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public LocalDateTime getFechaCreacion() {return fechaCreacion;}
    public void setFechaCreacion(LocalDateTime fechaCreacion) {this.fechaCreacion = fechaCreacion;}

    public String getFechaFormateada() {return fechaFormateada;}
    public void setFechaFormateada(String fechaFormateada) {this.fechaFormateada = fechaFormateada;}

    public Publicacion getPublicacion() {return publicacion;}
    public void setPublicacion(Publicacion publicacion) {this.publicacion = publicacion;}

    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

}
