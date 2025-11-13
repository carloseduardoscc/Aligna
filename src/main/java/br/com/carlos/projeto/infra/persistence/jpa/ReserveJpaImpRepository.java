package br.com.carlos.projeto.infra.persistence.jpa;

import br.com.carlos.projeto.domain.repository.ReserveRepository;
import br.com.carlos.projeto.infra.persistence.entity.ReserveEntity;
import br.com.carlos.projeto.infra.persistence.entity.ReserveEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
interface ReserveJpaRepositoryImp extends JpaRepository<ReserveEntity, Long> {
}

@Repository
public class ReserveJpaImpRepository implements ReserveRepository<ReserveEntity> {

    @Qualifier("reserveJpaRepositoryImp")
    @Autowired
    ReserveJpaRepositoryImp repo;

    public ReserveEntity save(ReserveEntity object) {
        return (ReserveEntity) repo.save(object);
    }

    public Iterable<ReserveEntity> saveAll(Iterable<ReserveEntity> objects) {
        return (Iterable<ReserveEntity>) repo.saveAll(objects);
    }

    public ReserveEntity findById(Long id) {
        return (ReserveEntity) repo.findById(id).get();
    }

    public Iterable<ReserveEntity> findAll() {
        return (Iterable<ReserveEntity>) repo.findAll();
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Page<ReserveEntity> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
