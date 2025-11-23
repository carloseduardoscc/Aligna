package br.com.carlos.projeto.infra.config;

import br.com.carlos.projeto.application.authentication.command.RegisterUserCommand;
import br.com.carlos.projeto.application.authentication.dto.RegisterResponseDTO;
import br.com.carlos.projeto.application.authentication.useCase.LoadUserByUserNameUseCase;
import br.com.carlos.projeto.application.authentication.useCase.RegisterUserCase;
import br.com.carlos.projeto.application.professional.command.RegisterProfessionalProfileCommand;
import br.com.carlos.projeto.application.professional.useCase.RegisterProfessionalProfileUseCase;
import br.com.carlos.projeto.application.reserve.command.RequestReserveCommand;
import br.com.carlos.projeto.application.reserve.useCase.RequestReserveUseCase;
import br.com.carlos.projeto.application.service.command.RegisterServiceCommand;
import br.com.carlos.projeto.application.service.useCase.RegisterServiceUseCase;
import br.com.carlos.projeto.application.user.dto.UserDTO;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.repository.UserRepository;
import br.com.carlos.projeto.infra.security.AuthUser;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Set;

@Component
public class TestDataLoader implements CommandLineRunner {

    RegisterUserCase registerUserCase;
    UserRepository uRepo;
    RegisterProfessionalProfileUseCase registerProfessionalProfileUseCase;
    RegisterServiceUseCase registerServiceUseCase;
    RequestReserveUseCase requestReserveUseCase;
    LoadUserByUserNameUseCase loadUserByUserNameUseCase;

    public TestDataLoader(LoadUserByUserNameUseCase loadUserByUserNameUseCase, RequestReserveUseCase requestReserveUseCase, RegisterServiceUseCase registerServiceUseCase, RegisterProfessionalProfileUseCase registerProfessionalProfileUseCase, UserRepository uRepo, RegisterUserCase registerUserCase) {
        this.loadUserByUserNameUseCase = loadUserByUserNameUseCase;
        this.requestReserveUseCase = requestReserveUseCase;
        this.registerServiceUseCase = registerServiceUseCase;
        this.registerProfessionalProfileUseCase = registerProfessionalProfileUseCase;
        this.uRepo = uRepo;
        this.registerUserCase = registerUserCase;
    }

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    public void run(String... args) throws Exception {
        if(activeProfile.equals("homolog")) {
            loadTestData();
        }
    }

    private void loadTestData() {
        String password = "123456789";

        RegisterResponseDTO userADTO = registerUserCase.execute(new RegisterUserCommand("Carlos Eduardo", "carlos@gmail.com", password));
        RegisterResponseDTO userBDTO = registerUserCase.execute(new RegisterUserCommand("Paulo Roberto", "paulo@gmail.com", password));

        User userA = uRepo.findById(Long.parseLong(userADTO.id())).get();
        User userB = uRepo.findById(Long.parseLong(userBDTO.id())).get();

        login(userB,password);
        registerProfessionalProfileUseCase.execute(new RegisterProfessionalProfileCommand("Profissional que trabalha com serviços gerais."));

        registerServiceUseCase.execute(new RegisterServiceCommand("Marcenaria", "Faço serviços de marcenaria de todos os tipos.", LocalTime.of(8,0), LocalTime.of(16,0), Set.of("MONDAY", "TUESDAY", "FRIDAY")));
        registerServiceUseCase.execute(new RegisterServiceCommand("Eletricista", "Faço serviços de elétrica em geral.", LocalTime.of(10,0), LocalTime.of(14,0), Set.of("WEDNESDAY")));

        login(userA, password);

        requestReserveUseCase.execute(new RequestReserveCommand(1l, LocalDateTime.now().plusWeeks(1).with(DayOfWeek.MONDAY).withHour(9).withMinute(0)));
    }

    public void login(User user, String password){
        AuthUser authUser = (AuthUser) loadUserByUserNameUseCase.execute(user.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(authUser, password, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
