package com.utn.fintech.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class CuentaRequestDTO {

    @NotBlank(message = "El tipo de cuenta es obligatorio (AHORRO o CORRIENTE)")
    private String tipoCuenta;

    @Positive(message = "El saldo en USD debe ser mayor a 0")
    private double saldoUSD;

    @NotNull(message = "El ID del usuario es obligatorio")
    private Long usuarioId;

    // Para cuenta ahorro
    private double limiteExtraccionDiario;

    // Para cuenta corriente
    private double descubierto;

    public String getTipoCuenta() { return tipoCuenta; }
    public double getSaldoUSD() { return saldoUSD; }
    public Long getUsuarioId() { return usuarioId; }
    public double getLimiteExtraccionDiario() { return limiteExtraccionDiario; }
    public double getDescubierto() { return descubierto; }

    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }
    public void setSaldoUSD(double saldoUSD) { this.saldoUSD = saldoUSD; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }
    public void setLimiteExtraccionDiario(double limiteExtraccionDiario) { this.limiteExtraccionDiario = limiteExtraccionDiario; }
    public void setDescubierto(double descubierto) { this.descubierto = descubierto; }
}

