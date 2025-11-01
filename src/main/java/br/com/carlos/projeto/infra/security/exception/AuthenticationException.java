package br.com.carlos.projeto.infra.security.exception;

public class AuthenticationException extends RuntimeException{
    public AuthenticationException(String msg){
        super(msg);
    }
}
