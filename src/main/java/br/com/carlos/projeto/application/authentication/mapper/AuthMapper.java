package br.com.carlos.projeto.application.authentication.mapper;

import br.com.carlos.projeto.application.authentication.command.RegisterUserCommand;
import br.com.carlos.projeto.application.authentication.dto.RegisterResponseDTO;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.security.AuthUser;

@org.mapstruct.Mapper(componentModel = "spring", uses = {})
public interface AuthMapper {
    public User fromRegisterUserCommand(RegisterUserCommand cmd);
    public AuthUser toAuth(User user);
    public RegisterResponseDTO toDTO(User user);
}
