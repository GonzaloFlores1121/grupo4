package com.tallerwebi.dominio;

import javax.persistence.*;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Set;

@Entity
public class Colacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TipoColacion tipo;

    @ManyToOne
    private Usuario usuario;

    @ManyToOne
    private Alimento alimentos;

    private LocalDate fecha;

    public Colacion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoColacion getTipo() {
        return tipo;
    }

    public void setTipo(TipoColacion tipo) {
        this.tipo = tipo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Alimento getAlimentos() {
        return alimentos;
    }

    public void setAlimentos(Alimento alimentos) {
        this.alimentos = alimentos;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}