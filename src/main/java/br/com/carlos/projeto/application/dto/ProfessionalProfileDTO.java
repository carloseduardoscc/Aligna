package br.com.carlos.projeto.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object representando o perfil profissional de um usuário.")
public record ProfessionalProfileDTO(

        @Schema(description = "Identificador único do perfil profissional.", example = "1")
        Long id,
        @Schema(description = "Descrição do perfil profissional do usuário.", example = "Desenvolvedor de software com 5 anos de experiência em Java e Spring Boot.")
        String description,
        @Schema(description = "Identificador do usuário associado ao perfil profissional.", example = "1")
        Long userId
) {
}
