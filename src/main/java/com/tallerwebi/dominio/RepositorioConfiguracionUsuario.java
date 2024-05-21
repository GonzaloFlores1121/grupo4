package com.tallerwebi.dominio;

public interface RepositorioConfiguracionUsuario {

    public void guardar(ConfiguracionUsuario configuracionUsuario);
    public ConfiguracionUsuario buscar(Long id);
    public void modificar(ConfiguracionUsuario configuracionUsuario);

}
