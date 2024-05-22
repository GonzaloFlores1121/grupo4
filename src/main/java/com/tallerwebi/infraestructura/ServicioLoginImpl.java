package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ConfiguracionUsuario;
import com.tallerwebi.dominio.RepositorioConfiguracionUsuario;
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
    private RepositorioConfiguracionUsuario repositorioConfiguracionUsuario;

    @Autowired
    public ServicioLoginImpl(RepositorioUsuario repositorioUsuario, RepositorioConfiguracionUsuario repositorioConfiguracionUsuario){
        this.repositorioUsuario = repositorioUsuario;
        this.repositorioConfiguracionUsuario = repositorioConfiguracionUsuario;
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
            if(usuario.getGenero().equals("masculino")) {
                usuario.setImagen("icono-perfil-1.png");
            }else {
                usuario.setImagen("icono-perfil-2.png");
            }
            usuario.setConfiguracionUsuario(crearConfiguracionPredeterminada());
            repositorioUsuario.guardar(usuario);         
        }
    }

    private ConfiguracionUsuario crearConfiguracionPredeterminada() {
        ConfiguracionUsuario configuracionUsuario = new ConfiguracionUsuario();
        configuracionUsuario.setRecibirNotificaciones(true);
        configuracionUsuario.setUnidadEnergia("calorias");
        configuracionUsuario.setUnidadMasa("kilogramos");
        repositorioConfiguracionUsuario.guardar(configuracionUsuario);
        return configuracionUsuario;
    }

    public Boolean usuarioDatosCorrecto(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        return validarDatos(usuario);
    }

    @Override
    public void modificarPerfil(Usuario usuario, String nuevoEmail) throws UsuarioExistente, DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException {
        if(nuevoEmail!=usuario.getEmail() && repositorioUsuario.buscar(usuario.getEmail())!=null) {
            throw new UsuarioExistente();
        }
        if(validarDatos(usuario)) {
            repositorioConfiguracionUsuario.modificar(usuario.getConfiguracionUsuario());
            repositorioUsuario.modificar(usuario);
        }
        
    }

    @Override
    public Usuario buscar(String email) {
        return repositorioUsuario.buscar(email);
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

