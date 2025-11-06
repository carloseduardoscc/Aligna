package br.com.carlos.projeto.api;

import br.com.carlos.projeto.application.MeService;
import br.com.carlos.projeto.application.command.RegisterProfessionalProfileCommand;
import br.com.carlos.projeto.application.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/me")
@Tag(name = "Ações do usuário autenticado")
public class MeController {

    MeService meService;

    public MeController(MeService meService) {
        this.meService = meService;
    }


    @Operation(summary = "Traz informações sobre o usuário autenticado")
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> me() {
        UserDTO user = meService.me();
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Registra o perfil profissional do usuário autenticado")
    @PostMapping("/professional-profile")
    public ResponseEntity<ProfessionalProfileDTO> registerProfessionalProfile(@RequestBody @Valid RegisterProfessionalProfileCommand cmd){
        ProfessionalProfileDTO response = meService.registerProfessionalProfile(cmd);
        return ResponseEntity.created(null).body(response);
    }
}
