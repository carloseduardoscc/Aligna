package br.com.carlos.projeto.api;

import br.com.carlos.projeto.application.UserService;
import br.com.carlos.projeto.application.command.RegisterProfessionalProfileCommand;
import br.com.carlos.projeto.application.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.dto.UserDTO;
import br.com.carlos.projeto.domain.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
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
        return ResponseEntity.created(null).body(response);
    }

    @Operation(summary = "Busca um usuário pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO response = userService.findById(id);
        return ResponseEntity.ok(response);
    }
}
