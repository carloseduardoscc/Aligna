package br.com.carlos.projeto.domain;

import br.com.carlos.projeto.domain.exceptions.DomainException;

public class User {
    public Long id;
    public String name;
    public String email;
    public String password;

    public User(Long id, String name, String email, String password) {
        setId(id);
        setName(name);
        setEmail(email);
        setPassword(password);
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
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
