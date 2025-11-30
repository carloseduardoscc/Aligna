package br.com.carlos.projeto.api;

import br.com.carlos.projeto.application.service.dto.ServiceDTO;
import br.com.carlos.projeto.application.service.useCase.FindAllServiceUseCase;
import br.com.carlos.projeto.application.service.useCase.FindServiceByIdUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/services")
@Tag(name = "Ações sobre serviços")
@AllArgsConstructor
public class ServiceController {

    FindServiceByIdUseCase findServiceByIdUseCase;
    FindAllServiceUseCase findAllServiceUseCase;

    @Operation(summary = "Busca um usuário pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> findById(@PathVariable Long id) {
        ServiceDTO response = findServiceByIdUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Busca todos os serviços com paginação")
    @GetMapping
    public ResponseEntity<Page<ServiceDTO>> findAll(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
                                                    @ParameterObject
                                                    @Parameter(description = "Parâmetros de paginação e ordenação. Exemplo: ?page=0&size=10&sort=title,asc")
                                                    Pageable pageable) {
        Page<ServiceDTO> response = findAllServiceUseCase.execute(pageable);
        return ResponseEntity.ok(response);
    }
}
