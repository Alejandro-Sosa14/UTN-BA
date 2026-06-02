package com.utn.fintech.model;

// Interfaz que obliga a todas las cuentas a poder convertir su saldo a pesos
public interface Convertible {
    double convertirARS(double tasaCompra);
}

