package br.com.carlos.projeto.application.user.useCase;

import br.com.carlos.projeto.application.authentication.useCase.GetLoggedUserUseCase;
import br.com.carlos.projeto.application.user.dto.UserDTO;
import br.com.carlos.projeto.application.user.mapper.UserMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class MeInfoUseCase {
    private final UserMapper mapper;
    private final GetLoggedUserUseCase auth;

    @Transactional
    public UserDTO me() {
        return mapper.toDTO(auth.execute());
    }
}
