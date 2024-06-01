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
    public Usuario verificarUsuario (String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    @Override
    public void registrarUsuario(Usuario usuario) throws UsuarioExistente, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        if(repositorioUsuario.buscarPorEmail(usuario.getEmail())!=null){
            throw new UsuarioExistente();
        }
        if(validarDatos(usuario)) {
            insertarAvatarPredeterminado(usuario);
            usuario.setConfiguracionUsuario(crearConfiguracionPredeterminada());
            repositorioUsuario.guardar(usuario);             
        }
    }

    private void insertarAvatarPredeterminado(Usuario usuario) {
        if(usuario.getGenero().equals("masculino")) {
            usuario.setImagen("icono-perfil-1.png");
        }else {
            usuario.setImagen("icono-perfil-2.png");
        }
    }    

    private ConfiguracionUsuario crearConfiguracionPredeterminada() {
        ConfiguracionUsuario configuracionUsuario = new ConfiguracionUsuario();
        configuracionUsuario.setRecibirNotificaciones(true);
        configuracionUsuario.setUnidadEnergia("calorias");
        configuracionUsuario.setUnidadMasa("kilogramos");
        repositorioConfiguracionUsuario.save(configuracionUsuario);
        return configuracionUsuario;
    }

    @Override
    public Usuario buscarUsuario(String email) {
        return repositorioUsuario.buscarPorEmail(email);
    }   

    @Override
    public void modificarImagen(Usuario usuario, String imagen) {
        usuario.setImagen(imagen);
        repositorioUsuario.modificar(usuario);
    }

    @Override
    public void modificarUsuario(Usuario usuario, Usuario nuevosDatos) throws UsuarioExistente, DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException {
        if((repositorioUsuario.buscarPorEmail(nuevosDatos.getEmail())!=null) && (usuario.getEmail().equals(nuevosDatos.getEmail())==false)) {
            throw new UsuarioExistente();
        }
        if(validarDatos(nuevosDatos)) {
            usuario.setNombre(nuevosDatos.getNombre());
            usuario.setEmail(nuevosDatos.getEmail());
            usuario.setPassword(nuevosDatos.getPassword());
            usuario.setEdad(nuevosDatos.getEdad());
            usuario.setGenero(nuevosDatos.getGenero());
            usuario.setAltura(nuevosDatos.getAltura());
            usuario.setPeso(nuevosDatos.getPeso());
            usuario.setMetaAlcanzarPeso(nuevosDatos.getMetaAlcanzarPeso());
            usuario.setNivelDeActividad(nuevosDatos.getNivelDeActividad());
            repositorioUsuario.modificar(usuario);
        }     
    }

    @Override
    public void modificarConfiguracion(Usuario usuario, ConfiguracionUsuario configuracionUsuario) {
        usuario.getConfiguracionUsuario().setRecibirNotificaciones(configuracionUsuario.getRecibirNotificaciones());
        usuario.getConfiguracionUsuario().setUnidadEnergia(configuracionUsuario.getUnidadEnergia());
        usuario.getConfiguracionUsuario().setUnidadMasa(configuracionUsuario.getUnidadMasa());
        repositorioConfiguracionUsuario.update(usuario.getConfiguracionUsuario());
        repositorioUsuario.modificar(usuario);
    }

    @Override
    public void eliminarUsuario(Usuario usuario) throws UsuarioNoExistente {
        if(repositorioUsuario.buscarPorEmail(usuario.getEmail())==null) {
            throw new UsuarioNoExistente();
        }
        repositorioUsuario.eliminar(usuario);
    }

    @Override
    public Boolean validarDatos(Usuario usuario) throws DatosIncorrectos, EdadInvalidaException, AlturaIncorrectaException, PesoIncorrectoException {
        validarUsuario(usuario);
        validarEdad(usuario.getEdad());
        validarAltura(usuario.getAltura());
        validarPeso(usuario.getPeso());
        return true;
    }

    //validaciones
    private void validarUsuario(Usuario usuario) throws DatosIncorrectos {
        if (usuario == null || usuario.getEmail() == null || usuario.getPassword() == null) {throw new DatosIncorrectos("El usuario, el correo electrónico y la contraseña no pueden ser nulos");}
    }

    private void validarEdad(Integer edad) throws EdadInvalidaException {
        if (edad == null || edad <= 12 || edad >= 100) {throw new EdadInvalidaException();} 
    }

    private void validarAltura(Double altura) throws AlturaIncorrectaException {
        if (altura == null || altura <= 0 || altura > 300) {throw new AlturaIncorrectaException();}
    }

    private void validarPeso(Double peso) throws PesoIncorrectoException {
        if (peso == null || peso <= 0 || peso > 500) {throw new PesoIncorrectoException();}
    }

}
