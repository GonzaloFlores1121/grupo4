package com.tallerwebi.presentacion;
import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ControladorPago {

    private ServicioPago servicioPago;

    @Autowired
    public ControladorPago(ServicioPago servicioPago) {

        this.servicioPago  = servicioPago;
    }

    @RequestMapping(value = "/api/mp", method = RequestMethod.POST)
    public String getList(@RequestBody UserBuyer userBuyer, HttpServletRequest request) {
        if (userBuyer == null) {
            return "error json :/";
        }

        String title = userBuyer.getTitle();
        int quantity = userBuyer.getQuantity();
        int price = userBuyer.getUnit_price();

        try {
            MercadoPagoConfig.setAccessToken("TEST-4626193547107194-062716-e5936344f95bd7d8a79be74b6d33bbca-637766730");

            PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                    .title(title)
                    .quantity(quantity)
                    .unitPrice(new BigDecimal(price))
                    .currencyId("ARS")
                    .build();
            List<PreferenceItemRequest> items = new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backUrls = PreferenceBackUrlsRequest.builder()
                    .success("http://localhost:8080/spring/pago-completado")
                    .pending("http://localhost:8080/spring/accesoDenegado")
                    .failure("http://localhost:8080/spring/accesoDenegado")
                    .build();

            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .build();

            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);

            HttpSession session = request.getSession();
            session.setAttribute("pagoEnProceso", true);

            return preference.getId();
        } catch (MPException | MPApiException e) {
            return e.toString();
        }
    }

    @RequestMapping(value = "/pago-completado", method = RequestMethod.GET)
    public ModelAndView pagoCompletado(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");
        Boolean pagoEnProceso = (Boolean) session.getAttribute("pagoEnProceso");
        ModelMap model = new ModelMap();

        if (usuario != null && Boolean.TRUE.equals(pagoEnProceso)) {
            session.removeAttribute("pagoEnProceso");
            session.setAttribute("pagoCompletado", true);
            servicioPago.cambiarEstado(usuario.getEmail());
            redirectAttributes.addFlashAttribute("mensajePagoRealizado", "¡Pago realizado con éxito! Ahora puedes acceder al contenido premium.");
            return new ModelAndView("redirect:/estadisticasUsuario", model);
        } else {
            return new ModelAndView("redirect:/home");
        }
    }
}