package com.portfolio.portfolio.common.excpetion.custom;

public class UsuarioNaoEcontradoException extends RuntimeException {
    public UsuarioNaoEcontradoException(String message) {
        super(message);
    }
}
