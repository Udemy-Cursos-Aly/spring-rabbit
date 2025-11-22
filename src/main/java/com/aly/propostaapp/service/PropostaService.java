package com.aly.propostaapp.service;

import com.aly.propostaapp.entity.Proposta;
import com.aly.propostaapp.mapper.PropostaMapper;
import com.aly.propostaapp.payload.PropostaRequestDTO;
import com.aly.propostaapp.payload.PropostaResponseDTO;
import com.aly.propostaapp.repository.PropostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PropostaService {
    @Value("${rabbit.mq.exchange}")
    private String exchange;

    private final PropostaRepository repository;
    private final NotificacaoService notificacaoService;
    private final PropostaMapper propostaMapper = PropostaMapper.INSTANCE;

    public List<PropostaResponseDTO> buscarTodos() {
        return propostaMapper.toListResponseDTO(repository.findAll());
    }

    @Transactional
    public PropostaResponseDTO criar(PropostaRequestDTO dto) {
        final Proposta entity = propostaMapper.toProposta(dto);
        var entitySave = salvarProposta(entity);
        notificarRabbitMQ(entitySave);

        return propostaMapper.toResponseDTO(entitySave);
    }

    @Transactional
    public Proposta salvarProposta(Proposta proposta) {
        return repository.save(proposta);
    }

    private void notificarRabbitMQ(Proposta entity) {
        try {
            notificacaoService.notificar(entity, exchange);
        } catch (RuntimeException ex) {
            entity.setIntegrada(Boolean.FALSE);
            repository.save(entity);
        }
    }
}
