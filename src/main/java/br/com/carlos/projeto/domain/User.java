package br.com.carlos.projeto.domain;

import br.com.carlos.projeto.domain.exceptions.DomainException;

import java.util.Objects;

public class User {
    private Long id;
    private String name;
    private String email;
    private String password;

    //External
    ProfessionalProfile professionalProfile;

    public User(String name, String email, String password) {
        setName(name);
        setEmail(email);
        setPassword(password);
    }

    private void validateNotBlank(String name) {
        if (name == null || name.isBlank()){
            throw new DomainException("Nome não deve ser nulo ou estar em branco");
        }
    }

    public void setProfessionalProfile(ProfessionalProfile professionalProfile) {
        if (this.professionalProfile != null) {
            throw new DomainException("Este usuário já possui um perfil profissional cadastrado.");
        }
        this.professionalProfile = professionalProfile;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        validateNotBlank(name);
        this.name = name;
    }

    public void setEmail(String email) {
        validateNotBlank(email);
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ProfessionalProfile getProfessionalProfile() {
        return professionalProfile;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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
