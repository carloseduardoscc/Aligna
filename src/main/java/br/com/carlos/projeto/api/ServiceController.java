package br.com.carlos.projeto.api;

import br.com.carlos.projeto.application.ServiceService;
import br.com.carlos.projeto.application.dto.ServiceDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
public class ServiceController {

    ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @Operation(summary = "Busca um usuário pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ServiceDTO> findById(@PathVariable Long id){
        ServiceDTO response = serviceService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Busca todos os serviços com paginação")
    @GetMapping
    public ResponseEntity<Page<ServiceDTO>> findAll(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        Page<ServiceDTO> response = serviceService.findAll(pageable);
        return ResponseEntity.ok(response);
    }
}
