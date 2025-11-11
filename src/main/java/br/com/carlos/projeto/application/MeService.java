package br.com.carlos.projeto.application;

import br.com.carlos.projeto.application.command.RegisterProfessionalProfileCommand;
import br.com.carlos.projeto.application.command.RegisterServiceCommand;
import br.com.carlos.projeto.application.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.dto.ServiceDTO;
import br.com.carlos.projeto.application.dto.UserDTO;
import br.com.carlos.projeto.application.mapper.ProfessionalProfileMapper;
import br.com.carlos.projeto.application.mapper.ServiceMapper;
import br.com.carlos.projeto.application.mapper.UserMapper;
import br.com.carlos.projeto.domain.ProfessionalProfile;
import br.com.carlos.projeto.domain.Service;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.domain.repository.ServiceRepository;
import br.com.carlos.projeto.domain.repository.UserRepository;
import br.com.carlos.projeto.infra.persistence.entity.ServiceEntity;
import br.com.carlos.projeto.infra.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@org.springframework.stereotype.Service
public class MeService {

    AuthenticationService auth;
    UserRepository<UserEntity> userRepo;
    ServiceRepository<ServiceEntity> serviceRepo;
    ProfessionalProfileMapper pMapper;
    UserMapper uMapper;
    ServiceMapper sMapper;

    public MeService(AuthenticationService auth, UserRepository<UserEntity> userRepo, ServiceRepository<ServiceEntity> serviceRepo, ProfessionalProfileMapper pMapper, UserMapper uMapper, ServiceMapper sMapper) {
        this.auth = auth;
        this.userRepo = userRepo;
        this.serviceRepo = serviceRepo;
        this.pMapper = pMapper;
        this.uMapper = uMapper;
        this.sMapper = sMapper;
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

    @Transactional
    public ServiceDTO registerService(RegisterServiceCommand cmd) {
        User user = auth.getCurrentAuthenticatedUser();
        ProfessionalProfile profile = user.getProfessionalProfile();

        if (profile == null) {
            throw new IllegalStateException("Usuário não possui perfil profissional registrado.");
        }

        Service service = sMapper.fromRegisterServiceCommand(cmd);
        profile.addService(service);
        service.setProfessionalProfile(profile);

        ServiceEntity savedService = serviceRepo.save(sMapper.toEntity(service));
        return sMapper.toDTO(sMapper.fromEntity(savedService));
    }
}
