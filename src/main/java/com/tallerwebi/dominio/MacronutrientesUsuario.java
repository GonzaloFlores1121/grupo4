package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class MacronutrientesUsuario {

    private Long id_usuario;
    private Integer grasaAConsumir;
    private Integer proteinaAConsumir;
    private Integer carbohidratosAConsumir;
    @Id
    private Integer id;

    public MacronutrientesUsuario(Long id_usuario) {
        this.id_usuario = id_usuario;
        this.grasaAConsumir = getGrasaAConsumir();
        this.proteinaAConsumir = getProteinaAConsumir();
        this.carbohidratosAConsumir = getCarbohidratosAConsumir();
    }

    public MacronutrientesUsuario() {

    }

    public Long getId_usuario() { return id_usuario;
    }

    public void setUsuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getGrasaAConsumir() {
        return grasaAConsumir;
    }

    public void setGrasaAConsumir(Integer grasaAConsumir) {
        this.grasaAConsumir = grasaAConsumir;
    }

    public Integer getProteinaAConsumir() {
        return proteinaAConsumir;
    }

    public void setProteinaAConsumir(Integer proteinaAConsumir) {
        this.proteinaAConsumir = proteinaAConsumir;
    }

    public Integer getCarbohidratosAConsumir() {
        return carbohidratosAConsumir;
    }

    public void setCarbohidratosAConsumir(Integer carbohidratosAConsumir) {
        this.carbohidratosAConsumir = carbohidratosAConsumir;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }
}