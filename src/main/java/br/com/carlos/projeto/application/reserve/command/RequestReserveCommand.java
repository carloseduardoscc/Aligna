package br.com.carlos.projeto.application.reserve.command;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Comando para solicitar uma reserva de serviço")
public record RequestReserveCommand(
        @Schema(description = "ID do serviço a ser reservado", example = "1")
        Long service_id,
        @Schema(description = "Data e hora agendadas para a reserva", example = "2024-07-15T10:00:00")
        LocalDateTime scheduledTo) {
}
