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
    private String tamanoPorcion;
    private String energia;
    private String carbohidratos;
    private String azucar;
    private String proteina;
    private String grasa;
    private String grasaSaturada;
    private String grasaPoliinsaturada;
    private String grasaMonoinsaturada;
    private String colesterol;
    private String fibra;
    private String sodio;
    private String potasio;

    @OneToMany(mappedBy = "alimento")
    private List<AlimentoReceta> alimentoRecetas;

    @ManyToOne(optional = true)
    @JoinColumn(name = "colacion_id", nullable = true)
    private Colacion colacion;

    @ManyToOne
    //en el insert se debe enviar el id de la categoria a la que pertenece el alimento
    @JoinColumn(name = "categoria_id")
    private CategoriaAlimento categoria;

    // getters y setters


    public Alimento() {
    }

    public List<AlimentoReceta> getAlimentoRecetas() {
        return alimentoRecetas;
    }

    public void setAlimentoRecetas(List<AlimentoReceta> alimentoRecetas) {
        this.alimentoRecetas = alimentoRecetas;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CategoriaAlimento getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaAlimento categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getTamanoPorcion() {
        return tamanoPorcion;
    }

    public void setTamanoPorcion(String tamanoPorcion) {
        this.tamanoPorcion = tamanoPorcion;
    }

    public String getEnergia() {
        return energia;
    }

    public void setEnergia(String energia) {
        this.energia = energia;
    }

    public String getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(String carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    public String getAzucar() {
        return azucar;
    }

    public void setAzucar(String azucar) {
        this.azucar = azucar;
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

    public String getGrasaSaturada() {
        return grasaSaturada;
    }

    public void setGrasaSaturada(String grasaSaturada) {
        this.grasaSaturada = grasaSaturada;
    }

    public String getGrasaPoliinsaturada() {
        return grasaPoliinsaturada;
    }

    public void setGrasaPoliinsaturada(String grasaPoliinsaturada) {
        this.grasaPoliinsaturada = grasaPoliinsaturada;
    }

    public String getGrasaMonoinsaturada() {
        return grasaMonoinsaturada;
    }

    public void setGrasaMonoinsaturada(String grasaMonoinsaturada) {
        this.grasaMonoinsaturada = grasaMonoinsaturada;
    }

    public String getColesterol() {
        return colesterol;
    }

    public void setColesterol(String colesterol) {
        this.colesterol = colesterol;
    }

    public String getFibra() {
        return fibra;
    }

    public void setFibra(String fibra) {
        this.fibra = fibra;
    }

    public String getSodio() {
        return sodio;
    }

    public void setSodio(String sodio) {
        this.sodio = sodio;
    }

    public String getPotasio() {
        return potasio;
    }

    public void setPotasio(String potasio) {
        this.potasio = potasio;
    }

    public Colacion getColacion() {
        return colacion;
    }

    public void setColacion(Colacion colacion) {
        this.colacion = colacion;
    }
}