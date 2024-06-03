package com.tallerwebi.dominio;


import javax.persistence.*;
import java.sql.Date;
import java.sql.Time;
@Entity
public class EjercicioUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Integer minutos;
    private Date fecha;
    private String  intensidad;
    @ManyToOne
    private Ejercicio ejercicio;
    @ManyToOne
    private Usuario usuario;

    public EjercicioUsuario( String nombre, Integer minutos, Date fecha, String intensidad, Ejercicio ejercicio, Usuario usuario) {

        this.nombre = nombre;
        this.minutos = minutos;
        this.fecha = fecha;
        this.intensidad = intensidad;
        this.ejercicio = ejercicio;
        this.usuario = usuario;
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

    public Date getFecha() {return fecha;}

    public void setFecha(Date fecha) {this.fecha = fecha;}

    public String getIntensidad() {
        return intensidad;
    }

    public void setIntensidad(String intensidad) {
        this.intensidad = intensidad;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public Ejercicio getEjercicio() {return ejercicio;}

    public void setEjercicio(Ejercicio ejercicio) {this.ejercicio = ejercicio;}

    public Usuario getUsuario() {return usuario;}

    public void setUsuario(Usuario usuario) {this.usuario = usuario;}
}
