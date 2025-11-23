package br.com.carlos.projeto.application.reserve.mapper;

import br.com.carlos.projeto.application.reserve.dto.ReserveDTO;
import br.com.carlos.projeto.domain.Reserve;
import org.mapstruct.Mapping;

@org.mapstruct.Mapper(componentModel = "spring", uses = {})
public interface ReserveMapper {
    @Mapping(source = "service.id", target = "service_id")
    @Mapping(source = "applicant.id", target = "applicant_id")
    public ReserveDTO toDTO(Reserve reserve);
}
