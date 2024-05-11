package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.*;
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
    public Usuario consultarUsuario (String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    @Override
    public void registrar(Usuario usuario) throws UsuarioExistente, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());
        if(usuarioEncontrado != null){
            throw new UsuarioExistente();
        }
        if(validarDatos(usuario)) {
            repositorioUsuario.guardar(usuario);
        }

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

    private Boolean validarDatos(Usuario usuario) throws DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException {
        validarUsuario(usuario);
        validarEdad(usuario.getEdad());
        validarAltura(usuario.getAltura());
        validarPeso(usuario.getPeso());
        return true;
    }
    private void validarUsuario(Usuario usuario) throws DatosIncorrectos {
        if (usuario == null || usuario.getEmail() == null || usuario.getPassword() == null) {
            throw new DatosIncorrectos("El usuario, el correo electrónico y la contraseña no pueden ser nulos");
        }
    }

    private void validarEdad(Integer edad) throws EdadInvalidaException {
        if (edad == null || edad <= 12 || edad >= 100) {
            throw new EdadInvalidaException();
        }
    }

    private void validarAltura(Double altura) throws AlturaIncorrectaException {
        if (altura == null || altura <= 0 || altura > 300) {
            throw new AlturaIncorrectaException();
        }
    }

    private void validarPeso(Double peso) throws PesoIncorrectoException {
        if (peso == null || peso <= 0 || peso > 500) {
            throw new PesoIncorrectoException();
        }
    }
}

