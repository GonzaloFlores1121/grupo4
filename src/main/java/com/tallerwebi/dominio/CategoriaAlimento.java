package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.List;

@Entity
public class CategoriaAlimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String imagen;
    @OneToMany(mappedBy = "categoria", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<Alimento> alimentos;

    public CategoriaAlimento() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getImagen() {return imagen;}
    public void setImagen(String imagen) {this.imagen = imagen;}

    public List<Alimento> getAlimentos() {return alimentos;}
    public void setAlimentos(List<Alimento> alimentos) {this.alimentos = alimentos;}

}
