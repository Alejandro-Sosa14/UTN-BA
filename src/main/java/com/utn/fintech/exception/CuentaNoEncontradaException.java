package com.utn.fintech.exception;

public class CuentaNoEncontradaException extends RuntimeException {

    public CuentaNoEncontradaException(Long id) {
        super("No se encontro ninguna cuenta con el ID: " + id);
    }
}

