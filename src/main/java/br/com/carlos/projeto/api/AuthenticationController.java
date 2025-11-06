package br.com.carlos.projeto.api;

import br.com.carlos.projeto.application.AuthenticationService;
import br.com.carlos.projeto.application.command.LoginCommand;
import br.com.carlos.projeto.application.command.RegisterUserCommand;
import br.com.carlos.projeto.application.dto.LoginResponseDTO;
import br.com.carlos.projeto.application.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação")
public class AuthenticationController {

    AuthenticationService service;

    public AuthenticationController(AuthenticationService userService) {
        this.service = userService;
    }

    @Operation(summary = "Autentica um usuário e retorna um token JWT.")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid LoginCommand cmd) {
        LoginResponseDTO response = service.login(cmd);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Registra um novo usuário no sistema.")
    @PostMapping("/register")
    public ResponseEntity<UserDTO> registerUser(HttpServletRequest request, @RequestBody @Valid RegisterUserCommand cmd) {
        UserDTO dto = service.register(cmd);
        return ResponseEntity.created(URI.create("")).body(dto);
    }
}
