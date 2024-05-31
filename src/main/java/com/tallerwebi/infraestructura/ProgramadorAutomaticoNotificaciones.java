package com.tallerwebi.infraestructura;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.tallerwebi.dominio.Notificacion;
import com.tallerwebi.dominio.RepositorioNotificacion;
import com.tallerwebi.dominio.ServicioNotificacion;
import com.tallerwebi.dominio.excepcion.UsuarioNoExistente;

@Component
public class ProgramadorAutomaticoNotificaciones {

    private RepositorioNotificacion repositorioNotificacion;
    private ServicioNotificacion servicioNotificacion;

    @Autowired
    public ProgramadorAutomaticoNotificaciones(RepositorioNotificacion repositorioNotificacion, ServicioNotificacion servicioNotificacion) {
        this.repositorioNotificacion = repositorioNotificacion;
        this.servicioNotificacion = servicioNotificacion;
    }

    public void enviarNotificacionDeBienvenida(Long idUsuario) throws UsuarioNoExistente {
        String titulo = "Bienvenido a fatloss";
        String contenido = "Nos alegra que te unas a nosotros en tu camino hacia una vida mas saludable. FatLoss es tu app de nutricion ideal para alcanzar tus objetivos de perdida de peso.";
        servicioNotificacion.enviarNotificacion(titulo, contenido, LocalDateTime.now(), idUsuario);
    }

    @Scheduled(cron = "0 0 6 * * *")
    public void enviarNotificacionExaminaTuPeso() {
        String titulo = "Examina tu Peso";
        String contenido = "¡Es Hora de pesarte! Recuerda que tu peso es solo un numero y no define tu valia como persona. Utiliza esta informacion como una guia para seguir tu progreso hacia tus metas de salud y bienestar. ¡Animo y sigue adelante!";
        servicioNotificacion.enviarNotificaciones(titulo, contenido, LocalDateTime.now());
    }

    @Scheduled(cron = "0 0 18 * * *")
    public void enviarNotificacionEjercitarse() {
        String titulo = "Ejercitarse";
        String contenido = "¡Es hora de ejercitarse! Dedica este tiempo a cuidar tu cuerpo y mente. Encuentra una actividad que disfrutes y que te haga sentir bien. Recuerda que cada paso cuenta, asi que ¡adelante, tu puedes hacerlo!";
        servicioNotificacion.enviarNotificaciones(titulo, contenido, LocalDateTime.now());
    }

    @Scheduled(cron = "0 0 8 * * *")
    public void enviarNotificacionAlimentarseDesayuno() {
        String titulo = "Desayuno";
        String contenido = "¡Es hora de comenzar el dia con energia! No te saltes el desayuno, es la comida mas importante. Preparate un desayuno nutritivo y delicioso para cargar tus baterias y enfrentar el dia con todo.";
        servicioNotificacion.enviarNotificaciones(titulo, contenido, LocalDateTime.now());
    }

    @Scheduled(cron = "0 0 12 * * *")
    public void enviarNotificacionAlimentarseAlmuerzo() {
        String titulo = "Almuerzo";
        String contenido = "¡Hora de recargar energias! Disfruta de un almuerzo equilibrado que te mantenga activo y concentrado durante el dia. Asegurate de incluir una buena porcion de vegetales, proteinas y carbohidratos para un almuerzo completo y satisfactorio.";
        servicioNotificacion.enviarNotificaciones(titulo, contenido, LocalDateTime.now());
    }

    @Scheduled(cron = "0 0 16 * * *")
    public void enviarNotificacionAlimentarseMerienda() {
        String titulo = "Merienda";
        String contenido = "¡Un descanso para recargar energias! A media tarde, regalate un momento para disfrutar de una merienda saludable. Un punado de frutos secos, una pieza de fruta o un yogur con granola son excelentes opciones para mantener la energia hasta la cena.";
        servicioNotificacion.enviarNotificaciones(titulo, contenido, LocalDateTime.now());
    }

    @Scheduled(cron = "0 0 20 * * *")
    public void enviarNotificacionAlimentarseCena() {
        String titulo = "Cena";
        String contenido = "¡Hora de relajarse y disfrutar de una cena reconfortante! Termina tu dia con una comida ligera pero nutritiva. Opta por platos balanceados que te ayuden a descansar bien por la noche. Evita comidas pesadas y busca opciones que te hagan sentir satisfecho sin dejar de lado la salud.";
        servicioNotificacion.enviarNotificaciones(titulo, contenido, LocalDateTime.now());
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void eliminarNotificacionesAntiguas() {
        servicioNotificacion.eliminarNotificaciones(LocalDateTime.now().minusWeeks(1));
    }

}