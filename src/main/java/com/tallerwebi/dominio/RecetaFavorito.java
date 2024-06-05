package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class RecetaFavorito {

    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Usuario usuario;

    @ManyToOne
    private Receta recetasFavoritas;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String imagen;

    @Column(nullable = false,length = 100)
    private String descripcion;

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Receta getRecetasFavoritas() {
        return recetasFavoritas;
    }

    public void setRecetasFavoritas(Receta recetasFavoritas) {
        this.recetasFavoritas = recetasFavoritas;
    }
}
