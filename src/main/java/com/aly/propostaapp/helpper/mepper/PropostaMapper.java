package com.aly.propostaapp.helpper.mepper;

import com.aly.propostaapp.entity.Proposta;
import com.aly.propostaapp.payload.PropostaRequestDTO;
import com.aly.propostaapp.payload.PropostaResponseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

@Mapper
public interface PropostaMapper {
    PropostaMapper INSTANCE = Mappers.getMapper(PropostaMapper.class);

    @Mapping(target = "usuario.nome", source = "nome")
    @Mapping(target = "usuario.sobrenome", source = "sobrenome")
    @Mapping(target = "usuario.cpf", source = "cpf")
    @Mapping(target = "usuario.telefone", source = "telefone")
    @Mapping(target = "usuario.renda", source = "renda")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "aprovado", ignore = true)
    @Mapping(target = "integrada", constant = "true")
    @Mapping(target = "observacao", ignore = true)
    Proposta toProposta(PropostaRequestDTO dto);

    @Mapping(target = "nome", source = "usuario.nome")
    @Mapping(target = "sobrenome", source = "usuario.sobrenome")
    @Mapping(target = "cpf", source = "usuario.cpf")
    @Mapping(target = "telefone", source = "usuario.telefone")
    @Mapping(target = "renda", source = "usuario.renda")
    @Mapping(target = "valorSolicitadoFmt", expression = "java(setValorSolicitadoFmt(entity))")
    PropostaResponseDTO toResponseDTO(Proposta entity);

    List<PropostaResponseDTO> toListResponseDTO(List<Proposta> listEntity);

    default String setValorSolicitadoFmt(Proposta entity) {
        return NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(entity.getValorSolicitado());
    }
}
