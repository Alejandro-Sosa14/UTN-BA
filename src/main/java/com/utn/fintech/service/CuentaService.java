package com.utn.fintech.service;

import com.utn.fintech.dto.CuentaRequestDTO;
import com.utn.fintech.dto.CuentaResponseDTO;

import java.util.List;

public interface CuentaService {
    List<CuentaResponseDTO> listarCuentas();
    CuentaResponseDTO obtenerPorId(Long id);
    CuentaResponseDTO crearCuenta(CuentaRequestDTO request);
    CuentaResponseDTO actualizarCuenta(Long id, CuentaRequestDTO request);
    void eliminarCuenta(Long id);
}

