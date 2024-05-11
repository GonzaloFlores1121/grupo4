package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.MacronutrientesUsuario;
import com.tallerwebi.dominio.RepositorioMacronutrientes;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import javax.transaction.Transactional;

@Repository("repositorioMacronutrientes")
@Transactional
public class RepositorioMacronutrienteImpl implements RepositorioMacronutrientes {

    private final SessionFactory sessionFactory;

    @Autowired
    public RepositorioMacronutrienteImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void guardar(MacronutrientesUsuario macronutriente) {
        Session session = sessionFactory.getCurrentSession();
        session.save(macronutriente);
    }

    @Override
    public MacronutrientesUsuario buscar(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.find(MacronutrientesUsuario.class, id);
    }

    @Override
    public void modificar(MacronutrientesUsuario macronutriente) {
        Session session = sessionFactory.getCurrentSession();
        session.update(macronutriente);
    }
}