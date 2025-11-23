package br.com.carlos.projeto.application.professional.useCase;

import br.com.carlos.projeto.application.authentication.useCase.GetLoggedUserUseCase;
import br.com.carlos.projeto.application.professional.command.RegisterProfessionalProfileCommand;
import br.com.carlos.projeto.application.professional.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.professional.mapper.ProfessionalMapper;
import br.com.carlos.projeto.domain.ProfessionalProfile;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RegisterProfessionalProfileUseCase {
    GetLoggedUserUseCase auth;
    UserRepository uRepo;
    ProfessionalMapper mapper;
    @Transactional
    public ProfessionalProfileDTO execute(RegisterProfessionalProfileCommand cmd) {
        ProfessionalProfile profile = new ProfessionalProfile(cmd.description());
        User user = auth.execute();
        user.setProfessionalProfile(profile);
        profile.setUser(user);

        user = uRepo.save(user);

        return mapper.toDTO(user.getProfessionalProfile());
    }
}
