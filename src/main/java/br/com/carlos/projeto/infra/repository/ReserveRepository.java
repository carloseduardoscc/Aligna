package br.com.carlos.projeto.infra.repository;

import br.com.carlos.projeto.domain.Reserve;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ReserveRepository extends JpaRepository<Reserve, Long> {
    public Page<Reserve> findAll(Pageable pageable);

    Page<Reserve> findAllByService_Id(Long serviceId, Pageable pageable);
    Page<Reserve> findAllByApplicant_Id(Long applicantId, Pageable pageable);
}
