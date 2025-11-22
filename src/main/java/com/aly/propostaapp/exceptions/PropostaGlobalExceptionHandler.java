package com.aly.propostaapp.exceptions;

import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class PropostaGlobalExceptionHandler {
    @ExceptionHandler(PropostaException.class)
    public ProblemDetail propostaException(PropostaException exception) {
        return exception.toProblemDetail();
    }
}
