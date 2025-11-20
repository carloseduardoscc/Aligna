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

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MeControllerIntegrationTest {
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

        @Nested
        class EnvolvesProfessionalUser {

            String existingProfessionalUserEmail = "usuarioprofissional@exemplo.com";
            String existingProfessionalUserPassword = "123456789";

            Long createdProfessionalUserId;
            Long createdServiceId;

            String professionalToken;

            @BeforeEach
            void setup() throws Exception {

                /// registrando usuário profissional

                String body = """
                        {
                            "email": "%s",
                            "password": "%s",
                            "name": "Usuário profissional teste"
                        }
                        """.formatted(existingProfessionalUserEmail, existingProfessionalUserPassword);

                String registerResponse = mock.perform(post("/auth/register")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

                createdProfessionalUserId = Long.parseLong(JsonPath.read(registerResponse, "$.id"));

                /// logando usuário profissional

                body = """
                            {
                                "login": "%s",
                                "password": "%s"
                            }
                    """.formatted(existingProfessionalUserEmail, existingProfessionalUserPassword);


                professionalToken = mock.perform(post("/auth/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                        .andExpect(status().isOk())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

                professionalToken = professionalToken.replace("{\"token\":\"", "").replace("\"}", "");

                /// registrando perfil profissional para o usuário profissional

                body = """
                    {
                        "description":"Descrição teste."
                    }""";

                mock.perform(post("/me/professional-profile")
                                .header("Authorization", "Bearer " + professionalToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.description").value("Descrição teste."));



                /// registrando servico para o usuário profissional

                String bodyService = """
                                        {
                        "title":"Jardinagem",
                        "description":"Faço serviços de jardinagem de todos os tipos.",
                        "availableFrom":"09:00",
                        "availableUntil":"17:00",
                        "availableDays":["WEDNESDAY", "THURSDAY"]
                    }""";


                String registerServiceResponse = mock.perform(post("/me/professional-profile/services")
                                .header("Authorization", "Bearer " + professionalToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(bodyService))
                        .andExpect(status().isCreated())
                        .andReturn()
                        .getResponse()
                        .getContentAsString();

                createdServiceId = Long.parseLong(JsonPath.read(registerServiceResponse, "$.id").toString());
            }

            @Test
            void deveCriarReservaSemErro() throws Exception{
                String body = """
                        {
                            "service_id" : "%s",
                            "dateTime" : "%s"
                        }""".formatted(createdServiceId, LocalDateTime.now().plusWeeks(1).with(DayOfWeek.WEDNESDAY).withHour(10).toString());

                mock.perform(post("/me/reserves")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                        .andExpect(status().isCreated());
            }

            @Test
            void deveLancarExcecaoAoCriarReservaComHorarioIndisponivel() throws Exception{
                String body = """
                        {
                            "service_id" : "%s",
                            "dateTime" : "%s"
                        }""".formatted(createdServiceId, LocalDateTime.now().plusWeeks(1).with(DayOfWeek.TUESDAY).withHour(10).toString());

                mock.perform(post("/me/reserves")
                                .header("Authorization", "Bearer " + token)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                        .andExpect(status().isBadRequest());
            }
        }


    }
}
