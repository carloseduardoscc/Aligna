package br.com.carlos.projeto.application.authentication.useCase;

import br.com.carlos.projeto.application.authentication.command.RegisterUserCommand;
import br.com.carlos.projeto.application.authentication.dto.RegisterResponseDTO;
import br.com.carlos.projeto.application.authentication.mapper.AuthMapper;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.repository.UserRepository;
import br.com.carlos.projeto.infra.security.exception.AuthenticationException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegisterUserCase {

    private static final Logger logger = LoggerFactory.getLogger("ACCESS_LOGGER");
    private final AuthMapper mapper;
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;

    @Transactional
    public RegisterResponseDTO execute(RegisterUserCommand registerDTO) {
        validateEmailIsNotDuplicated(registerDTO.email());
        String encryptedPassword = encoder.encode(registerDTO.password());
        User userWithEncodedPassword = mapper.fromRegisterUserCommand(registerDTO);
        userWithEncodedPassword.setPassword(encryptedPassword);
        User UserSaved = userRepo.save(userWithEncodedPassword);

        logger.atDebug().log("Usuário {} criado com sucesso!", registerDTO.email());

        return mapper.toDTO(UserSaved);
    }

    private void validateEmailIsNotDuplicated(String email) {
        if (userRepo.findByEmail(email) != null) {
            throw new AuthenticationException("O usuário com e-mail " + email + " já existe!");
        }
    }
}
