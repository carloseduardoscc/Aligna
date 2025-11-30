package br.com.carlos.projeto.application.user.useCase;

import br.com.carlos.projeto.application.user.dto.PublicUserDTO;
import br.com.carlos.projeto.application.user.mapper.UserMapper;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FindAllUserUseCase {
    private UserMapper mapper;
    private UserRepository repo;

    @Transactional
    public Page<PublicUserDTO> execute(Pageable pageable) {
        Page<User> userEntities = repo.findAll(pageable);
        return userEntities.map(entity -> mapper.toPublicDTO(entity));
    }
}
