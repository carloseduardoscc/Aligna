package br.com.carlos.projeto.application.reserve.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Data Transfer Object representando uma reserva de serviço.")
public record ReserveDTO(
        @Schema(description = "Identificador único da reserva.", example = "1")
        Long id,
        @Schema(description = "Data e hora da reserva.", example = "2024-07-01T14:30:00")
        LocalDateTime dateTime,
        @Schema(description = "Status da reserva.", example = "PENDING")
        String status,
        @Schema(description = "Identificador do solicitante da reserva.", example = "3")
        Long applicant_id,
        @Schema(description = "Identificador do serviço reservado.", example = "5")
        Long service_id) {
}
