package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.DatosLogin;
import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioDatosUsuarioImpl implements ServicioDatosUsuario {

    @Override
    public Boolean registrarUsuario(Usuario usuario) throws DatosIncorrectos {
        if (usuario.getPeso()!=null && usuario.getPeso()>0 && usuario.getAltura()>0 && usuario.getAltura()!=null && usuario.getEmail()!=null
        && usuario.getGenero()!=null && usuario.getPassword()!=null && usuario.getEdad()!=null && usuario.getEdad()>=18) {
            return true;
        } else {
            throw new DatosIncorrectos("Error al registrar el usuario");
        }
    }


}
