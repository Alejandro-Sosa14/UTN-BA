package com.utn.fintech.service;

import com.utn.fintech.dto.CuentaRequestDTO;
import com.utn.fintech.dto.CuentaResponseDTO;
import com.utn.fintech.dto.DolarMepDTO;
import com.utn.fintech.exception.CuentaNoEncontradaException;
import com.utn.fintech.model.*;
import com.utn.fintech.repository.CuentaRepository;
import com.utn.fintech.repository.UsuarioRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CuentaServiceImpl implements CuentaService {

    private final CuentaRepository cuentaRepository;
    private final UsuarioRepository usuarioRepository;
    private final DolarApiClient dolarApiClient;

    public CuentaServiceImpl(CuentaRepository cuentaRepository,
                              UsuarioRepository usuarioRepository,
                              DolarApiClient dolarApiClient) {
        this.cuentaRepository = cuentaRepository;
        this.usuarioRepository = usuarioRepository;
        this.dolarApiClient = dolarApiClient;
    }

    @Override
    public List<CuentaResponseDTO> listarCuentas() {
        DolarMepDTO cotizacion = dolarApiClient.obtenerCotizacionMEP();
        double tasaCompra = cotizacion.getCompra();

        return cuentaRepository.findAll().stream()
                .map(cuenta -> mapearAResponse(cuenta, tasaCompra))
                .collect(Collectors.toList());
    }

    @Override
    public CuentaResponseDTO obtenerPorId(Long id) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNoEncontradaException(id));

        DolarMepDTO cotizacion = dolarApiClient.obtenerCotizacionMEP();
        return mapearAResponse(cuenta, cotizacion.getCompra());
    }

    @Override
    public CuentaResponseDTO crearCuenta(CuentaRequestDTO request) {
        // Busco el usuario
        Usuario usuario = usuarioRepository.findById(request.getUsuarioId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + request.getUsuarioId()));

        // Obtengo la cotizacion del dolar MEP
        DolarMepDTO cotizacion = dolarApiClient.obtenerCotizacionMEP();
        double tasaCompra = cotizacion.getCompra();

        // Genero un numero de cuenta unico
        String numeroCuenta = "CBU-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        // Creo la cuenta segun el tipo
        Cuenta cuenta;
        if ("AHORRO".equalsIgnoreCase(request.getTipoCuenta())) {
            cuenta = new CuentaAhorro(numeroCuenta, request.getSaldoUSD(), usuario, request.getLimiteExtraccionDiario());
        } else if ("CORRIENTE".equalsIgnoreCase(request.getTipoCuenta())) {
            cuenta = new CuentaCorriente(numeroCuenta, request.getSaldoUSD(), usuario, request.getDescubierto());
        } else {
            throw new RuntimeException("Tipo de cuenta invalido. Usar AHORRO o CORRIENTE");
        }

        // Calculo el saldo en ARS usando la interfaz Convertible
        cuenta.setSaldoARS(cuenta.convertirARS(tasaCompra));

        cuentaRepository.save(cuenta);
        return mapearAResponse(cuenta, tasaCompra);
    }

    @Override
    public CuentaResponseDTO actualizarCuenta(Long id, CuentaRequestDTO request) {
        Cuenta cuenta = cuentaRepository.findById(id)
                .orElseThrow(() -> new CuentaNoEncontradaException(id));

        DolarMepDTO cotizacion = dolarApiClient.obtenerCotizacionMEP();
        double tasaCompra = cotizacion.getCompra();

        cuenta.setSaldoUSD(request.getSaldoUSD());
        cuenta.setSaldoARS(cuenta.convertirARS(tasaCompra));

        cuentaRepository.save(cuenta);
        return mapearAResponse(cuenta, tasaCompra);
    }

    @Override
    public void eliminarCuenta(Long id) {
        if (!cuentaRepository.existsById(id)) {
            throw new CuentaNoEncontradaException(id);
        }
        cuentaRepository.deleteById(id);
    }

    // Metodo auxiliar para convertir una Cuenta en un DTO de respuesta
    private CuentaResponseDTO mapearAResponse(Cuenta cuenta, double tasaCompra) {
        String tipoCuenta = cuenta instanceof CuentaAhorro ? "AHORRO" : "CORRIENTE";
        String nombreUsuario = cuenta.getUsuario() != null
                ? cuenta.getUsuario().getNombre() + " " + cuenta.getUsuario().getApellido()
                : "Sin usuario";

        return new CuentaResponseDTO(
                cuenta.getId(),
                cuenta.getNumeroCuenta(),
                tipoCuenta,
                cuenta.getSaldoUSD(),
                cuenta.convertirARS(tasaCompra),
                tasaCompra,
                nombreUsuario
        );
    }
}

