package com.utn.fintech.service;

import com.utn.fintech.dto.CuentaRequestDTO;
import com.utn.fintech.dto.CuentaResponseDTO;
import com.utn.fintech.dto.DolarMepDTO;
import com.utn.fintech.exception.CuentaNoEncontradaException;
import com.utn.fintech.exception.TipoCuentaInvalidoException;
import com.utn.fintech.exception.UsuarioNoEncontradoException;
import com.utn.fintech.model.CuentaAhorro;
import com.utn.fintech.model.Usuario;
import com.utn.fintech.repository.CuentaRepository;
import com.utn.fintech.repository.UsuarioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CuentaServiceImplTest {

    @Mock
    private CuentaRepository cuentaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private DolarApiClient dolarApiClient;

    @InjectMocks
    private CuentaServiceImpl cuentaService;

    private Usuario usuarioMock;
    private CuentaAhorro cuentaMock;
    private DolarMepDTO cotizacionMock;

    @BeforeEach
    void setUp() {
        usuarioMock = new Usuario("Juan", "Perez", "juan@email.com", "30123456");
        usuarioMock.setId(1L);

        cuentaMock = new CuentaAhorro("CBU-ABC123", 1000.0, usuarioMock, 500.0);
        cuentaMock.setId(1L);

        cotizacionMock = new DolarMepDTO();
        cotizacionMock.setCompra(1200.0);
        cotizacionMock.setVenta(1210.0);
    }

    @Test
    void listarCuentas_deberiaRetornarListaConSaldoARS() {
        when(dolarApiClient.obtenerCotizacionMEP()).thenReturn(cotizacionMock);
        when(cuentaRepository.findAll()).thenReturn(List.of(cuentaMock));

        List<CuentaResponseDTO> resultado = cuentaService.listarCuentas();

        assertFalse(resultado.isEmpty());
        assertEquals(1200.0 * 1000.0, resultado.get(0).getSaldoARS());
        assertEquals(1200.0, resultado.get(0).getTasaMEP());
    }

    @Test
    void obtenerPorId_cuentaExistente_deberiaRetornarDTO() {
        when(dolarApiClient.obtenerCotizacionMEP()).thenReturn(cotizacionMock);
        when(cuentaRepository.findById(1L)).thenReturn(Optional.of(cuentaMock));

        CuentaResponseDTO resultado = cuentaService.obtenerPorId(1L);

        assertNotNull(resultado);
        assertEquals("AHORRO", resultado.getTipoCuenta());
        assertEquals(1000.0, resultado.getSaldoUSD());
    }

    @Test
    void obtenerPorId_cuentaInexistente_deberiaLanzarExcepcion() {
        when(cuentaRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(CuentaNoEncontradaException.class, () -> cuentaService.obtenerPorId(99L));
    }

    @Test
    void crearCuenta_tipoAhorro_deberiaGuardarYRetornarDTO() {
        CuentaRequestDTO request = new CuentaRequestDTO();
        request.setTipoCuenta("AHORRO");
        request.setSaldoUSD(500.0);
        request.setUsuarioId(1L);
        request.setLimiteExtraccionDiario(200.0);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        when(dolarApiClient.obtenerCotizacionMEP()).thenReturn(cotizacionMock);
        when(cuentaRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        CuentaResponseDTO resultado = cuentaService.crearCuenta(request);

        assertNotNull(resultado);
        assertEquals("AHORRO", resultado.getTipoCuenta());
        assertEquals(500.0 * 1200.0, resultado.getSaldoARS());
        verify(cuentaRepository, times(1)).save(any());
    }

    @Test
    void eliminarCuenta_cuentaInexistente_deberiaLanzarExcepcion() {
        when(cuentaRepository.existsById(99L)).thenReturn(false);

        assertThrows(CuentaNoEncontradaException.class, () -> cuentaService.eliminarCuenta(99L));
    }

    @Test
    void eliminarCuenta_cuentaExistente_deberiaEliminar() {
        when(cuentaRepository.existsById(1L)).thenReturn(true);

        cuentaService.eliminarCuenta(1L);

        verify(cuentaRepository, times(1)).deleteById(1L);
    }

    @Test
    void crearCuenta_usuarioInexistente_deberiaLanzarExcepcion() {
        CuentaRequestDTO request = new CuentaRequestDTO();
        request.setTipoCuenta("AHORRO");
        request.setSaldoUSD(500.0);
        request.setUsuarioId(99L);

        when(usuarioRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class, () -> cuentaService.crearCuenta(request));
    }

    @Test
    void crearCuenta_tipoInvalido_deberiaLanzarExcepcion() {
        CuentaRequestDTO request = new CuentaRequestDTO();
        request.setTipoCuenta("PLAZO_FIJO");
        request.setSaldoUSD(500.0);
        request.setUsuarioId(1L);

        when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuarioMock));
        when(dolarApiClient.obtenerCotizacionMEP()).thenReturn(cotizacionMock);

        assertThrows(TipoCuentaInvalidoException.class, () -> cuentaService.crearCuenta(request));
    }
}

