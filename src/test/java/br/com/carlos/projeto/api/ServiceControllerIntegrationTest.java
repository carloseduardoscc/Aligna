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
public class ServiceControllerIntegrationTest {

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

        mock.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());
    }

    @Nested
    class LoggedInTests {

        String token;
        Long professionalProfileId;
        String existingProfileDescription = "Descrição teste.";

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

            String bodyProfile = """
                    {
                        "description":"%s"
                    }""".formatted(existingProfileDescription);

            String registerProfileResponse = mock.perform(post("/me/professional-profile")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(bodyProfile))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            professionalProfileId = Long.parseLong(JsonPath.read(registerProfileResponse, "$.id").toString());
        }

        @Test
        void registrarServicoDeveRetornar201() throws Exception {
            String bodyService = """
                                        {
                        "title":"Marcenaria",
                        "description":"Faço serviços de marcenaria de todos os tipos.",
                        "availableFrom":"08:00",
                        "availableUntil":"16:00",
                        "availableDays":["MONDAY", "TUESDAY", "FRIDAY"]
                    }""";

            mock.perform(post("/me/services")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(bodyService))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.title").value("Marcenaria"))
                    .andExpect(jsonPath("$.description").value("Faço serviços de marcenaria de todos os tipos."));
        }

        @Test
        void buscarServicosPaginadosDeveRetornar200() throws Exception {
            mock.perform(get("/services")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.content").isArray());
        }

        @Test
        void buscarServicoPorIdDeveRetornar200() throws Exception {
            String bodyService = """
                                        {
                        "title":"Jardinagem",
                        "description":"Faço serviços de jardinagem de todos os tipos.",
                        "availableFrom":"09:00",
                        "availableUntil":"17:00",
                        "availableDays":["WEDNESDAY", "THURSDAY"]
                    }""";

            String registerServiceResponse = mock.perform(post("/me/services")
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(bodyService))
                    .andExpect(status().isCreated())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            Long serviceId = Long.parseLong(JsonPath.read(registerServiceResponse, "$.id").toString());

            mock.perform(get("/services/%d".formatted(serviceId))
                            .header("Authorization", "Bearer " + token)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.title").value("Jardinagem"))
                    .andExpect(jsonPath("$.description").value("Faço serviços de jardinagem de todos os tipos."));
        }
    }
}
