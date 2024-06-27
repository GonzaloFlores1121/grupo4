package com.tallerwebi.dominio;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

        @Entity
        @Table(name = "Receta")
        public class Receta {

            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;

            @Column(nullable = false)
            private String nombre;

            @Column(nullable = false)
            private String imagen;

            @Column(nullable = false,length = 100)
            private String descripcion;

            @Column(nullable = false,length = 4000)
            private String ingredientes;

            @Column(nullable = false,length = 4000)
            private String instrucciones;

            @Column(nullable = false, columnDefinition = "integer default 1")
            private Integer cantidad;

            private Integer energia; // kcal
            private Integer fibra; // g
            private Integer calorias;
            private Integer colesterol; // mg
            private Integer sodio; // mg
            private Integer potasio; // mg
            private Double grasa; // g
            private Double grasaSaturada;
            private Double grasaPoliinsaturada;
            private Double grasaMonoinsaturada;
            private Double carbohidratos; // g
            private Double azucar; // g
            private Double proteina; // g

            @ManyToOne
            private Usuario usuario;
            // Getters y Setters


            public Receta() {
            }

            public Integer getCantidad() {
                return cantidad;
            }

            public void setCantidad(Integer cantidad) {
                this.cantidad = cantidad;
            }

            public Integer getEnergia() {
                return energia;
            }

            public void setEnergia(Integer energia) {
                this.energia = energia;
            }

            public Integer getFibra() {
                return fibra;
            }

            public void setFibra(Integer fibra) {
                this.fibra = fibra;
            }

            public Integer getCalorias() {
                return calorias;
            }

            public void setCalorias(Integer calorias) {
                this.calorias = calorias;
            }

            public Integer getColesterol() {
                return colesterol;
            }

            public void setColesterol(Integer colesterol) {
                this.colesterol = colesterol;
            }

            public Integer getSodio() {
                return sodio;
            }

            public void setSodio(Integer sodio) {
                this.sodio = sodio;
            }

            public Double getGrasa() {
                return grasa;
            }

            public void setGrasa(Double grasa) {
                this.grasa = grasa;
            }

            public Integer getPotasio() {
                return potasio;
            }

            public void setPotasio(Integer potasio) {
                this.potasio = potasio;
            }

            public Double getGrasaSaturada() {
                return grasaSaturada;
            }

            public void setGrasaSaturada(Double grasaSaturada) {
                this.grasaSaturada = grasaSaturada;
            }

            public Double getGrasaPoliinsaturada() {
                return grasaPoliinsaturada;
            }

            public void setGrasaPoliinsaturada(Double grasaPoliinsaturada) {
                this.grasaPoliinsaturada = grasaPoliinsaturada;
            }

            public Double getGrasaMonoinsaturada() {
                return grasaMonoinsaturada;
            }

            public void setGrasaMonoinsaturada(Double grasaMonoinsaturada) {
                this.grasaMonoinsaturada = grasaMonoinsaturada;
            }

            public Double getCarbohidratos() {
                return carbohidratos;
            }

            public void setCarbohidratos(Double carbohidratos) {
                this.carbohidratos = carbohidratos;
            }

            public Double getAzucar() {
                return azucar;
            }

            public void setAzucar(Double azucar) {
                this.azucar = azucar;
            }

            public Double getProteina() {
                return proteina;
            }

            public void setProteina(Double proteina) {
                this.proteina = proteina;
            }

            public String getInstrucciones() {
        return instrucciones;
    }

    public void setInstrucciones(String instrucciones) {
        this.instrucciones = instrucciones;
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

            public Usuario getUsuario() {
                return usuario;
            }

            public void setUsuario(Usuario usuario) {
                this.usuario = usuario;
            }
        }