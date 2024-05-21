package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ConfiguracionUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Boolean recibirNotificaciones;
    private String unidadEnergia;
    private String unidadMasa;

    public ConfiguracionUsuario() {}

    public Long getId() {return id;}
    public void setId(Long id) {this.id = id;}

    public Boolean getRecibirNotificaciones() {return recibirNotificaciones;}
    public void setRecibirNotificaciones(Boolean recibirNotificaciones) {this.recibirNotificaciones = recibirNotificaciones;}

    public String getUnidadEnergia() {return unidadEnergia;}
    public void setUnidadEnergia(String unidadEnergia) {this.unidadEnergia = unidadEnergia;}

    public String getUnidadMasa() {return unidadMasa;}
    public void setUnidadMasa(String unidadMasa) {this.unidadMasa = unidadMasa;}

}
