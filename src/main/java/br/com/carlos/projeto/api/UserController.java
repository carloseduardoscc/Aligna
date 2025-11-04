package br.com.carlos.projeto.api;

import br.com.carlos.projeto.application.UserService;
import br.com.carlos.projeto.application.command.RegisterProfessionalProfileCommand;
import br.com.carlos.projeto.application.dto.ProfessionalProfileDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
@Tag(name = "Ações de usuário")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Registra o perfil profissional do usuário autenticado")
    @PostMapping("/professional-profile")
    public ResponseEntity<ProfessionalProfileDTO> registerProfessionalProfile(@RequestBody @Valid RegisterProfessionalProfileCommand cmd){
        ProfessionalProfileDTO response = userService.registerProfessionalProfile(cmd);
        return ResponseEntity.ok(response);
    }
}
