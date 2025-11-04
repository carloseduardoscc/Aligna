package br.com.carlos.projeto.domain;

import br.com.carlos.projeto.domain.exceptions.DomainException;

public class ProfessionalProfile {
    Long id;
    String description;

    // External
    User user;

    public ProfessionalProfile(String description) {
        setDescription(description);
    }

    private void validateNotBlank(String name) {
        if (name == null || name.isBlank()){
            throw new DomainException("Nome não deve ser nulo ou estar em branco");
        }
    }

    public void setDescription(String description) {
        validateNotBlank(description);
        this.description = description;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
