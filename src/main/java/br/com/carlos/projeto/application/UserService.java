package br.com.carlos.projeto.application;

import br.com.carlos.projeto.application.command.RegisterUserCommand;
import br.com.carlos.projeto.application.dto.UserDTO;
import br.com.carlos.projeto.application.mapper.UserMapper;
import br.com.carlos.projeto.domain.repository.UserRepository;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserMapper mapper;
    private UserRepository<UserEntity> repo;

    public UserService(UserMapper mapper, UserRepository<UserEntity> repo) {
        this.mapper = mapper;
        this.repo = repo;
    }
}
