package br.com.carlos.projeto.api;

import br.com.carlos.projeto.application.MeService;
import br.com.carlos.projeto.application.ProfessionalProfileService;
import br.com.carlos.projeto.application.dto.ProfessionalProfileDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/professional-profiles")
@Tag(name = "Ações sobre perfis profissionais")
public class ProfessionalProfileController {

    MeService meService;
    ProfessionalProfileService ppService;

    public ProfessionalProfileController(MeService meService, ProfessionalProfileService ppService) {
        this.meService = meService;
        this.ppService = ppService;
    }

    @Operation(summary = "Busca um professional profile pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProfessionalProfileDTO> findById(@PathVariable Long id) {
        ProfessionalProfileDTO response = ppService.findById(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Busca todos os perfis profissionais com paginação")
    @GetMapping
    public ResponseEntity<Page<ProfessionalProfileDTO>> findAll(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
                                                                @ParameterObject
                                                                @Parameter(description = "Parâmetros de paginação e ordenação. Exemplo: ?page=0&size=10&sort=description,asc")
                                                                Pageable pageable) {
        Page<ProfessionalProfileDTO> response = ppService.findAll(pageable);
        return ResponseEntity.ok(response);
    }

}
