package br.com.carlos.projeto.api;

import br.com.carlos.projeto.application.professional.command.RegisterProfessionalProfileCommand;
import br.com.carlos.projeto.application.professional.useCase.RegisterProfessionalProfileUseCase;
import br.com.carlos.projeto.application.reserve.useCase.GetReservesByServiceIdUseCase;
import br.com.carlos.projeto.application.service.command.RegisterServiceCommand;
import br.com.carlos.projeto.application.professional.dto.ProfessionalProfileDTO;
import br.com.carlos.projeto.application.reserve.dto.ReserveDTO;
import br.com.carlos.projeto.application.service.dto.ServiceDTO;
import br.com.carlos.projeto.application.service.useCase.GetMyServicesUseCase;
import br.com.carlos.projeto.application.service.useCase.RegisterServiceUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
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
@Tag(name = "Casos de uso do perfil profissional do usuário autenticado")
@AllArgsConstructor
public class MeProfessionalController {

    RegisterProfessionalProfileUseCase registerProfessionalProfileUseCase;
    RegisterServiceUseCase registerServiceUseCase;
    GetMyServicesUseCase getMyServicesUseCase;
    GetReservesByServiceIdUseCase getReservesByServiceIdUseCase;

    @Operation(summary = "Registra o perfil profissional do usuário autenticado",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping
    public ResponseEntity<ProfessionalProfileDTO> registerProfessionalProfile(@RequestBody @Valid RegisterProfessionalProfileCommand cmd) {
        ProfessionalProfileDTO response = registerProfessionalProfileUseCase.execute(cmd);
        return ResponseEntity.created(URI.create("/professional-profiles/" + response.id())).body(response);
    }

    @Operation(summary = "Registra um serviço para o usuário autenticado",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping("/services")
    public ResponseEntity<ServiceDTO> registerService(@RequestBody @Valid RegisterServiceCommand cmd) {
        ServiceDTO response = registerServiceUseCase.execute(cmd);
        return ResponseEntity.created(URI.create("/services/" + response.id())).body(response);
    }

    @Operation(summary = "Busca todos os serviços do perfil profissional do usuário autenticado",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/services")
    public ResponseEntity<List<ServiceDTO>> getServices() {
        List<ServiceDTO> services = getMyServicesUseCase.execute();
        return ResponseEntity.ok(services);
    }

    @Operation(summary = "Busca todas as reservas de um serviço específico do perfil profissional do usuário autenticado com paginação",
            security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping("/services/{id}/reserves")
    public ResponseEntity<Page<ReserveDTO>> getReserves(@PathVariable Long id, @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
    @ParameterObject
    @Parameter(description = "Parâmetros de paginação e ordenação. Exemplo: ?page=0&size=10&sort=title,asc")
    Pageable pageable) {
        Page<ReserveDTO> reserves = getReservesByServiceIdUseCase.execute(id, pageable);
        return ResponseEntity.ok(reserves);

    }
}
