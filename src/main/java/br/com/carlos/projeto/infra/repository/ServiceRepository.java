package br.com.carlos.projeto.infra.repository;

import br.com.carlos.projeto.domain.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ServiceRepository extends JpaRepository<Service, Long> {

    public Page<Service> findAll(Pageable pageable);
}