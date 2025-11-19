package br.com.carlos.projeto.application;

import br.com.carlos.projeto.application.command.RegisterProfessionalProfileCommand;
import br.com.carlos.projeto.application.command.RegisterServiceCommand;
import br.com.carlos.projeto.application.command.RequestReserveCommand;
import br.com.carlos.projeto.application.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.dto.ReserveDTO;
import br.com.carlos.projeto.application.dto.ServiceDTO;
import br.com.carlos.projeto.application.dto.UserDTO;
import br.com.carlos.projeto.application.mapper.Mapper;
import br.com.carlos.projeto.domain.ProfessionalProfile;
import br.com.carlos.projeto.domain.Reserve;
import br.com.carlos.projeto.domain.Service;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.repository.ReserveRepository;
import br.com.carlos.projeto.infra.repository.ServiceRepository;
import br.com.carlos.projeto.infra.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.stream.Collectors;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class MeService {

    AuthenticationService auth;
    UserRepository uRepo;
    ServiceRepository sRepo;
    ReserveRepository rRepo;
    Mapper mapper;

    @Transactional
    public ReserveDTO requestReserve(RequestReserveCommand cmd) {
        User currentUser = auth.getCurrentAuthenticatedUser();
        Service service = sRepo.findById(cmd.service_id())
                .orElseThrow(() -> new IllegalArgumentException("Serviço não encontrado com o ID: " + cmd.service_id()));;

        Reserve reserve = new Reserve(cmd.dateTime(), currentUser, service);
        currentUser.addReserve(reserve);
        service.addReserve(reserve);

        reserve = rRepo.save(reserve);

        return mapper.toDTO(reserve);

    }

    @Transactional
    public UserDTO me() {
        return mapper.toDTO(auth.getCurrentAuthenticatedUser());
    }

    @Transactional
    public ProfessionalProfileDTO registerProfessionalProfile(RegisterProfessionalProfileCommand cmd) {
        ProfessionalProfile profile = new ProfessionalProfile(cmd.description());
        User user = auth.getCurrentAuthenticatedUser();
        user.setProfessionalProfile(profile);
        profile.setUser(user);

        user = uRepo.save(user);

        return mapper.toDTO(user.getProfessionalProfile());
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

        Service savedService = sRepo.save(service);
        return mapper.toDTO(savedService);
    }
}
