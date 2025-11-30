package br.com.carlos.projeto.domain;

import br.com.carlos.projeto.domain.exceptions.DomainException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ReserveUnitaryTest {
    User applicant;
    User professional;

    Service service1;
    Service service2;

    @BeforeEach
    public void setUp() {
        applicant = new User("Carlos Eduardo", "carlos@gmail.com", "123456789");
        applicant.setId(0l);
        professional = new User("Paulo Roberto", "paulo@gmail.com", "123456789");
        professional.setId(1l);

        ProfessionalProfile pf = new ProfessionalProfile("Profissional que trabalha com serviços gerais.");
        professional.setProfessionalProfile(pf);
        pf.setUser(professional);

        service1 = new Service("Marcenaria", "Faço serviços de marcenaria de todos os tipos.", java.time.LocalTime.of(8, 0), java.time.LocalTime.of(16, 0), java.util.Set.of(java.time.DayOfWeek.MONDAY, java.time.DayOfWeek.TUESDAY, java.time.DayOfWeek.FRIDAY), pf);
        service2 = new Service("Eletricista", "Faço serviços de elétrica em geral.", LocalTime.of(10, 0), LocalTime.of(14, 0), java.util.Set.of(DayOfWeek.WEDNESDAY), pf);

        professional.getProfessionalProfile().addService(service1);
        professional.getProfessionalProfile().addService(service2);
    }

    @Test
    void deveCriarReservaSemErro() {
        assertDoesNotThrow(() -> {
            new Reserve(java.time.LocalDateTime.now().plusWeeks(1).with(DayOfWeek.MONDAY).withHour(9).withMinute(0), applicant, service1);
        });
    }

    @Test
    void deveLancarErroAoCriarReservaComHorarioForaDoAtendimento() {
        assertThrows(DomainException.class, () -> {
            new Reserve(java.time.LocalDateTime.now().plusWeeks(1).with(DayOfWeek.MONDAY).withHour(7).withMinute(0), applicant, service1);
        });
    }

    @Test
    void deveLancarErroAoCriarReservaComHorarioNoPassado() {
        assertThrows(DomainException.class, () -> {
            new Reserve(java.time.LocalDateTime.now().minusDays(1), applicant, service1);
        });
    }

    @Test
    void deveLancarErroAoCriarReservaComHorarioEmDiaNaoAtendido() {
        assertThrows(DomainException.class, () -> {
            new Reserve(java.time.LocalDateTime.now().plusWeeks(1).with(DayOfWeek.SUNDAY).withHour(9).withMinute(0), applicant, service1);
        });
    }

    @Test
    void deveLancarErroAoCriarReservaComHorarioNulo() {
        assertThrows(DomainException.class, () -> {
            new Reserve(null, applicant, service1);
        });
    }

    @Test
    void deveLancarErroAoTentarAgendarMaisUmaReserva(){
        Reserve reservaExistente = new Reserve(java.time.LocalDateTime.now().plusWeeks(1).with(DayOfWeek.MONDAY).withHour(9).withMinute(0), applicant, service1);
        service1.addReserve(reservaExistente);

        assertThrows(DomainException.class, () -> {
            Reserve novaReserva = new Reserve(java.time.LocalDateTime.now().plusWeeks(1).with(DayOfWeek.MONDAY).withHour(10).withMinute(0), applicant, service1);
        });
    }

    @Test
    void deveLancarErroAoCriarReservaComApplicantEProfissionalIguais() {
        assertThrows(DomainException.class, () -> {
            new Reserve(java.time.LocalDateTime.now().plusWeeks(1).with(DayOfWeek.MONDAY).withHour(9).withMinute(0), professional, service1);
        });
    }

    @ParameterizedTest
    @CsvFileSource(resources = "/reserve-status-transitions.csv", numLinesToSkip = 1)
    void shouldValidateStatusTransitions(String initialState, String newState, boolean expectedValid){
        Reserve reserve = new Reserve(java.time.LocalDateTime.now().plusWeeks(1).with(DayOfWeek.MONDAY).withHour(9).withMinute(0), applicant, service1);

        applyStatusTransition(reserve, initialState);

        if(expectedValid){
            assertDoesNotThrow(()->{
                applyStatusTransition(reserve, newState);
            });
        } else {
            assertThrows(DomainException.class, ()->{
                applyStatusTransition(reserve, newState);
            });
        }
    }

    void applyStatusTransition(Reserve reserve, String newState) {
        switch(newState){
            case "ACCEPTED":
                reserve.accept();
                break;
            case "CANCELED":
                reserve.cancel(CancellationSource.APPLICANT);
                break;
            case "REJECTED":
                reserve.reject();
                break;
        }
    }
}
