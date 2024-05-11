package com.tallerwebi.dominio;

public interface RepositorioMacronutrientes {

        void guardar(MacronutrientesUsuario macronutriente);
        MacronutrientesUsuario buscar(Long id);
        void modificar(MacronutrientesUsuario macronutriente);
}
