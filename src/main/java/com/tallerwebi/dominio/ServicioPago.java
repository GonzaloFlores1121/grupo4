package com.tallerwebi.dominio;

public interface ServicioPago {
    void cambiarEstado(String estado);
    boolean isPremiumUser(String email);
}
