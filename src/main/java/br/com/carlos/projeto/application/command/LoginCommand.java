package br.com.carlos.projeto.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Comando para realizar o login do usuário.")
public record LoginCommand(

        @Schema(description = "Login do usuário.", example = "email@example.com")
        @NotBlank(message = "O login é obrigatório.")
        String login,
        @Schema(description = "Senha do usuário.", example = "SenhaSegura123!")
        @NotBlank(message = "A senha é obrigatória.")
        String password
) {
}
