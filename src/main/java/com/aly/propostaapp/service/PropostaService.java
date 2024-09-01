package com.aly.propostaapp.service;

import com.aly.propostaapp.payload.PropostaRequestDTO;
import com.aly.propostaapp.payload.PropostaResponseDTO;
import com.aly.propostaapp.repository.PropostaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PropostaService {
    private final PropostaRepository repository;

    @Transactional
    public PropostaResponseDTO criar(PropostaRequestDTO dto) {
        return null;
    }
}
