package br.com.carlos.projeto.application.authentication.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Data Transfer Object representando a resposta de login contendo o token de autenticação.")
public record LoginResponseDTO(String token) {
}
