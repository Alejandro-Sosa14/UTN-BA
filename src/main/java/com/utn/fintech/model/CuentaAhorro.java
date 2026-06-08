package com.utn.fintech.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuentas_ahorro")
public class CuentaAhorro extends Cuenta {

    private double limiteExtraccionDiario;

    public CuentaAhorro() {}

    public CuentaAhorro(String numeroCuenta, double saldoUSD, Usuario usuario, double limiteExtraccionDiario) {
        super(numeroCuenta, saldoUSD, usuario);
        this.limiteExtraccionDiario = limiteExtraccionDiario;
    }

    @Override
    public String getTipo() { return "AHORRO"; }

    public double getLimiteExtraccionDiario() { return limiteExtraccionDiario; }
    public void setLimiteExtraccionDiario(double limiteExtraccionDiario) { this.limiteExtraccionDiario = limiteExtraccionDiario; }
}

