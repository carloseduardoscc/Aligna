package br.com.carlos.projeto.domain.repository;

public interface UserRepository<T> {
    public T save(T object);

    public Iterable<T> saveAll(Iterable<T> objects);

    public T findById(Long id);

    public Iterable<T> findAll();

    public void deleteById(Long id);

    T findByEmail(String email);
}
