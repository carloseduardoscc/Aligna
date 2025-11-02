package br.com.carlos.projeto.application.command;

public record LoginCommand(
        String login,
        String password
) {
}
