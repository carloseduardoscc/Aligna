package br.com.carlos.projeto.api;

import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class AuthenticationControllerIntegrationTest {

    @Autowired
    MockMvc mock;

    String existingUserEmail = "usuarioteste@exemplo.com";
    String existingUserPassword = "123456789";

    @BeforeEach
    void setup() throws Exception {

        String body = """
                {
                    "email": "%s",
                    "password": "%s",
                    "name": "Usuário teste"
                }
                """.formatted(existingUserEmail, existingUserPassword);

        mock.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());
    }

    @Test
    void dadoEmailNovoAoFazerRegistroDeveRetornar201() throws Exception {
        String body = """
                {
                    "email": "novo@exemplo.com",
                    "password": "123456789",
                    "name": "Novo Usuário"
                }
                """;

        mock.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("novo@exemplo.com"));
    }

    @Test
    void dadoLoginValidoDeveRetornarToken() throws Exception {
        String body = """
                {
                    "login": "%s",
                    "password": "%s"
                }
                """.formatted(existingUserEmail, existingUserPassword);

        mock.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void dadoLoginInvalidoDeveRetornar401() throws Exception {
        String body = """
                        {
                            "login": "invalido@exemplo.com",
                            "password": "senhaErrada"
                        }
                """;

        mock.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void dadoTokenValidoAoConsultarMeDeveRetornarUsuario() throws Exception {
        String body = """
                        {
                            "login": "%s",
                            "password": "%s"
                        }
                """.formatted(existingUserEmail, existingUserPassword);

        String token = mock.perform(post("/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        token = token.replace("{\"token\":\"", "").replace("\"}", "");

        mock.perform(get("/auth/me")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(existingUserEmail));

    }

    @Test
    void naoDadoTokenAoConsultarMeDeveRetornar401() throws Exception {
        mock.perform(get("/auth/me"))
                .andExpect(status().isUnauthorized());
    }

}
