package br.com.carlos.projeto.application.professional.mapper;

import br.com.carlos.projeto.application.professional.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.service.dto.ServiceDTO;
import br.com.carlos.projeto.domain.ProfessionalProfile;
import br.com.carlos.projeto.domain.Service;
import org.mapstruct.Mapping;

@org.mapstruct.Mapper(componentModel = "spring", uses = {})
public interface ProfessionalMapper {
    @Mapping(source = "user.id", target = "userId")
    ProfessionalProfileDTO toDTO(ProfessionalProfile profile);
    @Mapping(source = "professionalProfile.id", target = "professionalProfileId")
    public ServiceDTO toDTO(Service service);
}
