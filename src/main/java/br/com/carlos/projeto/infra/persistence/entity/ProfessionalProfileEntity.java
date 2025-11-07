package br.com.carlos.projeto.infra.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "ProfessionalProfileEntity_tb")
public class ProfessionalProfileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    public Long id;

    public String description;

    // External
    @OneToOne
    @JoinColumn(name = "user_id")
    public UserEntity user;
    @OneToMany(mappedBy = "professionalProfile", cascade = CascadeType.ALL)
    public Set<ServiceEntity> services;
}
