package br.com.carlos.projeto.infra.persistence.jpa;

import br.com.carlos.projeto.domain.repository.ServiceRepository;
import br.com.carlos.projeto.infra.persistence.entity.ServiceEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
interface ServiceJpaRepositoryImp extends JpaRepository<ServiceEntity, Long> {
    public Page<ServiceEntity> findAll(Pageable pageable);
}

@Repository
public class ServiceJpaImpRepository implements ServiceRepository<ServiceEntity> {

    @Qualifier("serviceJpaRepositoryImp")
    @Autowired
    ServiceJpaRepositoryImp repo;

    public ServiceEntity save(ServiceEntity object) {
        return (ServiceEntity) repo.save(object);
    }

    public Iterable<ServiceEntity> saveAll(Iterable<ServiceEntity> objects) {
        return (Iterable<ServiceEntity>) repo.saveAll(objects);
    }

    public ServiceEntity findById(Long id) {
        return (ServiceEntity) repo.findById(id).get();
    }

    public Iterable<ServiceEntity> findAll() {
        return (Iterable<ServiceEntity>) repo.findAll();
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public Page<ServiceEntity> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
