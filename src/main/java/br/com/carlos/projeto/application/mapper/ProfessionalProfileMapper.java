package br.com.carlos.projeto.application.mapper;

import br.com.carlos.projeto.application.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.domain.ProfessionalProfile;
import br.com.carlos.projeto.infra.persistence.entity.ProfessionalProfileEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ServiceMapper.class)
public interface ProfessionalProfileMapper {

    @Mapping(target = "user.professionalProfile", ignore = true)
    ProfessionalProfile fromEntity(ProfessionalProfileEntity entity);

    @Mapping(target = "user.professionalProfile", ignore = true)
    ProfessionalProfileEntity toEntity(ProfessionalProfile profile);

    @Mapping(source = "user.id", target = "userId")
    ProfessionalProfileDTO toDTO(ProfessionalProfile profile);
}
