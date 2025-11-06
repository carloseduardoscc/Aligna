package br.com.carlos.projeto.domain.repository;

import br.com.carlos.projeto.infra.persistence.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserRepository<T> {
    public T save(T object);

    public Iterable<T> saveAll(Iterable<T> objects);

    public T findById(Long id);

    public Iterable<T> findAll();

    public void deleteById(Long id);

    public T findByEmail(String email);

    public Page<UserEntity> findAll(Pageable pageable);
}
