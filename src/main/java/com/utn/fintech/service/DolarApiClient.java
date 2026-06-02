package com.utn.fintech.service;

import com.utn.fintech.dto.DolarMepDTO;

// Interfaz para el cliente de DolarAPI
// Separar en interfaz permite mockearlo en los tests
public interface DolarApiClient {
    DolarMepDTO obtenerCotizacionMEP();
}

