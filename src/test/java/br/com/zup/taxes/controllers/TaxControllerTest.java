package br.com.zup.taxes.controllers;

import br.com.zup.taxes.infra.BaseIT;
import br.com.zup.taxes.infra.jwt.JwtTestUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class TaxControllerTest extends BaseIT {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtTestUtil jwtTestUtil;



    @Test
    public void shouldRegisterTaxSuccessfully_withAdminRole() throws Exception {
        // Arrange
        String token = jwtTestUtil.generateToken("testUserAdmin", "ROLE_ADMIN");
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
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Tax Name"))
                .andExpect(jsonPath("$.description").value("Tax Description"))
                .andExpect(jsonPath("$.aliquot").value(10.0));
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
}