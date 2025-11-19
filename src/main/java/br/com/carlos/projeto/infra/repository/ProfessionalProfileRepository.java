package br.com.carlos.projeto.infra.repository;

import br.com.carlos.projeto.domain.ProfessionalProfile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfessionalProfileRepository extends JpaRepository<ProfessionalProfile, Long> {
    public Page<ProfessionalProfile> findAll(Pageable pageable);
}