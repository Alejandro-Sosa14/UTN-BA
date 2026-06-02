package com.utn.fintech.dto;

// Mapea la respuesta JSON que devuelve DolarAPI para el dolar MEP
public class DolarMepDTO {

    private String moneda;
    private String casa;
    private String nombre;
    private double compra;
    private double venta;
    private String fechaActualizacion;

    public String getMoneda() { return moneda; }
    public String getCasa() { return casa; }
    public String getNombre() { return nombre; }
    public double getCompra() { return compra; }
    public double getVenta() { return venta; }
    public String getFechaActualizacion() { return fechaActualizacion; }

    public void setMoneda(String moneda) { this.moneda = moneda; }
    public void setCasa(String casa) { this.casa = casa; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setCompra(double compra) { this.compra = compra; }
    public void setVenta(double venta) { this.venta = venta; }
    public void setFechaActualizacion(String fechaActualizacion) { this.fechaActualizacion = fechaActualizacion; }
}

