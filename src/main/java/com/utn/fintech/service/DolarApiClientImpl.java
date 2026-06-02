package com.utn.fintech.service;

import com.utn.fintech.dto.DolarMepDTO;
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
            return cotizacion;
        } catch (Exception e) {
            throw new RuntimeException("No se pudo obtener la cotizacion del dolar MEP: " + e.getMessage());
        }
    }
}

