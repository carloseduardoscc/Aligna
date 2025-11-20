package br.com.carlos.projeto.domain;

import br.com.carlos.projeto.domain.exceptions.DomainException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity(name = "ProfessionalProfileEntity_tb")
public class ProfessionalProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    Long id;
    String description;

    // External
    @OneToOne
    @JoinColumn(name = "user_id")
    User user;
    @OneToMany(mappedBy = "professionalProfile", cascade = CascadeType.ALL)
    Set<Service> services = new HashSet<>();

    public ProfessionalProfile() {
    }

    public ProfessionalProfile(String description) {
        setDescription(description);
    }

    private void validateNotBlank(String name) {
        if (name == null || name.isBlank()) {
            throw new DomainException("Nome não deve ser nulo ou estar em branco");
        }
    }

    public void addService(Service service) {
        if (this.services.stream().anyMatch(x -> x.getTitle().equals(service.title))) {
            throw new DomainException("Serviço já cadastrado para este perfil profissional");
        }
        services.add(service);
    }

    public void addServices(Set<Service> services) {
        services.forEach(this::addService);
    }

    public void removeService(Service service) {
        if (services != null) {
            services.remove(service);
        }
    }

    public Set<Service> getServices() {
        return new HashSet<Service>(services);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        validateNotBlank(description);
        this.description = description;
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
