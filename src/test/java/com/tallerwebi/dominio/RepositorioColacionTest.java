package com.tallerwebi.dominio;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})

public class RepositorioColacionTest {


    @Autowired
    private RepositorioColacion repositorioColacion;

    @Autowired
    private SessionFactory sessionFactory;

    @Test
    @Transactional
    @Rollback
    public void testObtenerColacion() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Test User");

        Alimento alimento= new Alimento();
        alimento.setNombre("Test alimento");

        Session session = sessionFactory.getCurrentSession();
        session.save(usuario);
        session.save(alimento);

        TipoColacion tipoColacion = TipoColacion.DESAYUNO;
        LocalDate fecha = LocalDate.now();

        Colacion colacion = new Colacion();
        colacion.setUsuario(usuario);
        colacion.setAlimentos(alimento);
        colacion.setTipo(tipoColacion);
        colacion.setFecha(fecha);

        repositorioColacion.agregarColacion(colacion);

        List<Colacion> colacionesRecuperadas = repositorioColacion.obtenerColacionesPorFechaYUsuarioYTipo(fecha, usuario, tipoColacion);

        for(Colacion c: colacionesRecuperadas){
            System.out.println(c.getAlimentos().getNombre());
            System.out.println(c.getUsuario().getNombre());
        }
        assertEquals(1, colacionesRecuperadas.size());
        assertEquals(colacion.getId(), colacionesRecuperadas.get(0).getId());
    }
}