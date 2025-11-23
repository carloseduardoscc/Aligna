package br.com.carlos.projeto.application.professional.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Comando para registrar o perfil profissional do usuário.")
public record RegisterProfessionalProfileCommand(

        @Schema(description = "Descrição do perfil profissional do usuário.", example = "Desenvolvedor de software com 5 anos de experiência em Java e Spring Boot.")
        @NotBlank(message = "A descrição é obrigatória.")
        @Size(min = 10, max = 500, message = "A descrição deve ter entre 10 e 500 caracteres.")
        String description
) {
}
