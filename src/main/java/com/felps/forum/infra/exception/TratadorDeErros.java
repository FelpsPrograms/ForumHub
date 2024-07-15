package com.felps.forum.infra.exception;

import com.felps.forum.domain.DuplicidadeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(DuplicidadeException.class)
    public ResponseEntity tratarErroRegraDeNegocio(DuplicidadeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

}
