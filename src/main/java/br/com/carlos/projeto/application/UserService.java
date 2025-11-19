package br.com.carlos.projeto.application;

import br.com.carlos.projeto.application.dto.PublicUserDTO;
import br.com.carlos.projeto.application.mapper.Mapper;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class UserService {

    private Mapper mapper;
    private UserRepository repo;

    @Transactional
    public PublicUserDTO findById(Long id) {
            return mapper.toPublicDTO(repo.findById(id).orElseThrow(()-> new NoSuchElementException("Usuário com id: " + id + " não encontrado.")));
    }

    @Transactional
    public Page<PublicUserDTO> findAll(Pageable pageable) {
        Page<User> userEntities = repo.findAll(pageable);
        return userEntities.map(entity -> mapper.toPublicDTO(entity));
    }
}
