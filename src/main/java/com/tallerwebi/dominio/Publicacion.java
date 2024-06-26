package com.tallerwebi.dominio;

import javax.persistence.*;
import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

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

    public Publicacion( Usuario usuario, String texto,    String rutaImagen  , LocalDateTime fechaHora) {
        this.usuario = usuario;
        this.texto = texto;
        this.rutaImagen = rutaImagen;
        this.fechaHora = fechaHora;
    }
    public Publicacion( ) {

    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getRutaImagen () {
        return rutaImagen;
    }

    public void setRutaImagen(String imagen) {
        this.rutaImagen = rutaImagen;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
   }
   public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
   }

    @Override
    public int compareTo(Publicacion otraPublicacion) {
        return otraPublicacion.getFechaHora().compareTo(this.fechaHora);
    }

    public String getFechaFormateada() {

        return fechaFormateada;
    }

    public void setFechaFormateada(String fechaFormateada) {
        this.fechaFormateada = fechaFormateada;
    }
}
