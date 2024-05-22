package com.tallerwebi.dominio;

public interface RepositorioConfiguracionUsuario {

    void guardar(ConfiguracionUsuario configuracionUsuario);
    ConfiguracionUsuario buscar(Long id);
    void modificar(ConfiguracionUsuario configuracionUsuario);

}
