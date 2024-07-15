package com.felps.forum.domain;

public class IdNegativoException extends IllegalArgumentException {
    public IdNegativoException(String mensagem) {
        super(mensagem);
    }
}
