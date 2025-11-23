package br.com.carlos.projeto.application.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object representando um usuário.")
public record UserDTO(

        @Schema(description = "Identificador único do usuário.", example = "1")
        String id,
        @Schema(description = "Nome completo do usuário.", example = "Carlos Silva")
        String name,
        @Schema(description = "Endereço de e-mail do usuário.", example = "email@example.com")
        String email,
        @Schema(description = "Identificador do perfil profissional associado ao usuário.", example = "2")
        Long professionalProfileId) {
}
