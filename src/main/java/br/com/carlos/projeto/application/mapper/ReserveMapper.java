package br.com.carlos.projeto.application.mapper;

import br.com.carlos.projeto.application.dto.ReserveDTO;
import br.com.carlos.projeto.domain.Reserve;
import br.com.carlos.projeto.infra.persistence.entity.ReserveEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ReserveMapper {
    /// Reserve Mappings

    @Mapping(target = "service.reserves", ignore = true)
    @Mapping(target = "applicant.reserves", ignore = true)
    public Reserve fromEntity(ReserveEntity reserveEntity);

    @Mapping(target = "service.reserves", ignore = true)
    @Mapping(target = "applicant.reserves", ignore = true)
    public ReserveEntity toEntity(Reserve reserve);

    @Mapping(source = "service.id", target = "service_id")
    @Mapping(source = "applicant.id", target = "applicant_id")
    public ReserveDTO toDTO(Reserve reserve);
}
