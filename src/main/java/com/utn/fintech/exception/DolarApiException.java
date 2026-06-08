package com.utn.fintech.exception;

public class DolarApiException extends RuntimeException {

    public DolarApiException(String mensaje, Throwable causa) {
        super("Error al consultar la cotizacion del dolar MEP: " + mensaje, causa);
    }
}
