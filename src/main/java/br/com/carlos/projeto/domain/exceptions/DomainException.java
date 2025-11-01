package br.com.carlos.projeto.domain.exceptions;

public class DomainException extends RuntimeException {
    public  DomainException(String message) {
        super(message);
    }
}
