package com.aly.propostaapp.service;

import com.aly.propostaapp.payload.PropostaResponseDTO;
import com.aly.propostaapp.repository.PropostaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WebSocketService {
    private final PropostaRepository propostaRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public void enviarViaWs(PropostaResponseDTO proposta) {
        simpMessagingTemplate.convertAndSend("/propostas", proposta);
    }
}
