package br.com.carlos.projeto.domain;

import br.com.carlos.projeto.domain.exceptions.DomainException;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class ProfessionalProfile {
    Long id;
    String description;

    // External
    User user;
    Set<Service> services = new HashSet<>();

    public ProfessionalProfile(String description) {
        setDescription(description);
    }

    private void validateNotBlank(String name) {
        if (name == null || name.isBlank()){
            throw new DomainException("Nome não deve ser nulo ou estar em branco");
        }
    }

    public void addService(Service service) {
        services.add(service);
    }

    public void addServices(Set<Service> services) {
        this.services.addAll(services);
    }

    public void removeService(Service service) {
        if (services != null) {
            services.remove(service);
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

    public Set<Service> getServices() {
        return new HashSet<Service>(services);
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ProfessionalProfile that = (ProfessionalProfile) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
