package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String rol;
    private Boolean activo = false;
    private String genero;
    private String nivelDeActividad;
    private Double peso;
    private Double altura;
    private Integer edad;
    private Integer ingestaCalorica;


    public Usuario() {

    }

    public void setEdad(Integer edad){this.edad=edad;}
    public Integer getEdad(){return edad;}
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRol() {
        return rol;
    }
    public void setRol(String rol) {
        this.rol = rol;
    }
    public Boolean getActivo() {
        return activo;
    }
    public void setActivo(Boolean activo) {
        this.activo = activo;
    }
    public String getGenero() {
        return genero;
    }
    public void setGenero(String genero) {
        this.genero = genero;
    }
    public String getNivelDeActividad() {
        return nivelDeActividad;
    }
    public void setNivelDeActividad(String nivelDeActividad) {
        this.nivelDeActividad = nivelDeActividad;
    }
    public Double getPeso() {
        return peso;
    }
    public void setPeso(Double peso) {
        this.peso = peso;
    }
    public Double getAltura() {
        return altura;
    }
    public Double setAltura(Double altura) {
        return this.altura=altura;
    }
    public Integer getIngestaCalorica() {
        return ingestaCalorica;
    }
    public void setIngestaCalorica(Integer ingestaCalorica) {
        this.ingestaCalorica = ingestaCalorica;
    }
    public void activar() {
        activo = true;
    }
}
