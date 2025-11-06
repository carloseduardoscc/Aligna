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
import org.springframework.stereotype.Service;

@Service
public class MeService {

    AuthenticationService auth;
    UserRepository<UserEntity> userRepo;
    ProfessionalProfileMapper pMapper;
    UserMapper uMapper;

    public MeService(AuthenticationService auth, UserRepository<UserEntity> userRepo, ProfessionalProfileMapper pMapper, UserMapper uMapper) {
        this.auth = auth;
        this.userRepo = userRepo;
        this.pMapper = pMapper;
        this.uMapper = uMapper;
    }

    @Transactional
    public UserDTO me() {
        return uMapper.toDTO(auth.getCurrentAuthenticatedUser());
    }

    @Transactional
    public ProfessionalProfileDTO registerProfessionalProfile(RegisterProfessionalProfileCommand cmd) {
        ProfessionalProfile profile = new ProfessionalProfile(cmd.description());
        User user = auth.getCurrentAuthenticatedUser();
        user.setProfessionalProfile(profile);
        profile.setUser(user);

        UserEntity userEntity = uMapper.toEntity(user);
        userEntity = userRepo.save(userEntity);
        user = uMapper.fromEntity(userEntity);

        return pMapper.toDTO(user.getProfessionalProfile());
    }
}
