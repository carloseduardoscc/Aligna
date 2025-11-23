package br.com.carlos.projeto.api;

import br.com.carlos.projeto.application.authentication.command.LoginCommand;
import br.com.carlos.projeto.application.authentication.command.RegisterUserCommand;
import br.com.carlos.projeto.application.authentication.dto.LoginResponseDTO;
import br.com.carlos.projeto.application.authentication.dto.RegisterResponseDTO;
import br.com.carlos.projeto.application.authentication.useCase.LoginUseCase;
import br.com.carlos.projeto.application.authentication.useCase.RegisterUserCase;
import br.com.carlos.projeto.application.user.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação")
@AllArgsConstructor
public class AuthenticationController {

    LoginUseCase loginUseCase;
    RegisterUserCase registerUserCase;

    @Operation(summary = "Autentica um usuário e retorna um token JWT.")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginCommand cmd) {
        LoginResponseDTO response = loginUseCase.execute(cmd);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registra um novo usuário no sistema.")
    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDTO> registerUser(HttpServletRequest request, @RequestBody @Valid RegisterUserCommand cmd) {
        RegisterResponseDTO dto = registerUserCase.execute(cmd);
        return ResponseEntity.created(URI.create("/users/" + dto.id())).body(dto);
    }
}
