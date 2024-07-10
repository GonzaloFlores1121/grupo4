package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class PublicacionRespuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Publicacion publicacion;
    @ManyToOne
    private Respuesta respuesta;

    public PublicacionRespuesta() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Publicacion getPublicacion() {return publicacion;}
    public void setPublicacion(Publicacion publicacion) {this.publicacion = publicacion;}

    public Respuesta getRespuesta() {return respuesta;}
    public void setRespuesta(Respuesta respuesta) {this.respuesta = respuesta;};

}
