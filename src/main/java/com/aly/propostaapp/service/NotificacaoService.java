package com.aly.propostaapp.service;

import com.aly.propostaapp.payload.PropostaResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificacaoService {
    private final RabbitTemplate template;

    public void notificar(PropostaResponseDTO dto, String exchange) {
        template.convertAndSend(exchange, "", dto);
    }
}
