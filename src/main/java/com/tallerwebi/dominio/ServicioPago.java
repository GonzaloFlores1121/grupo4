package com.tallerwebi.dominio;

public interface ServicioPago {
    public void cambiarEstado(String estado);
    boolean isPremiumUser(String email);
}
