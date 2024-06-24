package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PublicacionLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Publicacion publicacion;
    @ManyToOne
    private Usuario usuario;

    public PublicacionLike() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Publicacion getuPublicacion() {return publicacion;}
    public void setPublicacion(Publicacion publicacion) {this.publicacion = publicacion;}

    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

}
