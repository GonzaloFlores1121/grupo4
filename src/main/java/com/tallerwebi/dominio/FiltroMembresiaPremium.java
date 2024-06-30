package com.tallerwebi.dominio;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter("/premiumContent/*")
public class FiltroMembresiaPremium implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inicialización si es necesario
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        HttpSession session = req.getSession(false);

        if (session != null && Boolean.TRUE.equals(session.getAttribute("isPremium"))) {
            chain.doFilter(request, response); // El usuario tiene acceso, continuar
        } else {
            res.sendRedirect(req.getContextPath() + "/accesoDenegado"); // Redirigir a acceso denegado
        }
    }

    @Override
    public void destroy() {
        // Limpieza si es necesario
    }
}