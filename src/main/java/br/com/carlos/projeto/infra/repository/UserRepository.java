package br.com.carlos.projeto.infra.repository;

import br.com.carlos.projeto.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepository extends JpaRepository<User, Long> {
    public User findByEmail(String email);

    public Page<User> findAll(Pageable pageable);
}
