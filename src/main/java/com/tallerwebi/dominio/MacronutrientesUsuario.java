package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class MacronutrientesUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private Usuario usuario;
    private Integer grasaAConsumir;
    private Integer proteinaAConsumir;
    private Integer carbohidratosAConsumir;


    public MacronutrientesUsuario(Usuario usuario){
        this.usuario = usuario;
        this.grasaAConsumir = getGrasaAConsumir();
        this.proteinaAConsumir = getProteinaAConsumir();
        this.carbohidratosAConsumir = getCarbohidratosAConsumir();
    }


    public MacronutrientesUsuario() {
    }

    public Usuario getUsuario() {return usuario;}

    public void setUsuario(Usuario usuario) {this.usuario = usuario;}

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


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}