package br.com.carlos.projeto.application.mapper;

import br.com.carlos.projeto.application.command.RegisterServiceCommand;
import br.com.carlos.projeto.application.dto.ServiceDTO;
import br.com.carlos.projeto.domain.Reserve;
import br.com.carlos.projeto.domain.Service;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.persistence.entity.ReserveEntity;
import br.com.carlos.projeto.infra.persistence.entity.ServiceEntity;
import br.com.carlos.projeto.infra.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;

@Mapper(componentModel = "spring", uses = {ReserveMapper.class})
public interface ServiceMapper {
    /// Service Mappings

    @Mapping(target = "professionalProfile.services", ignore = true)
    public Service fromEntity(ServiceEntity serviceEntity);

    @Mapping(target = "professionalProfile.services", ignore = true)
    public ServiceEntity toEntity(Service service);

    @Mapping(source = "professionalProfile.id", target = "professionalProfileId")
    public ServiceDTO toDTO(Service service);
}
