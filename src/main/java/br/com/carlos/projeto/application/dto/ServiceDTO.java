package br.com.carlos.projeto.application.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Set;

public record ServiceDTO(Long id, String title, String description, LocalTime availableFrom, LocalTime availableUntil, Set<DayOfWeek> availableDays, Long professionalProfileId) {
}
