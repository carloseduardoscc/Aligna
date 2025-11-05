package br.com.carlos.projeto.application;

import br.com.carlos.projeto.application.command.RegisterProfessionalProfileCommand;
import br.com.carlos.projeto.application.dto.ProfessionalProfileDTO;
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
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserMapper uMapper;
    private ProfessionalProfileMapper pMapper;
    private UserRepository<UserEntity> repo;
    private AuthenticationService auth;

    public UserService(UserMapper uMapper, ProfessionalProfileMapper pMapper, UserRepository<UserEntity> repo, AuthenticationService auth) {
        this.uMapper = uMapper;
        this.pMapper = pMapper;
        this.repo = repo;
        this.auth = auth;
    }

    @Transactional
    public ProfessionalProfileDTO registerProfessionalProfile(RegisterProfessionalProfileCommand cmd) {
        ProfessionalProfile profile = new ProfessionalProfile(cmd.description());
        User user = auth.getCurrentAuthenticatedUser();
        user.setProfessionalProfile(profile);
        profile.setUser(user);

        UserEntity userEntity = uMapper.toEntity(user);
        userEntity = repo.save(userEntity);
        user = uMapper.fromEntity(userEntity);

        return pMapper.toDTO(user.getProfessionalProfile());
    }

    @Transactional
    public UserDTO findById(Long id) {
        try {
            return uMapper.toDTO(uMapper.fromEntity(repo.findById(id)));

        } catch (NoSuchElementException ex) {
            throw new NoSuchElementException("User not found with id: " + id);
        }


    }

    @Transactional
    public Page<UserDTO> findAll(Pageable pageable) {
        Page<UserEntity> userEntities = repo.findAll(pageable);
        return userEntities.map(entity -> uMapper.toDTO(uMapper.fromEntity(entity)));
    }
}
