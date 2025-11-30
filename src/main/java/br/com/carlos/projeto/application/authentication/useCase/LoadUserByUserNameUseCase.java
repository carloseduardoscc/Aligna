package br.com.carlos.projeto.application.authentication.useCase;

import br.com.carlos.projeto.application.authentication.mapper.AuthMapper;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LoadUserByUserNameUseCase implements UserDetailsService {

    private final AuthMapper mapper;
    private final UserRepository repo;

    public UserDetails execute(String username) throws UsernameNotFoundException {
        return loadUserByUsername(username);
    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User entity = repo.findByEmail(username);
        UserDetails userDetails = mapper.toAuth(entity);
        if (userDetails == null) {
            throw new UsernameNotFoundException("Usuário com e-mail " + username + " não encontrado!");
        }
        return userDetails;
    }
}
