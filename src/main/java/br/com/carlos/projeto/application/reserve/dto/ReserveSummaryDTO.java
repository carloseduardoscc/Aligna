package br.com.carlos.projeto.application.reserve.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Data Transfer Object representando uma reserva de serviço.")
public record ReserveSummaryDTO(
        @Schema(description = "Identificador único da reserva.", example = "1")
        Long id,
        @Schema(description = "Data e hora reservada para o serviço.", example = "2024-07-01T14:30:00")
        LocalDateTime scheduledTo,
        @Schema(description = "Status da reserva.", example = "PENDING")
        String lastStatus
) {
}
