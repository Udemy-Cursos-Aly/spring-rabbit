package com.aly.propostaapp.service;

import com.aly.propostaapp.entity.Proposta;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificacaoService {
    private final RabbitTemplate template;

    public void notificar(Proposta entity, String exchange) {
        template.convertAndSend(exchange, "", entity);
    }
}
