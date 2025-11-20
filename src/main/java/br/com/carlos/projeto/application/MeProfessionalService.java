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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class MeProfessionalService {

    AuthenticationService auth;
    UserRepository uRepo;
    ServiceRepository sRepo;
    ReserveRepository rRepo;
    Mapper mapper;

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

    @Transactional
    public List<ServiceDTO> getServices() {
        User user = auth.getCurrentAuthenticatedUser();
        ProfessionalProfile profile = user.getProfessionalProfile();

        if (profile == null) {
            throw new IllegalStateException("Usuário não possui perfil profissional registrado.");
        }

        List<ServiceDTO> services = profile.getServices().stream().map(mapper::toDTO).toList();
        return services;
    }

    @Transactional
    public Page<ReserveDTO> getReservesByService(long serviceId, Pageable pageable) {
        User user = auth.getCurrentAuthenticatedUser();

        if (user.getProfessionalProfile() == null) {
            throw new IllegalStateException("Usuário não possui perfil profissional registrado.");
        }

        Page<Reserve> reserves = rRepo.findAllByService_Id(serviceId, pageable);

        return reserves.map(mapper::toDTO);
    }
}
