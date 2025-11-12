package br.com.carlos.projeto.infra.persistence.entity;

import br.com.carlos.projeto.domain.ProfessionalProfile;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "service_tb")
public class ServiceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    public Long id;

    public String title;
    public String description;
    LocalTime availableFrom;
    LocalTime availableUntil;
    Set<DayOfWeek> availableDays;

    // External
    @ManyToOne
    @JoinColumn(name = "profile_id")
    ProfessionalProfileEntity professionalProfile;
}
