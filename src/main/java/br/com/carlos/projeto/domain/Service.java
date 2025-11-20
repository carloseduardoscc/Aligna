package br.com.carlos.projeto.domain;

import br.com.carlos.projeto.domain.exceptions.DomainException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "service_tb")
public class Service {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Long id;

    String title;
    String description;
    LocalTime availableFrom;
    LocalTime availableUntil;
    Set<DayOfWeek> availableDays;

    // External
    @ManyToOne
    @JoinColumn(name = "profile_id")
    ProfessionalProfile professionalProfile;
    @OneToMany(mappedBy = "service", cascade = CascadeType.ALL)
    Set<Reserve> reserves = new HashSet<>();

    public Service() {
    }

    public Service(String title, String description, LocalTime availableFrom, LocalTime availableUntil, Set<DayOfWeek> availableDays, ProfessionalProfile professionalProfile) {
        setTitle(title);
        setDescription(description);
        setAvailableFrom(availableFrom);
        setAvailableUntil(availableUntil);
        setAvailableDays(availableDays);
        setProfessionalProfile(professionalProfile);
    }

    private void validateNotBlank(String name) {
        if (name == null || name.isBlank()) {
            throw new DomainException("Nome não deve ser nulo ou estar em branco");
        }
    }

    public void addReserve(Reserve reserve) {
        if (reserve == null) {
            throw new DomainException("Reserva não deve ser nula");
        }
        this.reserves.add(reserve);
    }

    public Set<Reserve> getReserves() {
        return new HashSet<Reserve>(reserves);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        validateNotBlank(title);
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        validateNotBlank(description);
        this.description = description;
    }

    public LocalTime getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(LocalTime availableFrom) {
        if (availableUntil != null && availableFrom.isAfter(availableUntil)) {
            throw new DomainException("Horário de disponibilidade inicial não pode ser após o horário final");
        }
        this.availableFrom = availableFrom;
    }

    public LocalTime getAvailableUntil() {
        return availableUntil;
    }

    public void setAvailableUntil(LocalTime availableUntil) {
        if (availableFrom != null && availableUntil.isBefore(availableFrom)) {
            throw new DomainException("Horário de disponibilidade final não pode ser antes do horário inicial");
        }
        this.availableUntil = availableUntil;
    }

    public Set<DayOfWeek> getAvailableDays() {
        return new HashSet<DayOfWeek>(availableDays);
    }

    public void setAvailableDays(Set<DayOfWeek> availableDays) {
        if (availableDays == null || availableDays.isEmpty()) {
            throw new DomainException("Dias disponíveis devem conter ao menos um dia da semana");
        }
        this.availableDays = availableDays;
    }

    public ProfessionalProfile getProfessionalProfile() {
        return professionalProfile;
    }

    public void setProfessionalProfile(ProfessionalProfile professionalProfile) {
        this.professionalProfile = professionalProfile;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(id, service.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
