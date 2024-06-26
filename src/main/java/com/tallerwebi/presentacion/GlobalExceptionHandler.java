package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.excepcion.*;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler({AlimentoNoEncontradoException.class,
            CategoriaAlimentoNoEncontradaException.class,
            UsuarioNoExistente.class,
             RecetaNoEncontradaException.class})
    public ModelAndView handleNotFound(Exception ex) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("error404");
        mav.addObject("exception", ex);
        return mav;
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ModelAndView handleResponseStatusException(ResponseStatusException ex, WebRequest request) {
        ModelAndView mav = new ModelAndView("error404");
        mav.addObject("exception", ex);
        return mav;
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ModelAndView handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        ModelAndView mav = new ModelAndView("error404");
        mav.addObject("message", "Valor de ID inválido. Por favor, ingrese un número válido.");
        return mav;
    }

}
