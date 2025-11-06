package br.com.carlos.projeto.application;

import br.com.carlos.projeto.application.command.LoginCommand;
import br.com.carlos.projeto.application.command.RegisterUserCommand;
import br.com.carlos.projeto.application.dto.LoginResponseDTO;
import br.com.carlos.projeto.application.dto.UserDTO;
import br.com.carlos.projeto.application.mapper.UserMapper;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.domain.repository.UserRepository;
import br.com.carlos.projeto.infra.persistence.entity.UserEntity;
import br.com.carlos.projeto.infra.security.AuthUser;
import br.com.carlos.projeto.infra.security.TokenService;
import br.com.carlos.projeto.infra.security.exception.AuthenticationException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthenticationService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger("ACCESS_LOGGER");
    private final UserMapper mapper;
    private final UserRepository<UserEntity> repo;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;

    public AuthenticationService(UserMapper mapper, PasswordEncoder passwordEncoder, @Lazy AuthenticationManager authenticationManager, TokenService tokenService, UserRepository<UserEntity> repo) {
        this.mapper = mapper;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.repo = repo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails userDetails = mapper.toAuth(mapper.fromEntity(repo.findByEmail(username)));
        if (userDetails == null) {
            throw new UsernameNotFoundException("Usuário com e-mail " + username + " não encontrado!");
        }
        return userDetails;
    }

    @Transactional
    public LoginResponseDTO login(LoginCommand cmd) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(cmd.login(), cmd.password());
        Authentication auth;
        auth = authenticationManager.authenticate(usernamePassword);
        AuthUser user = (AuthUser) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        logger.atDebug().log("Usuário {} autenticado com sucesso!", user.getUsername());

        return new LoginResponseDTO(token);
    }

    @Transactional
    public UserDTO register(RegisterUserCommand registerDTO) {
        validateEmailIsNotDuplicated(registerDTO.email());
        String encryptedPassword = passwordEncoder.encode(registerDTO.password());
        UserEntity userWithEncodedPassword = mapper.toEntity(mapper.fromRegisterUserCommand(registerDTO));
        userWithEncodedPassword.setPassword(encryptedPassword);
        UserEntity UserSaved = repo.save(userWithEncodedPassword);

        logger.atDebug().log("Usuário {} criado com sucesso!", registerDTO.email());

        return mapper.toDTO(mapper.fromEntity(UserSaved));
    }

    protected User getCurrentAuthenticatedUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof AuthUser)) {
            throw new AuthenticationException("É necessário estar autenticado");
        }

        AuthUser authUser = (AuthUser) auth.getPrincipal();
        return mapper.fromEntity(repo.findById((authUser.getId())));
    }

    private void validateEmailIsNotDuplicated(String email) {
        if (repo.findByEmail(email) != null) {
            throw new AuthenticationException("O usuário com e-mail " + email + " já existe!");
        }
    }
}