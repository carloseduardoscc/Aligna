package br.com.carlos.projeto.application.user.useCase;

import br.com.carlos.projeto.application.user.dto.PublicUserDTO;
import br.com.carlos.projeto.application.user.mapper.UserMapper;
import br.com.carlos.projeto.infra.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class FindUserByIdUseCase {

    private UserMapper mapper;
    private UserRepository repo;

    @Transactional
    public PublicUserDTO execute(Long id) {
        return mapper.toPublicDTO(repo.findById(id).orElseThrow(()-> new NoSuchElementException("Usuário com id: " + id + " não encontrado.")));
    }
}
