package br.com.carlos.projeto.application.mapper;

import br.com.carlos.projeto.application.command.RegisterServiceCommand;
import br.com.carlos.projeto.application.dto.ServiceDTO;
import br.com.carlos.projeto.domain.Service;
import br.com.carlos.projeto.infra.persistence.entity.ServiceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServiceMapper {
    /// Service Mappings

    @Mapping(target = "professionalProfile.services", ignore = true)
    @Mapping(target = "professionalProfile.user.professionalProfile", ignore = true)
    public Service fromEntity(ServiceEntity serviceEntity);

    public Service fromRegisterServiceCommand(RegisterServiceCommand cmd);

    @Mapping(target = "professionalProfile.services", ignore = true)
    public ServiceEntity toEntity(Service service);

    @Mapping(source = "professionalProfile.id", target = "professionalProfileId")
    public ServiceDTO toDTO(Service service);

}
