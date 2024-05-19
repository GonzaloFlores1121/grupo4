package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.MacronutrientesUsuario;
import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.ServicioLogin;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;



@Service
@Transactional
public class ServicioDatosUsuarioImpl implements ServicioDatosUsuario {

    ServicioLogin  servicioLogin;

    @Autowired
    public ServicioDatosUsuarioImpl(ServicioLogin servicioLogin) {
        this.servicioLogin = servicioLogin;
    }


    @Override
    public Integer calcularIngestaCalorica(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        if (!servicioLogin.usuarioDatosCorrecto(usuario)) {
            throw new DatosIncorrectos("Datos incorrectos del usuario");
        }

        Double md = calcularMetabolismoBasalDelUsuario(usuario);
        Double icr = 0.0;

        switch (usuario.getNivelDeActividad()) {
            case "sedentario":
                icr = md * 1.2;
                break;
            case "baja_actividad":
                icr = md * 1.375;
                break;
            case "activo":
                icr = md * 1.55;
                break;
            case "muy_activo":
                icr = md * 1.725;
                break;
        }

        Integer ingestaCalorica = (int) Math.round(icr);
        usuario.setIngestaCalorica(ingestaCalorica);
        return ingestaCalorica;
    }

    @Override
    public Double calcularMetabolismoBasalDelUsuario(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        if (!servicioLogin.usuarioDatosCorrecto(usuario)) {
            throw new DatosIncorrectos("Datos incorrectos del usuario");
        }

        Double mb = 0.0;
        Double valorPorDefecto;
        Double multiplicacionPeso;
        Double multiplicacionAltura;
        Double multiplicacionEdad;

        if (usuario.getGenero().equals("masculino")) {
            valorPorDefecto = 88.362;
            multiplicacionPeso = 13.397;
            multiplicacionAltura = 4.799;
            multiplicacionEdad = 5.677;
            mb = valorPorDefecto + (multiplicacionPeso * usuario.getPeso()) + (multiplicacionAltura * usuario.getAltura()) -
                    (multiplicacionEdad * usuario.getEdad());
        } else if (usuario.getGenero().equals("femenino")) {
            valorPorDefecto = 447.593;
            multiplicacionPeso = 9.247;
            multiplicacionAltura = 3.098;
            multiplicacionEdad = 4.330;
            mb = valorPorDefecto + (multiplicacionPeso * usuario.getPeso()) + (multiplicacionAltura * usuario.getAltura()) -
                    (multiplicacionEdad * usuario.getEdad());
        }
        return mb;
    }

    @Override
    public MacronutrientesUsuario CalcularDistribucionDeMacronutrientes(Usuario usuario) {

        MacronutrientesUsuario macronutrientesUsuario= new MacronutrientesUsuario(usuario);
        Integer caloriasGrasas = (int) (usuario.getIngestaCalorica() * 0.30);
        Integer gramosGrasas = caloriasGrasas / 9;
        macronutrientesUsuario.setGrasaAConsumir(gramosGrasas);

        Integer caloriasProteinas = (int) (usuario.getIngestaCalorica() * 0.20);
        Integer gramosProteinas = caloriasProteinas / 4;
        macronutrientesUsuario.setProteinaAConsumir(gramosProteinas);

        Integer caloriasCarbohidratos = (int) (usuario.getIngestaCalorica() * 0.50);
        Integer gramosCarbohidratos = caloriasCarbohidratos / 4;
        macronutrientesUsuario.setCarbohidratosAConsumir(gramosCarbohidratos);

        return macronutrientesUsuario;

    }
}


