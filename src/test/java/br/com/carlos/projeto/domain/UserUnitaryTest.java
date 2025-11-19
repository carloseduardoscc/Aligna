package br.com.carlos.projeto.domain;

import br.com.carlos.projeto.domain.exceptions.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class UserUnitaryTest {
    User user;

    @BeforeEach
    void setUp() throws Exception {
        user = new User("Teste user", "test@email.com", "123456789");
    }

    @Test
    void deveRegistrarPrimeiroPerfilProfissionalSemErro() {
        ProfessionalProfile profile = new ProfessionalProfile("Descrição profissional test");

        assertDoesNotThrow(() -> {
            user.setProfessionalProfile(profile);
        });
    }

    @Test
    void deveDarErroAoRegistrarSegundoPerfilProfissional() {
        ProfessionalProfile profile = new ProfessionalProfile("Descrição profissional test");
        user.setProfessionalProfile(profile);

        assertThrows(DomainException.class, () -> {
            user.setProfessionalProfile(profile);
        });
    }

}
