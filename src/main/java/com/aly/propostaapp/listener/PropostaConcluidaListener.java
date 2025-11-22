package com.aly.propostaapp.listener;

import com.aly.propostaapp.entity.Proposta;
import com.aly.propostaapp.mapper.PropostaMapper;
import com.aly.propostaapp.service.PropostaService;
import com.aly.propostaapp.service.WebSocketService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PropostaConcluidaListener {
    private final PropostaService propostaService;
    private final WebSocketService wsService;
    private final PropostaMapper mapper = PropostaMapper.INSTANCE;

    @RabbitListener(queues = "${rabbit.mq.queue.pc.ms.credito}")
    public void propostaConcluidaListener(Proposta proposta) {
        atualizarPropostaConcluida(proposta.getId(), proposta.getAprovada(), proposta.getObservacao());
        wsService.enviarViaWs(mapper.toResponseDTO(proposta));
    }

    private void atualizarPropostaConcluida(Long id, Boolean aprovada, String observacao) {
        propostaService.atualizarPropostaAprovada(id, aprovada, observacao);
    }
}
