package br.com.carlos.projeto.api;

import br.com.carlos.projeto.application.MeProfessionalService;
import br.com.carlos.projeto.application.command.RegisterProfessionalProfileCommand;
import br.com.carlos.projeto.application.command.RegisterServiceCommand;
import br.com.carlos.projeto.application.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.dto.ReserveDTO;
import br.com.carlos.projeto.application.dto.ServiceDTO;
import br.com.carlos.projeto.domain.Reserve;
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
import java.util.List;

@RestController
@RequestMapping("/me/professional-profile")
@Tag(name = "Ações do usuário autenticado")
public class MeProfessionalController {

    MeProfessionalService meService;

    public MeProfessionalController(MeProfessionalService meService) {
        this.meService = meService;
    }

    @Operation(summary = "Registra o perfil profissional do usuário autenticado",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<ProfessionalProfileDTO> registerProfessionalProfile(@RequestBody @Valid RegisterProfessionalProfileCommand cmd) {
        ProfessionalProfileDTO response = meService.registerProfessionalProfile(cmd);
        return ResponseEntity.created(URI.create("/professional-profiles/" + response.id())).body(response);
    }


    @Operation(summary = "Registra um serviço para o usuário autenticado",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/services")
    public ResponseEntity<ServiceDTO> registerService(@RequestBody @Valid RegisterServiceCommand cmd) {
        ServiceDTO response = meService.registerService(cmd);
        return ResponseEntity.created(URI.create("/services/" + response.id())).body(response);
    }

    @GetMapping("/services")
    public ResponseEntity<List<ServiceDTO>> getServices() {
        List<ServiceDTO> services = meService.getServices();
        return ResponseEntity.ok(services);
    }

    @GetMapping("/services/{id}/reserves")
    public ResponseEntity<Page<ReserveDTO>> getReserves(@PathVariable Long id, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
    @ParameterObject
    @Parameter(description = "Parâmetros de paginação e ordenação. Exemplo: ?page=0&size=10&sort=title,asc")
    Pageable pageable) {
        Page<ReserveDTO> reserves = meService.getReservesByService(id, pageable);
        return ResponseEntity.ok(reserves);

    }
}
