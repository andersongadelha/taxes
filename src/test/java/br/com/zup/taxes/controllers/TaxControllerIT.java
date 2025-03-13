package br.com.zup.taxes.controllers;

import br.com.zup.taxes.infra.BaseIT;
import br.com.zup.taxes.infra.jwt.JwtTestUtil;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class TaxControllerIT extends BaseIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTestUtil jwtTestUtil;
    private Long id;

    @BeforeEach
    public void init() throws Exception {
        String token = jwtTestUtil.generateToken("testUserAdmin", "ROLE_ADMIN");
        String requestBody = """
            {
                "name": "Tax Name",
                "description": "Tax Description",
                "aliquot": 10.0
            }
            """;

        MvcResult result = mockMvc.perform(post("/impostos/tipos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        id = JsonPath.parse(responseBody).read("$.id", Long.class);
    }


    @Test
    public void shouldRegisterTaxSuccessfully_withAdminRole() throws Exception {
        // Arrange
        String token = jwtTestUtil.generateToken("testUserAdmin", "ROLE_ADMIN");
        String requestBody = """
                {
                    "name": "Tax",
                    "description": "Tax description for test",
                    "aliquot": 15.0
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/impostos/tipos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Tax"))
                .andExpect(jsonPath("$.description").value("Tax description for test"))
                .andExpect(jsonPath("$.aliquot").value(15.0));
    }

    @Test
    public void shouldReturnForbidden_withUserRole() throws Exception {
        // Arrange
        String token = jwtTestUtil.generateToken("testUser", "ROLE_USER");
        String requestBody = """
                {
                    "name": "Tax Name",
                    "description": "Tax Description",
                    "aliquot": 10.0
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/impostos/tipos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnUnauthorized_whenNoTokenProvided() throws Exception {
        // Arrange
        String requestBody = """
                {
                    "name": "Tax Name",
                    "description": "Tax Description",
                    "aliquot": 10.0
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/impostos/tipos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorized_whenTokenIsInvalid() throws Exception {
        // Arrange
        String invalidToken = jwtTestUtil.generateInvalidToken();
        String requestBody = """
                {
                    "name": "Tax Name",
                    "description": "Tax Description",
                    "aliquot": 10.0
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/impostos/tipos")
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.mensagem").value("Credenciais inválidas."));
    }

    @Test
    public void shouldReturnAllTaxTypesSuccessfully_withAdminRole() throws Exception {
        // Arrange
        String token = jwtTestUtil.generateToken("testUserAdmin", "ROLE_ADMIN");

        // Act & Assert
        mockMvc.perform(get("/impostos/tipos")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void shouldReturnTaxTypeByIdSuccessfully_withAdminRole() throws Exception {
        // Arrange
        String token = jwtTestUtil.generateToken("testUserAdmin", "ROLE_ADMIN");
        Long taxId = id;

        // Act & Assert
        mockMvc.perform(get("/impostos/tipos/{id}", taxId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Tax Name"))
                .andExpect(jsonPath("$.description").value("Tax Description"))
                .andExpect(jsonPath("$.aliquot").value(10.0));
    }

    @Test
    public void shouldReturnNotFound_whenTaxTypeDoesNotExist_withAdminRole() throws Exception {
        // Arrange
        String token = jwtTestUtil.generateToken("testUserAdmin", "ROLE_ADMIN");
        Long taxId = 999L;

        // Act & Assert
        mockMvc.perform(get("/impostos/tipos/{id}", taxId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Imposto não cadastrado na base."));
    }

    @Test
    public void shouldReturnUnauthorized_whenNoTokenProvided_forFindAll() throws Exception {
        // Act & Assert
        mockMvc.perform(get("/impostos/tipos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldDeleteTaxSuccessfully_withAdminRole() throws Exception {
        // Arrange
        String token = jwtTestUtil.generateToken("testUserAdmin", "ROLE_ADMIN");
        Long taxId = id;

        // Act & Assert
        mockMvc.perform(delete("/impostos/tipos/{id}", taxId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldReturnForbidden_whenUserRoleTriesToDelete() throws Exception {
        // Arrange
        String token = jwtTestUtil.generateToken("testUser", "ROLE_USER");
        Long taxId = id;

        // Act & Assert
        mockMvc.perform(delete("/impostos/tipos/{id}", taxId)
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnUnauthorized_whenNoTokenProvided_forDelete() throws Exception {
        // Arrange
        Long taxId = id;

        // Act & Assert
        mockMvc.perform(delete("/impostos/tipos/{id}", taxId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorized_whenTokenIsInvalid_forDelete() throws Exception {
        // Arrange
        String invalidToken = jwtTestUtil.generateInvalidToken();
        Long taxId = id;

        // Act & Assert
        mockMvc.perform(delete("/impostos/tipos/{id}", taxId)
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.mensagem").value("Credenciais inválidas."));
    }

    @Test
    public void shouldCalculateTaxSuccessfully_withAdminRole() throws Exception {
        // Arrange
        String token = jwtTestUtil.generateToken("testUserAdmin", "ROLE_ADMIN");
        Long taxId = id;

        String requestBody = String.format("""
        {
            "taxId": %d,
            "baseValue": 1000.0
        }
        """, taxId);

        // Act & Assert
        mockMvc.perform(post("/impostos/calculo")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taxName").value("Tax Name"))
                .andExpect(jsonPath("$.baseValue").value(1000.0))
                .andExpect(jsonPath("$.taxAmount").value(100.00))
                .andExpect(jsonPath("$.aliquot").value(10.0));
    }

    @Test
    public void shouldReturnForbidden_whenUserRoleTriesToCalculate() throws Exception {
        // Arrange
        String token = jwtTestUtil.generateToken("testUser", "ROLE_USER");
        Long taxId = id;
        String requestBody = String.format("""
        {
            "taxId": %d,
            "baseValue": 1000.0
        }
        """, taxId);

        // Act & Assert
        mockMvc.perform(post("/impostos/calculo")
                        .header("Authorization", "Bearer " + token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnUnauthorized_whenNoTokenProvided_forCalculate() throws Exception {
        // Arrange
        Long taxId = id;
        String requestBody = String.format("""
        {
            "taxId": %d,
            "baseValue": 1000.0
        }
        """, taxId);

        // Act & Assert
        mockMvc.perform(post("/impostos/calculo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void shouldReturnUnauthorized_whenTokenIsInvalid_forCalculate() throws Exception {
        // Arrange
        Long taxId = id;
        String invalidToken = jwtTestUtil.generateInvalidToken();
        String requestBody = String.format("""
        {
            "taxId": %d,
            "baseValue": 1000.0
        }
        """, taxId);

        // Act & Assert
        mockMvc.perform(post("/impostos/calculo")
                        .header("Authorization", "Bearer " + invalidToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.mensagem").value("Credenciais inválidas."));
    }

}