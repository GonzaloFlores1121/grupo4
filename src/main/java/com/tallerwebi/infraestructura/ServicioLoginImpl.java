package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import com.tallerwebi.dominio.excepcion.UsuarioExistente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioLogin")
@Transactional
public class ServicioLoginImpl implements ServicioLogin {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario){
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public void registrarUsuario(Usuario usuario) throws UsuarioExistente, DatosIncorrectos {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());
        if(usuarioEncontrado != null){
            throw new UsuarioExistente("El usuario ya existe");
        }else if(!usuarioDatosCorrecto(usuario)){
            throw new DatosIncorrectos("Los valores del usuario son incorrectos");
        }
        repositorioUsuario.guardar(usuario);

    }


    @Override
    public Usuario consultarUsuario (String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    @Override
    public Boolean usuarioDatosCorrecto(Usuario usuario) throws DatosIncorrectos {
        if (usuario != null && usuario.getPeso() != null && usuario.getPeso() > 0.0 && usuario.getAltura() != null && usuario.getAltura() > 0
                && usuario.getEmail() != null && usuario.getPassword() != null && usuario.getEdad() != null && usuario.getEdad() >= 18) {
            return true;
        } else{
            throw new DatosIncorrectos("Datos incorrectos del usuario");
        }
    }


}

