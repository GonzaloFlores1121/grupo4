package com.tallerwebi.dominio;


import javax.persistence.*;
import java.util.List;

@Entity
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String imagen;

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


    @ManyToOne(optional = true)
    @JoinColumn(name = "colacion_id", nullable = true)
    private Colacion colacion;
    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaAlimento categoria;

    public Alimento() {}

    public void actualizarValoresNutricionalesPorCantidad() {
        this.calorias *= cantidad;
        this.energia *= cantidad;
        this.carbohidratos *= cantidad;
        this.azucar *= cantidad;
        this.proteina *= cantidad;
        this.grasa *= cantidad;
        this.grasaSaturada *= cantidad;
        this.grasaPoliinsaturada *= cantidad;
        this.grasaMonoinsaturada *= cantidad;
        this.colesterol *= cantidad;
        this.fibra *= cantidad;
        this.sodio *= cantidad;
        this.potasio *= cantidad;
    }


    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getImagen() {return imagen;}
    public void setImagen(String imagen) {this.imagen = imagen;}

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

    public Integer getPotasio() {
        return potasio;
    }

    public void setPotasio(Integer potasio) {
        this.potasio = potasio;
    }

    public Double getGrasa() {
        return grasa;
    }

    public void setGrasa(Double grasa) {
        this.grasa = grasa;
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

    public Colacion getColacion() {return colacion;}
    public void setColacion(Colacion colacion) {this.colacion = colacion;}

    public CategoriaAlimento getCategoria() {return categoria;}
    public void setCategoria(CategoriaAlimento categoria) {this.categoria = categoria;}

}
