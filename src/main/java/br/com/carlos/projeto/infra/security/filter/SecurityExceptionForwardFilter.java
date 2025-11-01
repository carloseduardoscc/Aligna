package br.com.carlos.projeto.infra.security.filter;

import br.com.carlos.projeto.infra.security.exception.AuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/// Pré-filtro que captura exceções lançadas pelo security e redireciona ao {@link br.com.carlos.projeto.api.SecurityErrorHandlerController}
@Component
public class SecurityExceptionForwardFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response); // deixa o Security rodar normalmente
        } catch (Exception ex) {
//             aqui você pega qualquer exceção que veio dos filtros
            request.setAttribute("javax.servlet.error.exception", ex); // opcional
            request.getRequestDispatcher("/security-error-handler").forward(request, response); // envia pro controller
        }
    }
}

