package com.tallerwebi.presentacion;

import com.mercadopago.MercadoPagoConfig;
import com.mercadopago.client.preference.PreferenceBackUrlsRequest;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.tallerwebi.dominio.Ejercicio;
import com.tallerwebi.dominio.UserBuyer;
import com.tallerwebi.dominio.Usuario;
import lombok.Value;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
@CrossOrigin(origins = "http://localhost:8080")
@RestController
public class ControladorPago {

    @RequestMapping(value = "/premium", method = RequestMethod.GET)
    public ModelAndView irApremium(HttpServletRequest request) {

        return new ModelAndView("premium");
    }


    @RequestMapping(value ="/api/mp",method = RequestMethod.POST)
    public String getList (@RequestBody UserBuyer userBuyer){
        if(userBuyer==null){return "error json :/";}
        String title = userBuyer.getTitle();
        int quantity=userBuyer.getQuantity();
        int price= userBuyer.getUnit_price();

        try{
            MercadoPagoConfig.setAccessToken("TEST-4626193547107194-062716-e5936344f95bd7d8a79be74b6d33bbca-637766730");

            PreferenceItemRequest itemRequest=PreferenceItemRequest.builder()
                    .title(title)
                    .quantity(quantity)
                    .unitPrice(new BigDecimal(price))
                    .currencyId("ARS")
                    .build();
            List<PreferenceItemRequest> items= new ArrayList<>();
            items.add(itemRequest);

            PreferenceBackUrlsRequest backUrls=PreferenceBackUrlsRequest
                    .builder().success("https://youtube.com")
                    .pending("https://youtube.com")
                    .failure("https://youtube.com")
                    .build();

            PreferenceRequest preferenceRequest=PreferenceRequest.builder()
                    .items(items)
                    .backUrls(backUrls)
                    .build();

            PreferenceClient client=new PreferenceClient();
            Preference preference=client.create(preferenceRequest);
            return preference.getId();
        } catch (MPException e) {
           return e.toString();
        } catch (MPApiException e) {
            return e.toString();
        }
    }
}
