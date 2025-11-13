package br.com.carlos.projeto.infra.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "user_tb")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    public Long id;

    public String name;
    public String email;
    public String password;

    // External
    @OneToOne(mappedBy = "user", cascade =  CascadeType.ALL)
    public ProfessionalProfileEntity professionalProfile;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    public Set<ReserveEntity> reserves;

}
