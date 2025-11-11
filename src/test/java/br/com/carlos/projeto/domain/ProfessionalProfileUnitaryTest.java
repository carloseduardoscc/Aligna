package br.com.carlos.projeto.domain;


import br.com.carlos.projeto.domain.exceptions.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class ProfessionalProfileUnitaryTest {

    ProfessionalProfile professionalProfile;

    @BeforeEach
    void setUp() throws Exception {
        professionalProfile = new ProfessionalProfile("Descrição profissional test");
    }

    @Test
    void deveDarErroAoRegistrarServicoComNomeDuplicado() {
        Service service1 = new Service("Marcenaria", "Faço serviços de marcenaria de todos os tipos.", LocalTime.of(8, 0), LocalTime.of(16, 0), new HashSet<>(List.of(DayOfWeek.WEDNESDAY)), professionalProfile);

        professionalProfile.addService(service1);

        Service service2 = new Service("Marcenaria", "Outro serviço de marcenaria.", LocalTime.of(8, 0), LocalTime.of(16, 0), new HashSet<>(List.of(DayOfWeek.WEDNESDAY)), professionalProfile);

        assertThrows(DomainException.class, () -> professionalProfile.addService(service2));
    }
}
