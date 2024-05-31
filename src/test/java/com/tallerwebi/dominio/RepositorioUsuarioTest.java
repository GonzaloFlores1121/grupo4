package com.tallerwebi.dominio;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioUsuarioTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioUsuario repositorioUsuario;

    @Test
    @Transactional
    @Rollback
    public void testObtenerUsuarioPorEmailYPassword() {
        String email = "pacolo@gmail.com";
        String password = "1234abcd";
        givenExisteUsuarioConEmailYPassword(email, password);
        Usuario usuario = whenBuscarUsuarioConEmailYPassword(email, password);
        thenObtenerUsuario(usuario);
    }

    private void givenExisteUsuarioConEmailYPassword(String email, String password) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setPassword(password);
        sessionFactory.getCurrentSession().save(usuario);
    }

    private Usuario whenBuscarUsuarioConEmailYPassword(String email, String password) {
        return repositorioUsuario.buscarUsuario(email, password);
    }

    private void thenObtenerUsuario(Usuario usuario) {
        assertNotNull(usuario);
    }

    @Test
    @Transactional
    @Rollback
    public void testNoObtenerUsuarioEmailYPasswordIncorrectos() {
        givenExisteUsuarioConEmailYPassword("pacolo@gmail.com", "1234abcd");
        Usuario usuario = whenBuscarUsuarioConEmailYPassword("pacolo@outlook.com", "qwerty123");
        thenNoObtenerUsuario(usuario);
    }

    private void thenNoObtenerUsuario(Usuario usuario) {
        assertNull(usuario);
    }

    @Test
    @Transactional
    @Rollback
    public void testObtenerUsuarioPorEmail() {
        String email = "pacolo@gmail.com";
        givenExisteUsuarioConEmail(email);
        Usuario usuario = whenBuscarUsuarioConEmail(email);
        thenObtenerUsuario(usuario);
    }

    private void givenExisteUsuarioConEmail(String email) {
        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        sessionFactory.getCurrentSession().save(usuario);
    }

    private Usuario whenBuscarUsuarioConEmail(String email) {
        return repositorioUsuario.buscarPorEmail(email);
    }

    @Test
    @Transactional
    @Rollback
    public void testObtenerTodosLosUsuarios() {
        Integer esperado = 3;
        givenExistenUsuario();
        givenExistenUsuario();
        givenExistenUsuario();
        List<Usuario> usuarios = whenBuscarUsuarios();
        thenObtenerUsuarios(esperado, usuarios);
    }

    private void givenExistenUsuario() {
        Usuario usuario = new Usuario();
        sessionFactory.getCurrentSession().save(usuario);
    }

    private List<Usuario> whenBuscarUsuarios() {
        return repositorioUsuario.obtenerTodos();
    }

    private void thenObtenerUsuarios(Integer esperado, List<Usuario> usuarios) {
        assertEquals(esperado, usuarios.size());
    }

}
