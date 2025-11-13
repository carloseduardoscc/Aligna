package br.com.carlos.projeto.application.command;

import java.time.LocalDateTime;

public record RequestReserveCommand(Long service_id, LocalDateTime dateTime) {
}
