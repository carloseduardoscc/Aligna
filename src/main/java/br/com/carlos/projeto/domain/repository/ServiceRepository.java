package br.com.carlos.projeto.domain.repository;

import br.com.carlos.projeto.infra.persistence.entity.ProfessionalProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ServiceRepository<T> {
    public T save(T object);

    public Iterable<T> saveAll(Iterable<T> objects);

    public T findById(Long id);

    public void deleteById(Long id);

    public Page<T> findAll(Pageable pageable);
}