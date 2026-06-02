package com.utn.fintech.controller;

import com.utn.fintech.dto.CuentaRequestDTO;
import com.utn.fintech.dto.CuentaResponseDTO;
import com.utn.fintech.service.CuentaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
@Tag(name = "Cuentas", description = "Operaciones sobre cuentas bancarias")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    @GetMapping
    @Operation(summary = "Listar todas las cuentas", description = "Devuelve todas las cuentas con el saldo en ARS calculado con la cotizacion MEP actual")
    public ResponseEntity<List<CuentaResponseDTO>> listar() {
        return ResponseEntity.ok(cuentaService.listarCuentas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener cuenta por ID", description = "Devuelve una cuenta especifica. Si no existe retorna 404")
    public ResponseEntity<CuentaResponseDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(cuentaService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva cuenta", description = "Crea una cuenta de tipo AHORRO o CORRIENTE asociada a un usuario")
    public ResponseEntity<CuentaResponseDTO> crear(@Valid @RequestBody CuentaRequestDTO request) {
        CuentaResponseDTO nueva = cuentaService.crearCuenta(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una cuenta", description = "Actualiza el saldo de una cuenta existente")
    public ResponseEntity<CuentaResponseDTO> actualizar(@PathVariable Long id,
                                                         @Valid @RequestBody CuentaRequestDTO request) {
        return ResponseEntity.ok(cuentaService.actualizarCuenta(id, request));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una cuenta", description = "Elimina una cuenta por su ID. Si no existe retorna 404")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return ResponseEntity.noContent().build();
    }
}

