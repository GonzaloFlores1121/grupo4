package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioPago")
@Transactional
public class ServicioPagoImpl implements ServicioPago {

    private RepositorioPago repositorioPago;

    @Autowired
    public ServicioPagoImpl(RepositorioPago repositorioPago){
        this.repositorioPago = repositorioPago;
    }

    @Override
    public void cambiarEstado(String email) {
        Usuario usuario = repositorioPago.obtenerUsuario(email);
        if (usuario != null) {
            usuario.setPremium(true);
            repositorioPago.save(usuario);
        }
    }

    @Override
    public boolean isPremiumUser(String email) {
        Usuario usuario = repositorioPago.obtenerUsuario(email);
        return usuario != null && usuario.getPremium();
    }
}
