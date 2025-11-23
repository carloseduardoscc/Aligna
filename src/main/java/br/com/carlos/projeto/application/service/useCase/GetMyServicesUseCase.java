package br.com.carlos.projeto.application.service.useCase;

import br.com.carlos.projeto.application.authentication.useCase.GetLoggedUserUseCase;
import br.com.carlos.projeto.application.service.dto.ServiceDTO;
import br.com.carlos.projeto.application.service.mapper.ServiceMapper;
import br.com.carlos.projeto.domain.ProfessionalProfile;
import br.com.carlos.projeto.domain.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GetMyServicesUseCase {
    GetLoggedUserUseCase auth;
    ServiceMapper mapper;

    @Transactional
    public List<ServiceDTO> execute() {
        User user = auth.execute();
        ProfessionalProfile profile = user.getProfessionalProfile();

        if (profile == null) {
            throw new IllegalStateException("Usuário não possui perfil profissional registrado.");
        }

        List<ServiceDTO> services = profile.getServices().stream().map(mapper::toDTO).toList();
        return services;
    }
}
