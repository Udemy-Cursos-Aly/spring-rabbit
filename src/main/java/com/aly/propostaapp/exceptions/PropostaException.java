package com.aly.propostaapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public abstract class PropostaException extends RuntimeException {
    private final String message;

    public PropostaException(final String message) {
        super(message);
        this.message = message;
    }

    protected ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setTitle("Por favor! Entrar em contato com o suporte.");
        problemDetail.setDetail(this.message);

        return problemDetail;
    }
}
