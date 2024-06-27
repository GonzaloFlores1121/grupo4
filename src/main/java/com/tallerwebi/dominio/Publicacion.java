package com.tallerwebi.dominio;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Publicacion implements Comparable<Publicacion> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Usuario usuario;
    private String texto;
    private String rutaImagen;
    private LocalDateTime fechaHora;
    private String fechaFormateada;

    public Publicacion( Usuario usuario, String texto, String rutaImagen, LocalDateTime fechaHora) {
        this.usuario = usuario;
        this.texto = texto;
        this.rutaImagen = rutaImagen;
        this.fechaHora = fechaHora;
    }

    public Publicacion() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Usuario getUsuario() {return usuario;}
    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

    public String getTexto() {return texto;}
    public void setTexto(String texto) {this.texto = texto;}

    public String getRutaImagen () {return rutaImagen;}
    public void setRutaImagen(String rutaImagen) {this.rutaImagen = rutaImagen;}

    public LocalDateTime getFechaHora() {return fechaHora;}
    public void setFechaHora(LocalDateTime fechaHora) {this.fechaHora = fechaHora;}

    @Override
    public int compareTo(Publicacion otraPublicacion) {return otraPublicacion.getFechaHora().compareTo(this.fechaHora);}

    public String getFechaFormateada() {return fechaFormateada;}
    public void setFechaFormateada(String fechaFormateada) {this.fechaFormateada = fechaFormateada;}
    
}
