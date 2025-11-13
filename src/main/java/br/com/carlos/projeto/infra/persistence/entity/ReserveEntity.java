package br.com.carlos.projeto.infra.persistence.entity;

import br.com.carlos.projeto.domain.ReserveStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity(name = "reserve_tb")
public class ReserveEntity {
    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public LocalDateTime dateTime;
    public ReserveStatus status;

    //External
    @ManyToOne
    @JoinColumn(name = "applicant_id")
    public UserEntity applicant;
    @ManyToOne
    @JoinColumn(name = "service_id")
    public ServiceEntity service;
}
