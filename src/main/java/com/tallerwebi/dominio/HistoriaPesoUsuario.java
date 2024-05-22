package com.tallerwebi.dominio;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class HistoriaPesoUsuario {

    @Id
    private Long id;
    @ManyToOne
    Usuario usuario;

    Double peso;
    Date fecha;

    public HistoriaPesoUsuario() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
