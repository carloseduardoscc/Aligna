package br.com.carlos.projeto.application;

import br.com.carlos.projeto.application.command.RegisterProfessionalProfileCommand;
import br.com.carlos.projeto.application.command.RegisterServiceCommand;
import br.com.carlos.projeto.application.command.RequestReserveCommand;
import br.com.carlos.projeto.application.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.dto.ReserveDTO;
import br.com.carlos.projeto.application.dto.ServiceDTO;
import br.com.carlos.projeto.application.dto.UserDTO;
import br.com.carlos.projeto.application.mapper.ProfessionalProfileMapper;
import br.com.carlos.projeto.application.mapper.ReserveMapper;
import br.com.carlos.projeto.application.mapper.ServiceMapper;
import br.com.carlos.projeto.application.mapper.UserMapper;
import br.com.carlos.projeto.domain.ProfessionalProfile;
import br.com.carlos.projeto.domain.Reserve;
import br.com.carlos.projeto.domain.Service;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.domain.repository.ReserveRepository;
import br.com.carlos.projeto.domain.repository.ServiceRepository;
import br.com.carlos.projeto.domain.repository.UserRepository;
import br.com.carlos.projeto.infra.persistence.entity.ReserveEntity;
import br.com.carlos.projeto.infra.persistence.entity.ServiceEntity;
import br.com.carlos.projeto.infra.persistence.entity.UserEntity;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.stream.Collectors;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class MeService {

    AuthenticationService auth;
    UserRepository<UserEntity> uRepo;
    ServiceRepository<ServiceEntity> sRepo;
    ReserveRepository<ReserveEntity> rRepo;
    ProfessionalProfileMapper pMapper;
    UserMapper uMapper;
    ReserveMapper rMapper;
    ServiceMapper sMapper;

    @Transactional
    public ReserveDTO requestReserve(RequestReserveCommand cmd) {
        User user = auth.getCurrentAuthenticatedUser();
        Service service = sMapper.fromEntity(sRepo.findById(cmd.service_id()));

        Reserve reserve = new Reserve(cmd.dateTime(), user, service);

        reserve = rMapper.fromEntity(rRepo.save(rMapper.toEntity(reserve)));

        return rMapper.toDTO(reserve);
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
        userEntity = uRepo.save(userEntity);
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

        Service service = new Service(
                cmd.title(),
                cmd.description(),
                cmd.availableFrom(),
                cmd.availableUntil(),
                cmd.availableDays().stream().map(DayOfWeek::valueOf).collect(Collectors.toCollection(HashSet::new)),
                profile);
        profile.addService(service);
        service.setProfessionalProfile(profile);

        ServiceEntity savedService = sRepo.save(sMapper.toEntity(service));
        return sMapper.toDTO(sMapper.fromEntity(savedService));
    }
}
