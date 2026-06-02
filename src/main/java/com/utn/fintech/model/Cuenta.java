package com.utn.fintech.model;

import jakarta.persistence.*;
import java.time.LocalDate;

// Clase abstracta base para todos los tipos de cuenta
// Usa herencia con estrategia JOINED (una tabla por subclase)
@Entity
@Table(name = "cuentas")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Cuenta implements Convertible {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroCuenta;
    private double saldoUSD;
    private double saldoARS;
    private LocalDate fechaCreacion;

    // Muchas cuentas pertenecen a un usuario
    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    public Cuenta() {}

    public Cuenta(String numeroCuenta, double saldoUSD, Usuario usuario) {
        this.numeroCuenta = numeroCuenta;
        this.saldoUSD = saldoUSD;
        this.usuario = usuario;
        this.fechaCreacion = LocalDate.now();
    }

    // Implementacion del metodo de la interfaz Convertible
    @Override
    public double convertirARS(double tasaCompra) {
        return this.saldoUSD * tasaCompra;
    }

    public Long getId() { return id; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public double getSaldoUSD() { return saldoUSD; }
    public double getSaldoARS() { return saldoARS; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public Usuario getUsuario() { return usuario; }

    public void setId(Long id) { this.id = id; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }
    public void setSaldoUSD(double saldoUSD) { this.saldoUSD = saldoUSD; }
    public void setSaldoARS(double saldoARS) { this.saldoARS = saldoARS; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}

