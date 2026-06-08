package com.utn.fintech.exception;

public class TipoCuentaInvalidoException extends RuntimeException {

    public TipoCuentaInvalidoException(String tipo) {
        super("Tipo de cuenta invalido: '" + tipo + "'. Los tipos permitidos son AHORRO o CORRIENTE.");
    }
}
