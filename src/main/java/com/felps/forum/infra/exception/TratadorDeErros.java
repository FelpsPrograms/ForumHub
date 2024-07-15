package com.felps.forum.infra.exception;

import com.felps.forum.domain.DuplicidadeException;
import com.felps.forum.domain.IdNegativoException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorDeErros {

    @ExceptionHandler(DuplicidadeException.class)
    public ResponseEntity tratarErroDuplicidade(DuplicidadeException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(IdNegativoException.class)
    public ResponseEntity tratarErroIdNegativo(IdNegativoException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarErro404() {
        return ResponseEntity.notFound().build();
    }

}
