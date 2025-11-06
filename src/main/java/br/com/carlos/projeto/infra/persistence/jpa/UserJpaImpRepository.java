package br.com.carlos.projeto.infra.persistence.jpa;

import br.com.carlos.projeto.domain.repository.UserRepository;
import br.com.carlos.projeto.infra.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface UserJpaRepositoryImp extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);

    Page<UserEntity> findAll(Pageable pageable);
}

@Repository
public class UserJpaImpRepository implements UserRepository<UserEntity> {

    @Qualifier("userJpaRepositoryImp")
    @Autowired
    UserJpaRepositoryImp repo;

    public UserEntity save(UserEntity object) {
        return repo.save(object);
    }

    public Iterable<UserEntity> saveAll(Iterable<UserEntity> objects) {
        return repo.saveAll(objects);
    }

    public UserEntity findById(Long id) {
        return repo.findById(id).get();
    }

    public Iterable<UserEntity> findAll() {
        return repo.findAll();
    }

    public void deleteById(Long id) {
        repo.deleteById(id);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return repo.findByEmail(email);
    }

    @Override
    public Page<UserEntity> findAll(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
