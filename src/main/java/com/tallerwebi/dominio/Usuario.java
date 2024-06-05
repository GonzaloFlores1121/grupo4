package com.tallerwebi.dominio;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String password;
    private Integer edad;
    private String genero;   
    private Double altura;
    private Double peso;      
    private Double metaAlcanzarPeso;
    private String nivelDeActividad;    
    private Integer ingestaCalorica;
    private String imagen;
    @OneToOne
    private ConfiguracionUsuario configuracionUsuario;
    @OneToMany(mappedBy = "usuario")
    private Set<Colacion> colaciones;

    public Usuario() {}
    
    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public String getNombre() {return nombre;}
    public void setNombre(String nombre) {this.nombre = nombre;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}  

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public void setEdad(Integer edad){this.edad = edad;}
    public Integer getEdad(){return edad;}    

    public String getGenero() {return genero;}
    public void setGenero(String genero) {this.genero = genero;}

    public Double getAltura() {return altura;}
    public Double setAltura(Double altura) {return this.altura = altura;} 
    
    public Double getPeso() {return peso;}
    public void setPeso(Double peso) {this.peso = peso;}  

    public Double getMetaAlcanzarPeso() {return metaAlcanzarPeso;}
    public void setMetaAlcanzarPeso(Double metaAlcanzarPeso) {this.metaAlcanzarPeso = metaAlcanzarPeso;}    
    
    public String getNivelDeActividad() {return nivelDeActividad;}
    public void setNivelDeActividad(String nivelDeActividad) {this.nivelDeActividad = nivelDeActividad;}

    public Integer getIngestaCalorica() {return ingestaCalorica;}
    public void setIngestaCalorica(Integer ingestaCalorica) {this.ingestaCalorica = ingestaCalorica;}

    public String getImagen() {return imagen;}
    public void setImagen(String imagen) {this.imagen = imagen;}

    public ConfiguracionUsuario getConfiguracionUsuario() {return configuracionUsuario;}
    public void setConfiguracionUsuario(ConfiguracionUsuario configuracionUsuario) {this.configuracionUsuario = configuracionUsuario;}

    public Set<Colacion> getColaciones() {return colaciones;}
    public void setColaciones(Set<Colacion> colaciones) {this.colaciones = colaciones;}

}
