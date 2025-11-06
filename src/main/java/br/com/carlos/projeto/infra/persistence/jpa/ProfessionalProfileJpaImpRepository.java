package br.com.carlos.projeto.infra.persistence.jpa;

import br.com.carlos.projeto.domain.repository.ProfessionalProfileRepository;
import br.com.carlos.projeto.domain.repository.UserRepository;
import br.com.carlos.projeto.infra.persistence.entity.ProfessionalProfileEntity;
import br.com.carlos.projeto.infra.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;


@Repository
interface ProfessionalProfileJpaRepositoryImp extends JpaRepository<ProfessionalProfileEntity, Long> {
    public Page<ProfessionalProfileEntity> findAll(Pageable pageable);
}

@Repository
public class ProfessionalProfileJpaImpRepository implements ProfessionalProfileRepository<ProfessionalProfileEntity> {

    @Qualifier("professionalProfileJpaRepositoryImp")
    @Autowired
    ProfessionalProfileJpaRepositoryImp repo;

    public ProfessionalProfileEntity save(ProfessionalProfileEntity object) {
        return (ProfessionalProfileEntity) repo.save(object);
    }

    public Iterable<ProfessionalProfileEntity> saveAll(Iterable<ProfessionalProfileEntity> objects) {
        return (Iterable<ProfessionalProfileEntity>) repo.saveAll(objects);
    }

    public ProfessionalProfileEntity findById(Long id) {
        return (ProfessionalProfileEntity) repo.findById(id).get();
    }

    public Iterable<ProfessionalProfileEntity> findAll() {
        return (Iterable<ProfessionalProfileEntity>) repo.findAll();
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Page<ProfessionalProfileEntity> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
