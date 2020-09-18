package com.example.api.exception;

public class UnfoundZipCodException extends RuntimeException {

    public UnfoundZipCodException(String mensagem) {
        super(mensagem);
    }
}
