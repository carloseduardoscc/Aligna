package br.com.carlos.projeto.application.authentication.useCase;

import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.repository.UserRepository;
import br.com.carlos.projeto.infra.security.AuthUser;
import br.com.carlos.projeto.infra.security.exception.AuthenticationException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GetLoggedUserUseCase {
    public final UserRepository repo;

    @Transactional
    public User execute() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof AuthUser)) {
            throw new AuthenticationException("É necessário estar autenticado");
        }

        AuthUser authUser = (AuthUser) auth.getPrincipal();
        return repo.findById((authUser.getId())).orElseThrow(() -> new AuthenticationException("Usuário autenticado não encontrado"));
    }
}
