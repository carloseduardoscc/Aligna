package br.com.carlos.projeto.application.reserve.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object representando uma entrada de status de reserva.")
public record ReservationStatusEntryDTO(

        @Schema(description = "Carimbo de data/hora da entrada de status.", example = "2024-07-01T14:30:00")
        String timeStamp,
        @Schema(description = "Status da reserva.", example = "ACCEPTED")
        String status
) {
}
