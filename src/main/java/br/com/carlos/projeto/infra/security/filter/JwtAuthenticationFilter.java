package br.com.carlos.projeto.infra.security.filter;

import br.com.carlos.projeto.application.authentication.useCase.LoadUserByUserNameUseCase;
import br.com.carlos.projeto.infra.security.AuthUser;
import br.com.carlos.projeto.infra.security.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.security.sasl.AuthenticationException;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    TokenService tokenService;
    LoadUserByUserNameUseCase loadUserByUserNameUseCase;

    public JwtAuthenticationFilter(TokenService tokenService, @Lazy LoadUserByUserNameUseCase authService) {
        this.tokenService = tokenService;
        this.loadUserByUserNameUseCase = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = this.recoverToken(request);
            if (token != null) {
                String email = tokenService.validateToken(token);
                AuthUser user = (AuthUser) loadUserByUserNameUseCase.execute(email);

                // Validação
                if (user == null) {
                    throw new AuthenticationException("O usuário autenticado não foi encontrado!");
                }

                Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, null);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);
        } catch (Exception ex) {
            request.setAttribute("javax.servlet.error.exception", ex); // opcional
            request.getRequestDispatcher("/security-error-handler").forward(request, response); // envia pro controller
        }
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }
}