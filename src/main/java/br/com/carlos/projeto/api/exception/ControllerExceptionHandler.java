package br.com.carlos.projeto.api.exception;

import br.com.carlos.projeto.domain.exceptions.DomainException;
import br.com.carlos.projeto.infra.security.exception.AuthenticationException;
import br.com.carlos.projeto.infra.security.exception.AuthorizationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;

/**
 * Manipulador global de exceções da API.
 * Responsável por capturar e tratar exceções comuns, retornando respostas padronizadas.
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * BAD REQUEST - 400 <br>
     * Requisição inválida ou violação de regra de negócio.
     */
    @ExceptionHandler(DomainException.class)
    public ResponseEntity<StandardError> handleDomainException(DomainException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err = new StandardError(
                status.value(),
                "Regra de negócio violada",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

    /**
     * UNAUTHORIZED - 401 <br>
     * O usuário não está autenticado ou enviou credenciais inválidas.
     */
    @ExceptionHandler({BadCredentialsException.class, UsernameNotFoundException.class, AuthenticationException.class})
    public ResponseEntity<StandardError> handleAuthenticationException(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        StandardError err = new StandardError(
                status.value(),
                "Autenticação necessária",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

    /**
     * FORBIDDEN - 403 <br>
     * O usuário está autenticado, mas não tem permissão para acessar o recurso.
     */
    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<StandardError> handleAuthorizationException(AuthorizationException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        StandardError err = new StandardError(
                status.value(),
                "Acesso negado",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

    /**
     * NOT FOUND - 404 <br>
     * Recurso solicitado não foi encontrado.
     */
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<StandardError> handleNotFound(NoSuchElementException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err = new StandardError(
                status.value(),
                "Recurso não encontrado",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

    /**
     * CONFLICT - 409 <br>
     * Conflito de estado ou tentativa de operação inconsistente.
     */
    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<StandardError> handleConflict(IllegalStateException e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.CONFLICT;
        StandardError err = new StandardError(
                status.value(),
                "Conflito de estado",
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }

    /**
     * UNPROCESSABLE ENTITY - 422 <br>
     * Estrutura da requisição é válida, mas contém dados inválidos.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationError> handleValidation(MethodArgumentNotValidException e, HttpServletRequest request) {
        ValidationError err = new ValidationError();
        err.setStatus(HttpStatus.UNPROCESSABLE_ENTITY.value());
        err.setError("Erro de validação");
        err.setMessage("Um ou mais campos estão inválidos");
        err.setPath(request.getRequestURI());

        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            err.addError(fieldError.getField(), fieldError.getDefaultMessage());
        }

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(err);
    }

    /**
     * INTERNAL SERVER ERROR - 500 <br>
     * Erro interno não tratado no servidor.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<StandardError> handleInternalServerError(Exception e, HttpServletRequest request) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
        StandardError err = new StandardError(
                status.value(),
                "Erro interno no servidor - " + e.getClass().getSimpleName(),
                e.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(status).body(err);
    }
}
