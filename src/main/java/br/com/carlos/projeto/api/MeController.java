package br.com.carlos.projeto.api;

import br.com.carlos.projeto.application.MeService;
import br.com.carlos.projeto.application.command.RequestReserveCommand;
import br.com.carlos.projeto.application.dto.ReserveDTO;
import br.com.carlos.projeto.application.dto.UserDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/me")
@Tag(name = "Casos de uso do usuário autenticado")
public class MeController {

    MeService meService;

    public MeController(MeService meService) {
        this.meService = meService;
    }

    @Operation(summary = "Traz informações sobre o usuário autenticado",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/profile")
    public ResponseEntity<UserDTO> me() {
        UserDTO user = meService.me();
        return ResponseEntity.ok(user);
    }


    @Operation(summary = "Solicita uma reserva de um serviço para o usuário autenticado",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/reserves")
    public ResponseEntity<ReserveDTO> requestReserve(@RequestBody @Valid RequestReserveCommand cmd) {
        ReserveDTO response = meService.requestReserve(cmd);
        return ResponseEntity.created(URI.create("/reserves/" + response.id())).body(response);
    }

    @Operation(summary = "Busca todas as reservas do usuário autenticado com paginação",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/reserves")
    public ResponseEntity<Page<ReserveDTO>> getReserves(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
                                                              @ParameterObject
                                                              @Parameter(description = "Parâmetros de paginação e ordenação. Exemplo: ?page=0&size=10&sort=title,asc")
                                                              Pageable pageable){
        Page<ReserveDTO> response = meService.getMyReserves(pageable);
        return ResponseEntity.ok(response);
    }
}
