package com.tallerwebi.dominio;


import javax.persistence.*;
import java.sql.Time;
@Entity
@Table(name = "EJERCICIO_USUARIO")
public class EjercicioUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private Integer minutos;
    private Integer dia;
    private Integer mes;
    private Integer anio;
    private String  intensidad;
    private Integer id_ejercicio;
    private Integer id_usuario;

    public EjercicioUsuario(Integer id, String nombre, Integer minutos, Integer dia, Integer mes, Integer anio, String intensidad, Integer id_ejercicio, Integer id_usuario) {
        this.id = id;
        this.nombre = nombre;
        this.minutos = minutos;
        this.dia = dia;
        this.mes = mes;
        this.anio = anio;
        this.intensidad = intensidad;
        this.id_ejercicio = id_ejercicio;
        this.id_usuario = id_usuario;
    }

    public EjercicioUsuario() {

    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getMinutos() {
        return minutos;
    }

    public void setMinutos(Integer minutos) {
        this.minutos = minutos;
    }

    public Integer getDia() {
        return dia;
    }

    public void setDia(Integer dia) {
        this.dia = dia;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public String getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(String intensidad) {
        this.intensidad = intensidad;
    }

    public Integer getId_ejercicio() {
        return id_ejercicio;
    }

    public void setId_ejercicio(Integer id_ejercicio) {
        this.id_ejercicio = id_ejercicio;
    }

    public Integer getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(Integer id_usuario) {
        this.id_usuario = id_usuario;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}
