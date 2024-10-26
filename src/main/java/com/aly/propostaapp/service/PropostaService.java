package com.aly.propostaapp.service;

import com.aly.propostaapp.helpper.mepper.PropostaMapper;
import com.aly.propostaapp.payload.PropostaRequestDTO;
import com.aly.propostaapp.payload.PropostaResponseDTO;
import com.aly.propostaapp.repository.PropostaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropostaService {
    @Value("${rabbit.mq.exchange}")
    private String exchange;

    private final PropostaRepository repository;
    private final NotificacaoService notificacaoService;

    public List<PropostaResponseDTO> buscarTodos() {
        return PropostaMapper.INSTANCE.toListResponseDTO(repository.findAll());
    }

    @Transactional
    public PropostaResponseDTO criar(PropostaRequestDTO dto) {
        var entitySave = repository.save(PropostaMapper.INSTANCE.toProposta(dto));
        var response = PropostaMapper.INSTANCE.toResponseDTO(entitySave);

        notificacaoService.notificar(response, exchange);
        return response;
    }
}
