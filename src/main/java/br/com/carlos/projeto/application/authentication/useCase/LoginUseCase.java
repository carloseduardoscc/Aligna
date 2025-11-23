package br.com.carlos.projeto.application.authentication.useCase;

import br.com.carlos.projeto.application.authentication.command.LoginCommand;
import br.com.carlos.projeto.application.authentication.dto.LoginResponseDTO;
import br.com.carlos.projeto.infra.security.AuthUser;
import br.com.carlos.projeto.infra.security.TokenService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoginUseCase {

    private static final Logger logger = LoggerFactory.getLogger("ACCESS_LOGGER");
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    @Transactional
    public LoginResponseDTO execute(LoginCommand cmd) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(cmd.login(), cmd.password());
        Authentication auth;
        auth = authenticationManager.authenticate(usernamePassword);
        AuthUser user = (AuthUser) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        logger.atDebug().log("Usuário {} autenticado com sucesso!", user.getUsername());

        return new LoginResponseDTO(token);
    }
}
