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
    private boolean esPersonalizado;


    private Integer baseEnergia; // kcal
    private Integer baseFibra; // g
    private Integer baseCalorias;
    private Integer baseColesterol; // mg
    private Integer baseSodio; // mg
    private Integer basePotasio; // mg
    private Double baseGrasa; // g
    private Double baseGrasaSaturada;
    private Double baseGrasaPoliinsaturada;
    private Double baseGrasaMonoinsaturada;
    private Double baseCarbohidratos; // g
    private Double baseAzucar; // g
    private Double baseProteina; // g




    @OneToMany(mappedBy = "alimentos")
    private List<Colacion> colaciones;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private CategoriaAlimento categoria;

    public Alimento() {}

    public void actualizarValoresNutricionalesPorCantidad() {
        this.calorias = this.baseCalorias * cantidad;
        this.energia = this.baseEnergia * cantidad;
        this.carbohidratos = this.baseCarbohidratos * cantidad;
        this.azucar = this.baseAzucar * cantidad;
        this.proteina = this.baseProteina * cantidad;
        this.grasa = this.baseGrasa * cantidad;
        this.grasaSaturada = this.baseGrasaSaturada * cantidad;
        this.grasaPoliinsaturada = this.baseGrasaPoliinsaturada * cantidad;
        this.grasaMonoinsaturada = this.baseGrasaMonoinsaturada * cantidad;
        this.colesterol = this.baseColesterol * cantidad;
        this.fibra = this.baseFibra * cantidad;
        this.sodio = this.baseSodio * cantidad;
        this.potasio = this.basePotasio * cantidad;
    }


    public List<Colacion> getColaciones() {
        return colaciones;
    }

    public void setColaciones(List<Colacion> colaciones) {
        this.colaciones = colaciones;
    }

    public boolean isEsPersonalizado() {
        return esPersonalizado;
    }

    public void setEsPersonalizado(boolean esPersonalizado) {
        this.esPersonalizado = esPersonalizado;
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

    public Integer getBaseEnergia() {
        return baseEnergia;
    }

    public void setBaseEnergia(Integer baseEnergia) {
        this.baseEnergia = baseEnergia;
    }

    public Integer getBaseFibra() {
        return baseFibra;
    }

    public void setBaseFibra(Integer baseFibra) {
        this.baseFibra = baseFibra;
    }

    public Integer getBaseCalorias() {
        return baseCalorias;
    }

    public void setBaseCalorias(Integer baseCalorias) {
        this.baseCalorias = baseCalorias;
    }

    public Integer getBaseColesterol() {
        return baseColesterol;
    }

    public void setBaseColesterol(Integer baseColesterol) {
        this.baseColesterol = baseColesterol;
    }

    public Integer getBaseSodio() {
        return baseSodio;
    }

    public void setBaseSodio(Integer baseSodio) {
        this.baseSodio = baseSodio;
    }

    public Integer getBasePotasio() {
        return basePotasio;
    }

    public void setBasePotasio(Integer basePotasio) {
        this.basePotasio = basePotasio;
    }

    public Double getBaseGrasa() {
        return baseGrasa;
    }

    public void setBaseGrasa(Double baseGrasa) {
        this.baseGrasa = baseGrasa;
    }

    public Double getBaseGrasaSaturada() {
        return baseGrasaSaturada;
    }

    public void setBaseGrasaSaturada(Double baseGrasaSaturada) {
        this.baseGrasaSaturada = baseGrasaSaturada;
    }

    public Double getBaseGrasaPoliinsaturada() {
        return baseGrasaPoliinsaturada;
    }

    public void setBaseGrasaPoliinsaturada(Double baseGrasaPoliinsaturada) {
        this.baseGrasaPoliinsaturada = baseGrasaPoliinsaturada;
    }

    public Double getBaseGrasaMonoinsaturada() {
        return baseGrasaMonoinsaturada;
    }

    public void setBaseGrasaMonoinsaturada(Double baseGrasaMonoinsaturada) {
        this.baseGrasaMonoinsaturada = baseGrasaMonoinsaturada;
    }

    public Double getBaseCarbohidratos() {
        return baseCarbohidratos;
    }

    public void setBaseCarbohidratos(Double baseCarbohidratos) {
        this.baseCarbohidratos = baseCarbohidratos;
    }

    public Double getBaseAzucar() {
        return baseAzucar;
    }

    public void setBaseAzucar(Double baseAzucar) {
        this.baseAzucar = baseAzucar;
    }

    public Double getBaseProteina() {
        return baseProteina;
    }

    public void setBaseProteina(Double baseProteina) {
        this.baseProteina = baseProteina;
    }

    public CategoriaAlimento getCategoria() {return categoria;}
    public void setCategoria(CategoriaAlimento categoria) {this.categoria = categoria;}

}
