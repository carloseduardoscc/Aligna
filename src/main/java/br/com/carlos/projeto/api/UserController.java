package br.com.carlos.projeto.api;

import br.com.carlos.projeto.application.user.dto.PublicUserDTO;
import br.com.carlos.projeto.application.user.useCase.FindAllUserUseCase;
import br.com.carlos.projeto.application.user.useCase.FindUserByIdUseCase;
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
@RequestMapping("/users")
@Tag(name = "Ações sobre usuários")
@AllArgsConstructor
public class UserController {

    FindUserByIdUseCase findUserByIdUseCase;
    FindAllUserUseCase findAllUserUseCase;

    @Operation(summary = "Busca um usuário pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<PublicUserDTO> findById(@PathVariable Long id) {
        PublicUserDTO response = findUserByIdUseCase.execute(id);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Busca todos os usuários com paginação")
    @GetMapping
    public ResponseEntity<Page<PublicUserDTO>> findAll(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC)
                                                       @ParameterObject
                                                       @Parameter(description = "Parâmetros de paginação e ordenação. Exemplo: ?page=0&size=10&sort=name,asc")
                                                       Pageable pageable) {
        Page<PublicUserDTO> response = findAllUserUseCase.execute(pageable);
        return ResponseEntity.ok(response);
    }
}
