package br.com.carlos.projeto.application;

import br.com.carlos.projeto.application.command.RegisterProfessionalProfileCommand;
import br.com.carlos.projeto.application.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.dto.PublicUserDTO;
import br.com.carlos.projeto.application.dto.UserDTO;
import br.com.carlos.projeto.application.mapper.ProfessionalProfileMapper;
import br.com.carlos.projeto.application.mapper.UserMapper;
import br.com.carlos.projeto.domain.ProfessionalProfile;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.domain.repository.UserRepository;
import br.com.carlos.projeto.infra.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {

    private UserMapper uMapper;
    private UserRepository<UserEntity> repo;

    public UserService(UserMapper uMapper, UserRepository<UserEntity> repo) {
        this.uMapper = uMapper;
        this.repo = repo;
    }

    @Transactional
    public PublicUserDTO findById(Long id) {
        try {
            return uMapper.toPublicDTO(uMapper.fromEntity(repo.findById(id)));

        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("Usuário com id: " + id+" não encontrado.");
        }


    }

    @Transactional
    public Page<PublicUserDTO> findAll(Pageable pageable) {
        Page<UserEntity> userEntities = repo.findAll(pageable);
        return userEntities.map(entity -> uMapper.toPublicDTO(uMapper.fromEntity(entity)));
    }
}
