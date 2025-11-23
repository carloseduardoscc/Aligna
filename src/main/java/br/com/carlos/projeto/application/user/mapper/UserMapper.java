package br.com.carlos.projeto.application.user.mapper;

import br.com.carlos.projeto.application.authentication.command.RegisterUserCommand;
import br.com.carlos.projeto.application.user.dto.PublicUserDTO;
import br.com.carlos.projeto.application.user.dto.UserDTO;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.security.AuthUser;
import org.mapstruct.Mapping;

@org.mapstruct.Mapper(componentModel = "spring", uses = {})
public interface UserMapper {
    public User fromRegisterUserCommand(RegisterUserCommand cmd);
    @Mapping(source = "professionalProfile.id", target = "professionalProfileId")
    public UserDTO toDTO(User user);
    @Mapping(source = "professionalProfile.id", target = "professionalProfileId")
    public PublicUserDTO toPublicDTO(User user);
    public AuthUser toAuth(User user);
}
