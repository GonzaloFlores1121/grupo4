package com.tallerwebi.dominio;

public interface RepositorioConfiguracionUsuario {

    void save(ConfiguracionUsuario configuracionUsuario);
    ConfiguracionUsuario get(Long id);
    void update(ConfiguracionUsuario configuracionUsuario);
    void delete(ConfiguracionUsuario configuracionUsuario);

}
