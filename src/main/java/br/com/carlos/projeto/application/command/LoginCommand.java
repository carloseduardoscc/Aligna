package br.com.carlos.projeto.application.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Comando para realizar o login do usuário.")
public record LoginCommand(

        @Schema(description = "Login do usuário.", examples = {"carlos@gmail.com", "paulo@gmail.com"})
        @NotBlank(message = "O login é obrigatório.")
        String login,
        @Schema(description = "Senha do usuário.", examples = {"123456789", "123456789"})
        @NotBlank(message = "A senha é obrigatória.")
        String password
) {
}
