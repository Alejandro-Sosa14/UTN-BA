package com.utn.fintech.dto;

public class CuentaResponseDTO {

    private Long id;
    private String numeroCuenta;
    private String tipoCuenta;
    private double saldoUSD;
    private double saldoARS;
    private double tasaMEP;
    private String nombreUsuario;

    public CuentaResponseDTO() {}

    public CuentaResponseDTO(Long id, String numeroCuenta, String tipoCuenta,
                              double saldoUSD, double saldoARS, double tasaMEP, String nombreUsuario) {
        this.id = id;
        this.numeroCuenta = numeroCuenta;
        this.tipoCuenta = tipoCuenta;
        this.saldoUSD = saldoUSD;
        this.saldoARS = saldoARS;
        this.tasaMEP = tasaMEP;
        this.nombreUsuario = nombreUsuario;
    }

    public Long getId() { return id; }
    public String getNumeroCuenta() { return numeroCuenta; }
    public String getTipoCuenta() { return tipoCuenta; }
    public double getSaldoUSD() { return saldoUSD; }
    public double getSaldoARS() { return saldoARS; }
    public double getTasaMEP() { return tasaMEP; }
    public String getNombreUsuario() { return nombreUsuario; }

    public void setId(Long id) { this.id = id; }
    public void setNumeroCuenta(String numeroCuenta) { this.numeroCuenta = numeroCuenta; }
    public void setTipoCuenta(String tipoCuenta) { this.tipoCuenta = tipoCuenta; }
    public void setSaldoUSD(double saldoUSD) { this.saldoUSD = saldoUSD; }
    public void setSaldoARS(double saldoARS) { this.saldoARS = saldoARS; }
    public void setTasaMEP(double tasaMEP) { this.tasaMEP = tasaMEP; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
}

