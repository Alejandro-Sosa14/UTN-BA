package com.utn.fintech.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.utn.fintech.dto.CuentaRequestDTO;
import com.utn.fintech.dto.CuentaResponseDTO;
import com.utn.fintech.exception.CuentaNoEncontradaException;
import com.utn.fintech.service.CuentaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CuentaController.class)
class CuentaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CuentaService cuentaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void listar_deberiaRetornar200ConListaDeCuentas() throws Exception {
        CuentaResponseDTO cuenta = new CuentaResponseDTO(1L, "CBU-ABC123", "AHORRO", 1000.0, 1200000.0, 1200.0, "Juan Perez");
        when(cuentaService.listarCuentas()).thenReturn(List.of(cuenta));

        mockMvc.perform(get("/cuentas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipoCuenta").value("AHORRO"))
                .andExpect(jsonPath("$[0].saldoUSD").value(1000.0));
    }

    @Test
    void obtenerPorId_existente_deberiaRetornar200() throws Exception {
        CuentaResponseDTO cuenta = new CuentaResponseDTO(1L, "CBU-ABC123", "AHORRO", 1000.0, 1200000.0, 1200.0, "Juan Perez");
        when(cuentaService.obtenerPorId(1L)).thenReturn(cuenta);

        mockMvc.perform(get("/cuentas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1));
    }

    @Test
    void obtenerPorId_inexistente_deberiaRetornar404() throws Exception {
        when(cuentaService.obtenerPorId(99L)).thenThrow(new CuentaNoEncontradaException(99L));

        mockMvc.perform(get("/cuentas/99"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void crear_datosValidos_deberiaRetornar201() throws Exception {
        CuentaRequestDTO request = new CuentaRequestDTO();
        request.setTipoCuenta("AHORRO");
        request.setSaldoUSD(500.0);
        request.setUsuarioId(1L);

        CuentaResponseDTO response = new CuentaResponseDTO(2L, "CBU-XYZ999", "AHORRO", 500.0, 600000.0, 1200.0, "Juan Perez");
        when(cuentaService.crearCuenta(any())).thenReturn(response);

        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.numeroCuenta").value("CBU-XYZ999"));
    }

    @Test
    void eliminar_existente_deberiaRetornar204() throws Exception {
        mockMvc.perform(delete("/cuentas/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void eliminar_inexistente_deberiaRetornar404() throws Exception {
        doThrow(new CuentaNoEncontradaException(99L)).when(cuentaService).eliminarCuenta(99L);

        mockMvc.perform(delete("/cuentas/99"))
                .andExpect(status().isNotFound());
    }
}

