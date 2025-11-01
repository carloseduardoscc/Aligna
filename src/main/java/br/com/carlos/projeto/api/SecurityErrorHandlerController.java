package br.com.carlos.projeto.api;

import br.com.carlos.projeto.infra.security.exception.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/// Adaptação feita para receber exceções do spring security e tratar no Controller advice com um retorno detalhado do erro ao usuário
@RestController
public class SecurityErrorHandlerController {

    @RequestMapping("/security-error-handler")
    public void handleError(HttpServletRequest request) {
        Throwable exception = (Throwable) request.getAttribute("javax.servlet.error.exception");
        if (exception != null) throw new AuthenticationException("Usuário ou senha inválidos"); // vai cair no @ControllerAdvice
    }
}

