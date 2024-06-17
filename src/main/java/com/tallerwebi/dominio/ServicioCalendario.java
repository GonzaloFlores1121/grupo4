package com.tallerwebi.dominio;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface ServicioCalendario {

    Map<Date, Calendario> obtenerFechasCalendario(Usuario usuario);


    Integer mostrarIngestaCaloricaDelDia(Usuario usuario, Date fecha);
}
