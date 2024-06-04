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
