package br.com.carlos.projeto.application.command;

import java.time.LocalTime;
import java.util.Set;

public record RegisterServiceCommand(
    String title,
    String description,
    LocalTime availableFrom,
    LocalTime availableUntil,
    Set<String> availableDays
) {
}
