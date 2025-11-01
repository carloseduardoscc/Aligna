package br.com.carlos.projeto.infra.persistence.jpa;

import br.com.carlos.projeto.domain.repository.UserRepository;
import br.com.carlos.projeto.infra.persistence.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaRepositoryImp extends JpaRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}

@Repository
public class UserJpaImpRepository implements UserRepository<UserEntity> {

    @Qualifier("jpaRepositoryImp")
    @Autowired
    JpaRepositoryImp repo;

    public UserEntity save (UserEntity object){
        return (UserEntity) repo.save(object);
    }

    public Iterable<UserEntity> saveAll(Iterable<UserEntity> objects){
        return (Iterable<UserEntity>) repo.saveAll(objects);
    }

    public UserEntity findById(Long id){
        return (UserEntity) repo.findById(id).get();
    }

    public Iterable<UserEntity> findAll(){
        return (Iterable<UserEntity>) repo.findAll();
    }

    public void deleteById(Long id){
        repo.deleteById(id);
    }

    @Override
    public UserEntity findByEmail(String email) {
        return repo.findByEmail(email);
    }

}
