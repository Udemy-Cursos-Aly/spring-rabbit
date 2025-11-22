package com.aly.propostaapp.repository;

import com.aly.propostaapp.entity.Proposta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PropostaRepository extends JpaRepository<Proposta, Long> {
    List<Proposta> findAllByIntegradaIsFalse();

    @Query(value = """
    UPDATE Proposta p SET p.aprovada = :aprovada, p.observacao = :observacao WHERE p.id = :id
    """)
    @Modifying
    @Transactional
    void atualizarProposta(@Param("aprovada") Boolean aprovada, @Param("observacao") String observacao, @Param("id") Long id);
}
