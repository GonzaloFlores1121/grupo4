package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Service
@Transactional
public class ServicioDatosUsuarioImpl implements ServicioDatosUsuario {

    private  RepositorioUsuario repositorioUsuario;
    private ServicioLogin  servicioLogin;
    private RepositorioHistorialPesoUsuario repositorioHistorialPesoUsuario;
    private ServicioCalendario  servicioCalendario;
    private ServicioNotificacion servicioNotificacion;
    private RepositorioMacronutrientes repositorioMacronutrientes;

    @Autowired
    public ServicioDatosUsuarioImpl(ServicioLogin servicioLogin , RepositorioHistorialPesoUsuario repositorioHistorialPesoUsuario, RepositorioUsuario repositorioUsuario, ServicioCalendario servicioCalendario,ServicioNotificacion servicioNotificacion, RepositorioMacronutrientes repositorioMacronutrientes) {
        this.repositorioHistorialPesoUsuario=repositorioHistorialPesoUsuario;
        this.servicioLogin = servicioLogin;
        this.repositorioUsuario = repositorioUsuario;
        this.servicioCalendario = servicioCalendario;
        this.servicioNotificacion = servicioNotificacion;
        this.repositorioMacronutrientes = repositorioMacronutrientes;
    }

    @Override
    public Integer calcularIngestaCalorica(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        if (!servicioLogin.validarDatos(usuario)) {
            throw new DatosIncorrectos("Datos incorrectos del usuario");
        }

        Double md = calcularMetabolismoBasalDelUsuario(usuario);
        if (md == null) {
            throw new DatosIncorrectos("No se pudo calcular el metabolismo basal. Verifique los datos del usuario.");
        }

        Double icr = 0.0;
        switch (usuario.getNivelDeActividad()) {
            case "sedentario":
                icr = md * 1.2;
                break;
            case "baja actividad":
                icr = md * 1.375;
                break;
            case "activo":
                icr = md * 1.55;
                break;
            case "muy activo":
                icr = md * 1.725;
                break;
            default:
                throw new DatosIncorrectos("Nivel de actividad no válido.");
        }

        Integer ingestaCalorica = (int) Math.round(icr);
        usuario.setIngestaCalorica(ingestaCalorica);
        repositorioHistorialPesoUsuario.actualizarMiIcr(usuario, ingestaCalorica);
        return ingestaCalorica;
    }


    @Override
    public Double calcularMetabolismoBasalDelUsuario(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException {
        if (!servicioLogin.validarDatos(usuario)) {
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
        repositorioMacronutrientes.guardar(macronutrientesUsuario);
        return macronutrientesUsuario;

    }


    @Override
    public List<HistoriaPesoUsuario> obtenerTodoElHistorialDePeso(Usuario usuario) throws UsuarioNoExistente {
     if(repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword())!=null) {
         Usuario usuarioEncontrado = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());
        List<HistoriaPesoUsuario> historialPeso=repositorioHistorialPesoUsuario.obtenerHistorialPesoUsuario(usuario);
        return historialPeso;
     }{
         throw new UsuarioNoExistente();
        }
    }
    @Override
    public void verificarIngestaDelDia(Usuario usuario) throws DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException, PesoIncorrectoException, UsuarioNoExistente {
        Integer ingestaCalorica = calcularIngestaCalorica(usuario);
        LocalDate today = LocalDate.now();
        java.sql.Date sqlDate = java.sql.Date.valueOf(today);

        Integer ingestaCaloricaDelDia = servicioCalendario.mostrarIngestaCaloricaDelDia(usuario, sqlDate);

        if (ingestaCalorica < ingestaCaloricaDelDia) {
            String mensaje = String.format(" Tu límite diario es de %d calorías y has consumido %d calorías hasta ahora.",
                     ingestaCalorica,ingestaCaloricaDelDia);
            servicioNotificacion.enviarNotificacion("¡Has pasado tu ingesta calórica del día!", mensaje, today.atStartOfDay(), usuario.getId());
        }
    }

    @Override
    public void actualizarPeso(Usuario usuario, Double peso) throws PesoIncorrectoException, DatosIncorrectos, AlturaIncorrectaException, EdadInvalidaException {
        Usuario usuarioEnSesion = repositorioUsuario.buscarUsuario(usuario.getEmail(), usuario.getPassword());
System.out.print(calcularIngestaCalorica(usuarioEnSesion));
        if (usuarioEnSesion != null && pesoValidoUsuario(peso)) {
            LocalDateTime fechaActualLocalDateTime = LocalDateTime.now();
            Date fechaActual = Date.from(fechaActualLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
            java.sql.Date sqlFechaActual = new java.sql.Date(fechaActual.getTime());

            repositorioHistorialPesoUsuario.modificarPeso(peso, usuarioEnSesion);
            usuarioEnSesion.setPeso(peso);
            Integer icr = calcularIngestaCalorica(usuarioEnSesion);
            usuarioEnSesion.setIngestaCalorica(icr);
            repositorioHistorialPesoUsuario.actualizarMiIcr(usuarioEnSesion, icr);


            HistoriaPesoUsuario historialPeso = repositorioHistorialPesoUsuario.obtenerHistorialPesoUsuarioParaUnaFecha(sqlFechaActual);
            if (historialPeso != null) {
                historialPeso.setPeso(peso);
                repositorioHistorialPesoUsuario.actualizarMiPesoAgregado(historialPeso);

            } else {
                historialPeso = new HistoriaPesoUsuario(peso, usuarioEnSesion, sqlFechaActual);
                repositorioHistorialPesoUsuario.agregarPesoYFecha(historialPeso);
            }

        } else {
            throw new PesoIncorrectoException();
        }
    }

    private Boolean pesoValidoUsuario(Double peso) {
        return peso != null && peso >= 30 && peso <= 700;
    }
}


