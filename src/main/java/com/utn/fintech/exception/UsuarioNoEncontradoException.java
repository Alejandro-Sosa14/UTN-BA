package com.utn.fintech.exception;

public class UsuarioNoEncontradoException extends RuntimeException {

    public UsuarioNoEncontradoException(Long id) {
        super("No se encontro ningun usuario con el ID: " + id);
    }
}
