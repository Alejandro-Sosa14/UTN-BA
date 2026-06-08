package com.utn.fintech.service;

import com.utn.fintech.dto.DolarMepDTO;
import com.utn.fintech.exception.DolarApiException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class DolarApiClientImpl implements DolarApiClient {

    private final RestTemplate restTemplate;

    @Value("${dolar.api.url}")
    private String dolarApiUrl;

    public DolarApiClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public DolarMepDTO obtenerCotizacionMEP() {
        try {
            DolarMepDTO cotizacion = restTemplate.getForObject(dolarApiUrl, DolarMepDTO.class);
            if (cotizacion == null) {
                throw new DolarApiException("La respuesta de DolarAPI vino vacia", null);
            }
            return cotizacion;
        } catch (DolarApiException e) {
            throw e;
        } catch (Exception e) {
            throw new DolarApiException(e.getMessage(), e);
        }
    }
}

