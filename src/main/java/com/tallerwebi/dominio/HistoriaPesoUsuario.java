package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;


public class HistoriaPesoUsuario {

  Map<Date, Double>pesoUsuario;

    public HistoriaPesoUsuario() {
        this.pesoUsuario = new HashMap<Date, Double>();
    }

    public Map<Date, Double> getPesoUsuario() {
        return pesoUsuario;
    }

    public void add(Date fecha, Double peso) {
        this.pesoUsuario.put(fecha, peso);
    }
}
