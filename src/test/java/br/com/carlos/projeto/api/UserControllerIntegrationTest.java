package br.com.carlos.projeto.api;

import com.jayway.jsonpath.JsonPath;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class UserControllerIntegrationTest {

    @Autowired
    MockMvc mock;

    String existingUserEmail = "usuarioteste@exemplo.com";
    String existingUserPassword = "123456789";

    Long createdUserId;

    @BeforeEach
    void setup() throws Exception {

        String body = """
                {
                    "email": "%s",
                    "password": "%s",
                    "name": "Usuário teste"
                }
                """.formatted(existingUserEmail, existingUserPassword);

        String registerResponse = mock.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        createdUserId = Long.parseLong(JsonPath.read(registerResponse, "$.id"));
    }

    @Nested
    class LoggedInTests {

        String token;

        @BeforeEach
        void setup() throws Exception {
            String body = """
                            {
                                "login": "%s",
                                "password": "%s"
                            }
                    """.formatted(existingUserEmail, existingUserPassword);


            token = mock.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            token = token.replace("{\"token\":\"", "").replace("\"}", "");
        }

        @Test
        void dadoUsuarioSemPerfilProfissionalAoTentarCriarDeveRetornar201() throws Exception {
            String body = """
                    {
                        "description":"Descrição teste."
                    }""";

            mock.perform(post("/users/professional-profile")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.description").value("Descrição teste."));
        }

        @Test
        void dadoUsuarioComPerfilProfissionalAoTentarCriarDeveRetornar400() throws Exception {
            String body = """
                    {
                        "description":"Descrição teste."
                    }""";

            /// Cria o perfil profissional pela primeira vez
            mock.perform(post("/users/professional-profile")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(status().isCreated());

            /// Tenta criar o perfil profissional novamente
            mock.perform(post("/users/professional-profile")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(status().isBadRequest());
        }

        @Test
        void buscarUsuarioDeveRetornar200() throws Exception {
            mock.perform(get("/users/"+createdUserId)
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.email").value(existingUserEmail));
        }

        @Test
        void buscarTodosUsuariosDeveRetornar200() throws Exception {
            mock.perform(get("/users")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray());
        }
    }


}
