package br.com.carlos.projeto.application.dto;

import java.time.LocalDateTime;

public record ReserveDTO (Long id, LocalDateTime dateTime, String status, Long applicant_id, Long service_id ){}
