package br.com.carlos.projeto.infra.config;

import br.com.carlos.projeto.application.AuthenticationService;
import br.com.carlos.projeto.application.MeProfessionalService;
import br.com.carlos.projeto.application.MeService;
import br.com.carlos.projeto.application.command.*;
import br.com.carlos.projeto.application.dto.UserDTO;
import br.com.carlos.projeto.domain.User;
import br.com.carlos.projeto.infra.repository.UserRepository;
import br.com.carlos.projeto.infra.security.AuthUser;
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

    @Autowired
    AuthenticationService aService;

    @Autowired
    MeService meService;

    @Autowired
    MeProfessionalService pService;

    @Autowired
    UserRepository uRepo;

    @Value("${spring.profiles.active}")
    private String activeProfile;

    @Override
    public void run(String... args) throws Exception {
        if(activeProfile.equals("test")) {
            loadTestData();
        }
    }

    private void loadTestData() {
        String password = "123456789";

        UserDTO userADTO = aService.register(new RegisterUserCommand("Carlos Eduardo", "carlos@gmail.com", password));
        UserDTO userBDTO = aService.register(new RegisterUserCommand("Paulo Roberto", "paulo@gmail.com", password));

        User userA = uRepo.findById(Long.parseLong(userADTO.id())).get();
        User userB = uRepo.findById(Long.parseLong(userBDTO.id())).get();

        login(userB,password);
        pService.registerProfessionalProfile(new RegisterProfessionalProfileCommand("Profissional que trabalha com serviços gerais."));

        pService.registerService(new RegisterServiceCommand("Marcenaria", "Faço serviços de marcenaria de todos os tipos.", LocalTime.of(8,0), LocalTime.of(16,0), Set.of("MONDAY", "TUESDAY", "FRIDAY")));
        pService.registerService(new RegisterServiceCommand("Eletricista", "Faço serviços de elétrica em geral.", LocalTime.of(10,0), LocalTime.of(14,0), Set.of("WEDNESDAY")));

        login(userA, password);

        meService.requestReserve(new RequestReserveCommand(1l, LocalDateTime.now().plusWeeks(1).with(DayOfWeek.MONDAY).withHour(9).withMinute(0)));
    }

    public void login(User user, String password){
        AuthUser authUser = (AuthUser) aService.loadUserByUsername(user.getEmail());

        Authentication authentication = new UsernamePasswordAuthenticationToken(authUser, password, null);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
