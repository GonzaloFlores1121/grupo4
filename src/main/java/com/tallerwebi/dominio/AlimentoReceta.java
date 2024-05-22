package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class AlimentoReceta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alimento_id")
    private Alimento alimento;

    @ManyToOne
    @JoinColumn(name = "receta_id")
    private Receta receta;

    public AlimentoReceta() {
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Alimento getAlimento() {
        return alimento;
    }

    public void setAlimento(Alimento alimento) {
        this.alimento = alimento;
    }

    public Receta getReceta() {
        return receta;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }
}