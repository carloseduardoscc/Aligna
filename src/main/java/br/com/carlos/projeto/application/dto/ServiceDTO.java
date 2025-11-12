package br.com.carlos.projeto.application.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

@Schema(description = "Data Transfer Object representando um serviço oferecido por um profissional.")
public record ServiceDTO(
        @Schema(description = "Identificador único do serviço.", example = "1")
        Long id,
        @Schema(description = "Título do serviço.", example = "Consultoria em Desenvolvimento de Software")
        String title,
        @Schema(description = "Descrição detalhada do serviço.", example = "Ofereço consultoria especializada em desenvolvimento de software utilizando as melhores práticas do mercado.")
        String description,
        @Schema(description = "Horário em que o serviço está disponível para atendimento.", example = "09:00:00")
        LocalTime availableFrom,
        @Schema(description = "Horário em que o serviço deixa de estar disponível para atendimento.", example = "18:00:00")
        LocalTime availableUntil,
        @Schema(description = "Dias da semana em que o serviço está disponível.", example = "[MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY]")
        Set<DayOfWeek> availableDays,
        @Schema(description = "Identificador do perfil profissional associado ao serviço.", example = "2")
        Long professionalProfileId) {
}
