package com.aly.propostaapp.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;

public class PropostaNaoEncontradaException extends PropostaException {
    private final String message;

    public PropostaNaoEncontradaException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    protected ProblemDetail toProblemDetail() {
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setTitle("Proposta não encontrada.");
        problemDetail.setDetail(this.message);

        return problemDetail;
    }
}
