package com.aly.propostaapp.listener;

import com.aly.propostaapp.entity.Proposta;
import com.aly.propostaapp.service.PropostaService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropostaConcluidaListener {
    private final PropostaService propostaService;

    @RabbitListener(queues = "${rabbit.mq.queue.pc.ms.credito}")
    public void propostaConcluidaListener(Proposta proposta) {
        propostaService.salvarProposta(proposta);
    }
}
