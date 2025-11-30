package br.com.carlos.projeto.domain;

import br.com.carlos.projeto.domain.exceptions.DomainException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Access(AccessType.FIELD)
@Entity(name = "reserve_tb")
public class Reserve {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    LocalDateTime scheduledTo;
    @Enumerated(EnumType.STRING)
    CancellationSource cancellationSource;
    /// BELONGS TO
    @ManyToOne
    @JoinColumn(name = "applicant_id")
    User applicant;
    @ManyToOne
    @JoinColumn(name = "service_id")
    Service service;
    /// HAS
    @OneToMany(mappedBy = "reserve", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReservationStatusEntry> statusEntries = new ArrayList<>();

    protected Reserve() {
    }

    public Reserve(LocalDateTime scheduledTo, User applicant, Service service) {
        this.statusEntries.add(new ReservationStatusEntry(ReserveStatus.PENDING, this));
        setService(service);
        setScheduledTo(scheduledTo);
        setApplicant(applicant);
    }

    public void accept() {
        addStatus(ReserveStatus.ACCEPTED);
    }

    public void reject() {
        addStatus(ReserveStatus.REJECTED);
    }

    public void cancel(CancellationSource source) {
        if (source == null) {
            throw new DomainException("fonte de cancelamento não deve ser nula");
        }
        if (this.getLastStatus() == ReserveStatus.CANCELLED) {
            throw new DomainException("solicitação de agendamento já está cancelada");
        }
        if (source.equals(CancellationSource.PROFESSIONAL) && this.getLastStatus() == ReserveStatus.PENDING) {
            throw new DomainException("profissional não pode cancelar uma solicitação de agendamento que ainda não foi aceita ou rejeitada");
        }
        setCancellationSource(source);
        addStatus(ReserveStatus.CANCELLED);
    }

    private void addStatus(ReserveStatus status) {
        if (status == null) {
            throw new DomainException("status da solicitação de agendamento não deve ser nulo");
        }
        if (!this.getLastStatus().possibleTransitions().contains(status)) {
            throw new DomainException("Transição de status da solicitação de agendamento inválida de " + this.getLastStatus() + " para " + status);
        }
        this.statusEntries.add(new ReservationStatusEntry(status, this));
    }

    public ReserveStatus getLastStatus() {
        if (statusEntries.isEmpty()) {
            return null;
        }
        return statusEntries.get(statusEntries.size() - 1).getStatus();
    }

    public LocalDateTime getLastUpdate() {
        if (statusEntries.isEmpty()) {
            return null;
        }
        return statusEntries.get(statusEntries.size() - 1).getTimeStamp();
    }

    public CancellationSource getCancellationSource() {
        return cancellationSource;
    }

    public void setCancellationSource(CancellationSource cancellationSource) {
        this.cancellationSource = cancellationSource;
    }

    public LocalDateTime getScheduledTo() {
        return scheduledTo;
    }

    public void setScheduledTo(LocalDateTime scheduledAt) {
        if (scheduledAt == null) {
            throw new DomainException("horário de agendamento não deve ser nulo");
        }
        if (scheduledAt.isBefore(LocalDateTime.now())) {
            throw new DomainException("horário de agendamento não deve ser no passado");
        }
        if (scheduledAt.toLocalTime().isBefore(service.getAvailableFrom()) || scheduledAt.toLocalTime().isAfter(service.getAvailableUntil())) {
            throw new DomainException("horário de agendamento deve estar dentro do horário de atendimento do serviço: " + service.getAvailableFrom() + " até ás " + service.getAvailableUntil());
        }
        if (!service.getAvailableDays().contains(scheduledAt.getDayOfWeek())) {
            throw new DomainException("horário de agendamento deve estar dentro dos dias disponíveis do serviço: " + service.getAvailableDays());
        }
        this.scheduledTo = scheduledAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        if (applicant == null) {
            throw new DomainException("solicitante não deve ser nulo");
        }
        if (this.service.getProfessionalProfile().getUser().equals(applicant)) {
            throw new DomainException("O solicitante não pode ser o mesmo que o prestador de serviço.");
        }
        if (this.service.getReserves().stream().anyMatch(
                r -> r.getApplicant().equals(applicant) &&
                        List.of(ReserveStatus.PENDING, ReserveStatus.ACCEPTED).contains(r.getLastStatus()) &&
                        r.getScheduledTo().isAfter(LocalDateTime.now()))) {
            throw new DomainException("O solicitante já possui um agendamento para este serviço. Para realizar um novo agendamento, cancele o existente ou espere que a data agendada se cumpra.");
        }
        this.applicant = applicant;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        if (service == null) {
            throw new DomainException("serviço não deve ser nulo");
        }
        this.service = service;
    }

    public List<ReservationStatusEntry> getStatusEntries() {
        return statusEntries;
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
