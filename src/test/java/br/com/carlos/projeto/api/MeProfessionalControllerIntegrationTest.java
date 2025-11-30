package br.com.carlos.projeto.api;

import com.jayway.jsonpath.JsonPath;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public class MeProfessionalControllerIntegrationTest {
    @Autowired
    MockMvc mock;

    String existingProfessionalEmail = "usuarioteste@exemplo.com";
    String existingProfessionalPassword = "123456789";

    Long createdProfessionalId;

    @BeforeEach
    void setup() throws Exception {

        String body = """
                {
                    "email": "%s",
                    "password": "%s",
                    "name": "Usuário teste"
                }
                """.formatted(existingProfessionalEmail, existingProfessionalPassword);

        String registerResponse = mock.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString();

        createdProfessionalId = Long.parseLong(JsonPath.read(registerResponse, "$.id"));
    }

    @Nested
    class LoggedInTests {

        String professionalToken;

        @BeforeEach
        void setup() throws Exception {
            String body = """
                            {
                                "login": "%s",
                                "password": "%s"
                            }
                    """.formatted(existingProfessionalEmail, existingProfessionalPassword);


            professionalToken = mock.perform(post("/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(status().isOk())
                    .andReturn()
                    .getResponse()
                    .getContentAsString();

            professionalToken = professionalToken.replace("{\"token\":\"", "").replace("\"}", "");
        }

        @Test
        void dadoUsuarioSemPerfilProfissionalAoTentarCriarDeveRetornar201() throws Exception {
            String body = """
                    {
                        "description":"Descrição teste."
                    }""";

            mock.perform(post("/me/professional-profile")
                            .header("Authorization", "Bearer " + professionalToken)
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
            mock.perform(post("/me/professional-profile")
                            .header("Authorization", "Bearer " + professionalToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(status().isCreated());

            /// Tenta criar o perfil profissional novamente
            mock.perform(post("/me/professional-profile")
                            .header("Authorization", "Bearer " + professionalToken)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(body))
                    .andExpect(status().isBadRequest());
        }

        @Nested
        class EnvolveServicesTests {

            Long createdServiceId;

            @BeforeEach
            void setup() throws Exception {
                /// Cria o perfil profissional primeiro
                String body = """
                        {
                            "description":"Descrição teste."
                        }""";

                mock.perform(post("/me/professional-profile")
                                .header("Authorization", "Bearer " + professionalToken)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(body))
                        .andExpect(status().isCreated())
                        .andExpect(jsonPath("$.description").value("Descrição teste."));


                String bodyService = """
                                            {
                            "title":"Jardinagem",
                            "description":"Faço serviços de jardinagem de todos os tipos.",
                            "availableFrom":"09:00",
                            "availableUntil":"17:00",
                            "availableDays":["WEDNESDAY", "THURSDAY"]
                        }""";

                /// Registra o serviço
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

            @Nested
            class EnvolveReservesTests {

                String existingApplicantEmail = "applicant@example.com";
                String existingApplicantPassword = "123456789";
                Long createdApplicantId;
                String applicantToken;

                Long createdReserveId;

                @BeforeEach
                void setup() throws Exception {
                    registerApplicantUser();

                    String body = """
                            {
                                "service_id" : "%s",
                                "scheduledTo" : "%s"
                            }""".formatted(createdServiceId, LocalDateTime.now().plusWeeks(1).with(DayOfWeek.WEDNESDAY).withHour(10).toString());

                    String response = mock.perform(post("/me/reserves")
                                    .header("Authorization", "Bearer " + applicantToken)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(body))
                            .andExpect(status().isCreated())
                            .andReturn().getResponse().getContentAsString();

                    createdReserveId = Long.parseLong(JsonPath.read(response, "$.id").toString());
                }

                private void registerApplicantUser() throws Exception {
                    ///  aplicant user registration
                    String body = """
                            {
                                "email": "%s",
                                "password": "%s",
                                "name": "Usuário teste"
                            }
                            """.formatted(existingApplicantEmail, existingApplicantPassword);

                    String registerResponse = mock.perform(post("/auth/register")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(body))
                            .andExpect(status().isCreated())
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

                    createdApplicantId = Long.parseLong(JsonPath.read(registerResponse, "$.id"));

                    /// applicant user login
                    body = """
                            {
                                "login": "%s",
                                "password": "%s"
                            }
                    """.formatted(existingApplicantEmail, existingApplicantPassword);


                    applicantToken = mock.perform(post("/auth/login")
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(body))
                            .andExpect(status().isOk())
                            .andReturn()
                            .getResponse()
                            .getContentAsString();

                    applicantToken = applicantToken.replace("{\"token\":\"", "").replace("\"}", "");

                }

                @ParameterizedTest
                @CsvFileSource(resources = "/reserve-status-transitions-Mock.csv", numLinesToSkip = 1)
                void shouldValidateStateTransitions(String initialState, String newState, int expectedHttpStatus) throws Exception {
                    /// Primeiro coloca a reserva no estado inicial
                    performStatusTransition(createdReserveId, initialState);

                    /// Depois tenta fazer a transição para o novo estado esperando o status HTTP esperado
                    int returnedHttpStatus = performStatusTransition(createdReserveId, newState);

                    assertEquals(expectedHttpStatus, returnedHttpStatus);
                }

                int performStatusTransition(Long reserveId, String status) throws Exception {
                    switch (status) {
                        case "ACCEPTED" -> {
                            return mock.perform(post("/me/professional-profile/services/{serviceId}/reserves/{reserveId}/accept",createdServiceId, reserveId)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .header("Authorization", "Bearer " + professionalToken))
                                    .andReturn().getResponse().getStatus();
                        }
                        case "REJECTED" -> {
                            return mock.perform(post("/me/professional-profile/services/{serviceId}/reserves/{reserveId}/reject",createdServiceId, reserveId)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .header("Authorization", "Bearer " + professionalToken))
                                    .andReturn().getResponse().getStatus();
                        }
                        case "CANCELED" -> {
                            return mock.perform(post("/me/reserves/{reserveId}/cancel",reserveId)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .header("Authorization", "Bearer " + applicantToken))
                                    .andReturn().getResponse().getStatus();
                        }
                        default -> {
                            return 0;
                        }
                    }
                }
            }
        }
    }
}
