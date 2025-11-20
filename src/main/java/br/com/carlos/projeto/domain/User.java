package br.com.carlos.projeto.domain;

import br.com.carlos.projeto.domain.exceptions.DomainException;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "user_tb")
public class User {
    //External
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    ProfessionalProfile professionalProfile;
    @OneToMany(mappedBy = "applicant", cascade = CascadeType.ALL)
    Set<Reserve> reserves = new HashSet<>();
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    private String name;
    private String email;
    private String password;

    public User() {
    }

    public User(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
    }

    private void validateNotBlank(String name) {
        if (name == null || name.isBlank()) {
            throw new DomainException("Nome não deve ser nulo ou estar em branco");
        }
    }

    public void addReserve(Reserve reserve) {
        if (reserve == null) {
            throw new DomainException("Reserva não deve ser nula");
        }
        this.reserves.add(reserve);
    }

    public Set<Reserve> getReserves() {
        return new HashSet<Reserve>(reserves);
    }

    public ProfessionalProfile getProfessionalProfile() {
        return professionalProfile;
    }

    public void setProfessionalProfile(ProfessionalProfile professionalProfile) {
        if (this.professionalProfile != null) {
            throw new DomainException("Este usuário já possui um perfil profissional cadastrado.");
        }
        this.professionalProfile = professionalProfile;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        validateNotBlank(name);
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        validateNotBlank(email);
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
