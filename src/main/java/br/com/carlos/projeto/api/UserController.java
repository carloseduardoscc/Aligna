package br.com.carlos.projeto.api;

import br.com.carlos.projeto.application.UserService;
import br.com.carlos.projeto.application.command.RegisterProfessionalProfileCommand;
import br.com.carlos.projeto.application.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Ações sobre usuário")
public class UserController {

    UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Busca um usuário pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id){
        UserDTO response = userService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Busca todos os usuários com paginação")
    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<UserDTO> response = userService.findAll(pageable);
        return ResponseEntity.ok(response);
    }
}
