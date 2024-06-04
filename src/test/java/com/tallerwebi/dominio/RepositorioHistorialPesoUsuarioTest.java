package com.tallerwebi.dominio;

import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioHistorialPesoUsuarioTest {
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioHistorialPesoUsuario repositorioHistorialPesoUsuario;

    @Test
    @Transactional
    @Rollback
    public void modificoPesoUsuario() {
        Usuario usuario=givenTengoUnUsuario();

        whenModificoElPeso(70.0,usuario);

        thenSeActualizaElPeso(usuario);

    }

    private void thenSeActualizaElPeso(Usuario usuario) {
        assertEquals(70.0,usuario.getPeso());
    }

    private void whenModificoElPeso(double v, Usuario usuario) {
        repositorioHistorialPesoUsuario.modificarPeso(v,usuario);
        sessionFactory.getCurrentSession().refresh(usuario);
    }

    private Usuario givenTengoUnUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Mili");
        usuario.setPeso(56.0);
        usuario.setEmail("mili@gmail.com");
        sessionFactory.getCurrentSession().save(usuario);
        return usuario;
    }




}
