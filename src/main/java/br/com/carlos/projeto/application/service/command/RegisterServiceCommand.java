package br.com.carlos.projeto.application.service.command;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.time.LocalTime;
import java.util.Set;

@Schema(description = "Comando para registrar um novo serviço oferecido pelo profissional.")
public record RegisterServiceCommand(
        @Schema(description = "Título do serviço.", example = "Aula de Yoga para Iniciantes")
        @NotBlank(message = "O título do serviço é obrigatório.")
        String title,

        @Schema(description = "Descrição detalhada do serviço.", example = "Aula de yoga voltada para iniciantes, focando em posturas básicas e técnicas de respiração.")
        @NotBlank(message = "A descrição do serviço é obrigatória.")
        String description,

        @Schema(description = "Horário em que o serviço está disponível a partir de.", example = "08:00")
        LocalTime availableFrom,

        @Schema(description = "Horário em que o serviço está disponível até.", example = "18:00")
        LocalTime availableUntil,

        @Schema(description = "Dias da semana em que o serviço está disponível.", example = "[\"MONDAY\", \"WEDNESDAY\", \"FRIDAY\"]")
        Set<String> availableDays
) {
}
