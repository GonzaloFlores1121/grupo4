package com.tallerwebi.dominio;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Respuesta implements Comparable<Respuesta> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contenido;
    private LocalDateTime fechaCreacion;
    private String fechaFormateada;
    @ManyToOne
    private Usuario usuario;

    public Respuesta() {}

    @Override
    public int compareTo(Respuesta otraRespuesta) {return otraRespuesta.getFechaCreacion().compareTo(this.fechaCreacion);}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getContenido() {return contenido;}
    public void setContenido(String contenido) {this.contenido = contenido;}

    public LocalDateTime getFechaCreacion() {return fechaCreacion;}
    public void setFechaCreacion(LocalDateTime fechaCreacion) {this.fechaCreacion = fechaCreacion;}

    public String getFechaFormateada() {return fechaFormateada;}
    public void setFechaFormateada(String fechaFormateada) {this.fechaFormateada = fechaFormateada;}  

    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

}
