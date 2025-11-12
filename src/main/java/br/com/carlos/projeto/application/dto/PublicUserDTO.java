package br.com.carlos.projeto.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object representando uma visualização pública de um usuário.")
public record PublicUserDTO(

        @Schema(description = "Identificador único do usuário.", example = "1")
        String id,
        @Schema(description = "Nome completo do usuário.", example = "Carlos Silva")
        String name,
        @Schema(description = "Identificador do perfil profissional associado ao usuário.", example = "2")
        Long professionalProfileId) {
}
