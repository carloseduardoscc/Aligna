package br.com.carlos.projeto.application.reserve.command;

import java.time.LocalDateTime;

public record RequestReserveCommand(Long service_id, LocalDateTime dateTime) {
}
