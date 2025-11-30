package br.com.carlos.projeto.application.service.useCase;

import br.com.carlos.projeto.application.authentication.useCase.GetLoggedUserUseCase;
import br.com.carlos.projeto.application.service.command.RegisterServiceCommand;
import br.com.carlos.projeto.application.service.dto.ServiceDTO;
import br.com.carlos.projeto.application.service.mapper.ServiceMapper;
import br.com.carlos.projeto.domain.ProfessionalProfile;
import br.com.carlos.projeto.domain.Service;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.repository.ServiceRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import java.time.DayOfWeek;
import java.util.HashSet;
import java.util.stream.Collectors;

@AllArgsConstructor
@org.springframework.stereotype.Service
public class RegisterServiceUseCase {
    GetLoggedUserUseCase auth;
    ServiceRepository sRepo;
    ServiceMapper mapper;

    @Transactional
    public ServiceDTO execute(RegisterServiceCommand cmd) {
        User user = auth.execute();
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
