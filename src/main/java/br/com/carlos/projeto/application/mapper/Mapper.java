package br.com.carlos.projeto.application.mapper;

import br.com.carlos.projeto.application.command.RegisterUserCommand;
import br.com.carlos.projeto.application.dto.*;
import br.com.carlos.projeto.domain.ProfessionalProfile;
import br.com.carlos.projeto.domain.Reserve;
import br.com.carlos.projeto.domain.Service;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.security.AuthUser;
import org.mapstruct.Mapping;

@org.mapstruct.Mapper(componentModel = "spring", uses = {})
public interface Mapper {
    /// User Mappings

    public User fromRegisterUserCommand(RegisterUserCommand cmd);

    @Mapping(source = "professionalProfile.id", target = "professionalProfileId")
    public UserDTO toDTO(User user);

    @Mapping(source = "professionalProfile.id", target = "professionalProfileId")
    public PublicUserDTO toPublicDTO(User user);

    public AuthUser toAuth(User user);

    /// ProfessionalProfile Mappings

    @Mapping(source = "user.id", target = "userId")
    ProfessionalProfileDTO toDTO(ProfessionalProfile profile);


    @Mapping(source = "professionalProfile.id", target = "professionalProfileId")
    public ServiceDTO toDTO(Service service);

    /// Reserve Mappings

    @Mapping(source = "service.id", target = "service_id")
    @Mapping(source = "applicant.id", target = "applicant_id")
    public ReserveDTO toDTO(Reserve reserve);
}
