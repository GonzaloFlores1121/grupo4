package com.tallerwebi.presentacion;

import javax.servlet.http.HttpSession;

import com.tallerwebi.dominio.MacronutrientesUsuario;
import com.tallerwebi.dominio.ServicioDatosUsuario;
import com.tallerwebi.dominio.Usuario;

import java.util.TreeMap;
import java.util.Map;

import com.tallerwebi.dominio.excepcion.DatosIncorrectos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@SessionAttributes("usuario")
public class ControladorMenuPrincipal {


    private ServicioDatosUsuario servicioDatosUsuario;

    @Autowired
    public ControladorMenuPrincipal(ServicioDatosUsuario servicioDatosUsuario) {
        this.servicioDatosUsuario = servicioDatosUsuario;
    }

    @RequestMapping(value = "/actividadesFisicas",method = RequestMethod.GET)
    public ModelAndView irAEnForma(){

        return new ModelAndView("actividadesFisicas");
    }

    @RequestMapping(value = "/ejercicio",method = RequestMethod.GET)
    public ModelAndView irAEjercicio(){

        return new ModelAndView("ejercicio");
    }

    @RequestMapping(value = "/miDiario",method = RequestMethod.GET)
    public ModelAndView irAMiDiario(HttpServletRequest request) throws DatosIncorrectos {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

if(usuario != null) {
    ModelMap modelo = new ModelMap();
    Integer icr=servicioDatosUsuario.calcularIngestaCalorica(usuario);
    MacronutrientesUsuario macronutrientesUsuario = servicioDatosUsuario.CalcularDistribucionDeMacronutrientes(usuario);
    modelo.put("icr", icr);
    modelo.put("carbos", macronutrientesUsuario.getCarbohidratosAConsumir());
    modelo.put("grasas",  macronutrientesUsuario.getGrasaAConsumir());
    modelo.put("proteinas", macronutrientesUsuario.getProteinaAConsumir());
    return new ModelAndView("miDiario",modelo);
}
        else {
            return new ModelAndView("redirect:/inicio");
}

    }

    @RequestMapping(value = "/recetas",method = RequestMethod.GET)
    public ModelAndView irARecetas(){

        return new ModelAndView("recetas");
    }

    @RequestMapping(value = "/descripcionRecetas",method = RequestMethod.GET)
    public ModelAndView irADescripcionRecetas(){

        return new ModelAndView("descripcionRecetas");
    }

    @RequestMapping(value = "/alimentos",method = RequestMethod.GET)
        public ModelAndView irALimentos(){
            return new ModelAndView("alimentos");
    }


    @RequestMapping(value = "/diarioEjercicio",method = RequestMethod.GET)
    public ModelAndView irADiarioEjercicio(){

        return new ModelAndView("diarioEjercicio");
    }

    //probando un poco lo del modelo 
    @RequestMapping(path="/perfilUsuario", method=RequestMethod.GET)
    public ModelAndView perfilUsuario() {
        ModelMap modelo = new ModelMap();
        modelo.put("usuario", "admin");
        modelo.put("contraseña", "1234");
        modelo.put("email", "admin1234@gmail.com");
        modelo.put("edad", 25);
        modelo.put("genero", "Masculino");
        modelo.put("altura", 155);
        modelo.put("peso", 45);
        modelo.put("nivelDeActividadFisica", "Sedentaria");
        modelo.put("dietaObjetivo", "Mantener mi peso actual");
        modelo.put("pesoMeta", 45);
        return new ModelAndView("perfilUsuario", modelo);
    }

    //viendo que tal va con un map a posteriori estas weas se borraran, pero solo para ir viendo :v
    @RequestMapping(path="/notificaciones", method=RequestMethod.GET) 
    public ModelAndView notificaciones() {
        ModelMap modelo = new ModelMap();
        Map<String, String> notificaciones = new TreeMap<>();
        notificaciones.put("Control Peso", "A las 8 a.m. debes controlar tu peso.");
        notificaciones.put("Alimentacion", "No olvides que a las 12 a.m. debe almorzar de manera saludable.");
        notificaciones.put("Mantenimiento", "Desde las 8 p.m. a las 10 p.m. la aplicacion se hallara en mantenimiento gracias por su comprension.");
        notificaciones.put("Novedades", "No olvides leer el parche con las nuevas actualizaciones.");
        modelo.addAttribute("notificaciones", notificaciones);
        return new ModelAndView("notificaciones", modelo);
    }

    @RequestMapping(path="/configuracion", method=RequestMethod.GET)
    public ModelAndView configuracion() {
        ModelMap modelo = new ModelMap();
        modelo.put("region", "");
        modelo.put("idioma", "");
        modelo.put("unidad-energia", "");
        modelo.put("unidad-masa", "");
        modelo.put("idr", 0);
        return new ModelAndView("configuracion", modelo);
    }

    @RequestMapping(path="/guardarConfiguracion", method=RequestMethod.POST)
    public ModelAndView guardarConfiguracion() {
        return new ModelAndView("redirect:/menuprincipal");
    }

    @RequestMapping(value = "/calendarioDieta",method = RequestMethod.GET)
    public ModelAndView irAMiCalendarioDieta(){

        return new ModelAndView("calendarioDieta");
    }
}



