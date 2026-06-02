package com.utn.fintech.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "cuentas_corriente")
public class CuentaCorriente extends Cuenta {

    private double descubierto;

    public CuentaCorriente() {}

    public CuentaCorriente(String numeroCuenta, double saldoUSD, Usuario usuario, double descubierto) {
        super(numeroCuenta, saldoUSD, usuario);
        this.descubierto = descubierto;
    }

    public double getDescubierto() { return descubierto; }
    public void setDescubierto(double descubierto) { this.descubierto = descubierto; }
}

