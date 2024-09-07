package com.aly.propostaapp.controller;

import com.aly.propostaapp.payload.PropostaRequestDTO;
import com.aly.propostaapp.payload.PropostaResponseDTO;
import com.aly.propostaapp.service.PropostaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/proposta")
@RequiredArgsConstructor
public class PropostaController {
    private final PropostaService service;

    @PostMapping("/criar")
    public ResponseEntity<PropostaResponseDTO> criar(@RequestBody PropostaRequestDTO dto) {
        var response = service.criar(dto);
        URI uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(response.getId())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}
