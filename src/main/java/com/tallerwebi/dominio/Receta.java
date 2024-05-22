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

    @Column(nullable = true)
    private String tamanoPorcion;

    @Column(nullable = true)
    private String energia;

    @Column(nullable = true)
    private String carbohidratos;

    @Column(nullable = true)
    private String azucar;

    @Column(nullable = true)
    private String proteina;

    @Column(nullable = true)
    private String grasa;

    @Column(nullable = true)
    private String grasaSaturada;

    @Column(nullable = true)
    private String grasaPoliinsaturada;

    @Column(nullable = true)
    private String grasaMonoinsaturada;

    @Column(nullable = true)
    private String colesterol;

    @Column(nullable = true)
    private String fibra;

    @Column(nullable = true)
    private String sodio;

    @Column(nullable = true)
    private String potasio;

    @OneToMany(mappedBy = "receta", fetch = FetchType.EAGER)
    private List<AlimentoReceta> alimentoRecetas;
    // Getters y Setters


    public Receta() {
    }

    public String getAzucar() {
        return azucar;
    }

    public void setAzucar(String azucar) {
        this.azucar = azucar;
    }

    public String getPotasio() {
        return potasio;
    }

    public void setPotasio(String potasio) {
        this.potasio = potasio;
    }

    public String getSodio() {
        return sodio;
    }

    public void setSodio(String sodio) {
        this.sodio = sodio;
    }

    public String getFibra() {
        return fibra;
    }

    public void setFibra(String fibra) {
        this.fibra = fibra;
    }

    public String getColesterol() {
        return colesterol;
    }

    public void setColesterol(String colesterol) {
        this.colesterol = colesterol;
    }

    public String getGrasaMonoinsaturada() {
        return grasaMonoinsaturada;
    }

    public void setGrasaMonoinsaturada(String grasaMonoinsaturada) {
        this.grasaMonoinsaturada = grasaMonoinsaturada;
    }

    public String getGrasaPoliinsaturada() {
        return grasaPoliinsaturada;
    }

    public void setGrasaPoliinsaturada(String grasaPoliinsaturada) {
        this.grasaPoliinsaturada = grasaPoliinsaturada;
    }

    public String getGrasaSaturada() {
        return grasaSaturada;
    }

    public void setGrasaSaturada(String grasaSaturada) {
        this.grasaSaturada = grasaSaturada;
    }

    public String getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(String carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    public String getProteina() {
        return proteina;
    }

    public void setProteina(String proteina) {
        this.proteina = proteina;
    }

    public String getGrasa() {
        return grasa;
    }

    public void setGrasa(String grasa) {
        this.grasa = grasa;
    }

    public String getEnergia() {
        return energia;
    }

    public void setEnergia(String energia) {
        this.energia = energia;
    }

    public String getTamanoPorcion() {
        return tamanoPorcion;
    }

    public void setTamanoPorcion(String tamanoPorcion) {
        this.tamanoPorcion = tamanoPorcion;
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

    public List<AlimentoReceta> getAlimentoRecetas() {
        return alimentoRecetas;
    }

    public void setAlimentoRecetas(List<AlimentoReceta> alimentoRecetas) {
        this.alimentoRecetas = alimentoRecetas;
    }
}