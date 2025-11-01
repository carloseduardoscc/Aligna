package br.com.carlos.projeto.infra.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Value("${app.version}")
    private String appVersion;

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("Aligna API")
                                .description("Aligna é um projeto pessoal de estudo, uma API backend feita em Java que conecta profissionais e clientes através de agendamentos de serviços.")
                                .version(appVersion)
                );
    }
}
