package br.com.carlos.projeto.domain;

import br.com.carlos.projeto.domain.exceptions.DomainException;

import java.time.LocalDateTime;
import java.util.Objects;

public class Reserve {
    Long id;
    LocalDateTime dateTime;
    ReserveStatus status;

    //External
    User applicant;
    Service service;

    public Reserve(LocalDateTime dateTime, User applicant, Service service) {
        setStatus(ReserveStatus.PENDING);
        setService(service);
        setDateTime(dateTime);
        setApplicant(applicant);
    }

    public void setDateTime(LocalDateTime dateTime) {
        if (dateTime == null) {
            throw new DomainException("horário de agendamento não deve ser nulo");
        }
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new DomainException("horário de agendamento não deve ser no passado");
        }
        if (dateTime.toLocalTime().isBefore(service.getAvailableFrom()) || dateTime.toLocalTime().isAfter(service.getAvailableUntil())) {
            throw new DomainException("horário de agendamento deve estar dentro do horário de atendimento do serviço: " + service.getAvailableFrom() + " até ás " + service.getAvailableUntil());
        }
        if (!service.getAvailableDays().contains(dateTime.getDayOfWeek())) {
            throw new DomainException("horário de agendamento deve estar dentro dos dias disponíveis do serviço: " + service.getAvailableDays());
        }
        this.dateTime = dateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatus(ReserveStatus status) {
        if (status == null) {
            throw new DomainException("status da solicitação de agendamento não deve ser nulo");
        }
        if (this.status == null || !this.status.possibleTransitions().contains(status)) {
            throw new DomainException("Transição de status da solicitação de agendamento inválida de " + this.status + " para " + status);
        }
        this.status = status;
    }

    public void setApplicant(User applicant) {
        if (applicant == null) {
            throw new DomainException("solicitante não deve ser nulo");
        }
        if (this.service.getProfessionalProfile().getUser().equals(applicant)) {
            throw new DomainException("O solicitante não pode ser o mesmo que o prestador de serviço.");
        }
        this.applicant = applicant;
    }

    public void setService(Service service) {
        if (service == null) {
            throw new DomainException("serviço não deve ser nulo");
        }
        this.service = service;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public ReserveStatus getStatus() {
        return status;
    }

    public User getApplicant() {
        return applicant;
    }

    public Service getService() {
        return service;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reserve reserve = (Reserve) o;
        return Objects.equals(id, reserve.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
