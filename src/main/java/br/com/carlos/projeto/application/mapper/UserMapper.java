package br.com.carlos.projeto.application.mapper;

import br.com.carlos.projeto.application.command.RegisterUserCommand;
import br.com.carlos.projeto.application.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.dto.PublicUserDTO;
import br.com.carlos.projeto.application.dto.UserDTO;
import br.com.carlos.projeto.domain.ProfessionalProfile;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.persistence.entity.ProfessionalProfileEntity;
import br.com.carlos.projeto.infra.persistence.entity.UserEntity;
import br.com.carlos.projeto.infra.security.AuthUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = ProfessionalProfileMapper.class)
public interface UserMapper {
    /// User Mappings

    @Mapping(target = "professionalProfile.user", ignore = true)
    public User fromEntity(UserEntity userEntity);

    public User fromRegisterUserCommand(RegisterUserCommand cmd);

    public UserEntity toEntity(User user);

    @Mapping(source = "professionalProfile.id", target = "professionalProfileId")
    public UserDTO toDTO(User user);

    @Mapping(source = "professionalProfile.id", target = "professionalProfileId")
    public PublicUserDTO toPublicDTO(User user);

    public AuthUser toAuth(User user);

    /// ProfessionalProfile Mappings


}
