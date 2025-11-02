package br.com.carlos.projeto.application.mapper;

import br.com.carlos.projeto.application.command.RegisterUserCommand;
import br.com.carlos.projeto.application.dto.UserDTO;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.persistence.entity.UserEntity;
import br.com.carlos.projeto.infra.security.AuthUser;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    public User fromEntity(UserEntity userEntity);

    public User fromRegisterUserCommand(RegisterUserCommand cmd);

    public UserEntity toEntity(User user);

    public UserDTO toDTO(User user);

    public AuthUser toAuth(User user);

}
