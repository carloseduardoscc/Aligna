package br.com.carlos.projeto.application.service.mapper;

import br.com.carlos.projeto.application.service.dto.ServiceDTO;
import br.com.carlos.projeto.domain.Service;
import org.mapstruct.Mapping;

@org.mapstruct.Mapper(componentModel = "spring", uses = {})
public interface ServiceMapper {
    @Mapping(source = "professionalProfile.id", target = "professionalProfileId")
    public ServiceDTO toDTO(Service service);
}
