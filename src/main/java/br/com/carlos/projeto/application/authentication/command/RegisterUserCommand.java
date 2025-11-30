package br.com.carlos.projeto.application.authentication.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Comando para registrar um novo usuário.")
public record RegisterUserCommand(

        @Schema(description = "Nome completo do usuário.", example = "Carlos Silva")
        @NotBlank(message = "O nome é obrigatório.")
        @Size(min = 3, max = 50, message = "O nome deve ter entre 3 e 50 caracteres.")
        String name,

        @Schema(description = "E-mail do usuário.", example = "email@example.com")
        @NotBlank(message = "O e-mail é obrigatório.")
        @Email(message = "Formato de e-mail inválido.")
        String email,

        @Schema(description = "Senha do usuário.", example = "SenhaSegura123!")
        @NotBlank(message = "A senha é obrigatória.")
        @Size(min = 8, max = 100, message = "A senha deve ter no mínimo 8 caracteres.")
        String password
) {
}
