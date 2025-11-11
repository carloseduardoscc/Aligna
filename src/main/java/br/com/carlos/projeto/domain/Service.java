package br.com.carlos.projeto.domain;

import br.com.carlos.projeto.domain.exceptions.DomainException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Service {
    Long id;

    String title;
    String description;
    LocalTime availableFrom;
    LocalTime availableUntil;
    Set<DayOfWeek> availableDays;

    // External
    ProfessionalProfile professionalProfile;

    public Service(String title, String description, LocalTime availableFrom, LocalTime availableUntil, Set<DayOfWeek> availableDays, ProfessionalProfile professionalProfile) {
        setTitle(title);
        setDescription(description);
        setAvailableFrom(availableFrom);
        setAvailableUntil(availableUntil);
        setAvailableDays(availableDays);
        this.professionalProfile = professionalProfile;
    }

    private void validateNotBlank(String name) {
        if (name == null || name.isBlank()){
            throw new DomainException("Nome não deve ser nulo ou estar em branco");
        }
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        validateNotBlank(title);
        this.title = title;
    }

    public void setDescription(String description) {
        validateNotBlank(description);
        this.description = description;
    }

    public void setAvailableFrom(LocalTime availableFrom) {
        if (availableUntil != null && availableFrom.isAfter(availableUntil)) {
            throw new DomainException("Horário de disponibilidade inicial não pode ser após o horário final");
        }
        this.availableFrom = availableFrom;
    }

    public void setAvailableUntil(LocalTime availableUntil) {
        if (availableFrom != null && availableUntil.isBefore(availableFrom)) {
            throw new DomainException("Horário de disponibilidade final não pode ser antes do horário inicial");
        }
        this.availableUntil = availableUntil;
    }

    public void setAvailableDays(Set<DayOfWeek> availableDays) {
        if (availableDays == null || availableDays.isEmpty()) {
            throw new DomainException("Dias disponíveis devem conter ao menos um dia da semana");
        }
        this.availableDays = availableDays;
    }

    public void setProfessionalProfile(ProfessionalProfile professionalProfile) {
        this.professionalProfile = professionalProfile;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public LocalTime getAvailableFrom() {
        return availableFrom;
    }

    public LocalTime getAvailableUntil() {
        return availableUntil;
    }

    public Set<DayOfWeek> getAvailableDays() {
        return new HashSet<DayOfWeek>(availableDays);
    }

    public ProfessionalProfile getProfessionalProfile() {
        return professionalProfile;
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
