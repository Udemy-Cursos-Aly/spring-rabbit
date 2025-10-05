package com.aly.propostaapp.schedules;

import com.aly.propostaapp.entity.Proposta;
import com.aly.propostaapp.repository.PropostaRepository;
import com.aly.propostaapp.service.NotificacaoService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Log4j2
public class PropostaSchedule {
    private final PropostaRepository repository;
    private final NotificacaoService notificacaoService;

    @Value("${rabbit.mq.exchange}")
    private String exchange;

    public PropostaSchedule(PropostaRepository repository, NotificacaoService notificacaoService) {
        this.repository = repository;
        this.notificacaoService = notificacaoService;
    }

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.SECONDS)
    public void buscarPropostaSemIntegracao() {
        log.info("[Gateway] - Buscando propostas sem integração");

        repository.findAllByIntegradaIsFalse().forEach(this::notificarAndAtualizarPropostaSemIntegracao);
    }

    private void notificarAndAtualizarPropostaSemIntegracao(Proposta proposta) {
        try {
            notificacaoService.notificar(proposta, exchange);
            atualizarProposta(proposta);
        } catch (RuntimeException ex) {
            log.error("[Gateway] - Ocorreu um erro ao tentar enviar a fila e atualizar a proposta: {}",
                    ex.getMessage());
        }
    }

    private void atualizarProposta(Proposta proposta) {
        proposta.setIntegrada(Boolean.TRUE);
        Proposta propostaAtualizada = repository.save(proposta);

        log.info("[Gateway] - Proposta atualizada com sucesso: {}", propostaAtualizada);
    }
}
