package br.com.zup.taxes.controllers;

import br.com.zup.taxes.infra.BaseIT;
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
class UserControllerIT extends BaseIT {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void mustRegisterUserSuccessfully() throws Exception {
        String requestBody = """
                    {
                        "userName": "test_userAdmin",
                        "password": "12345",
                        "role": "ROLE_ADMIN"
                    }
                """;
        mockMvc.perform(post("/usuario/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.usuario").value("test_userAdmin"));

    }

    @Test
    public void shouldReturnBadRequest_userRegistered() throws Exception {
        String requestBody = """
                    {
                        "userName": "testUserAdmin",
                        "password": "12345",
                        "role": "ROLE_ADMIN"
                    }
                """;
        mockMvc.perform(post("/usuario/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Usuário já cadastrado."));

    }

    @Test
    public void shouldReturnBadRequest_roleNotFound() throws Exception {
        String requestBody = """
                    {
                        "userName": "test_userAdmin",
                        "password": "12345",
                        "role": "ROLE_NEW"
                    }
                """;
        mockMvc.perform(post("/usuario/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.mensagem").value("Papel não cadastrado na base."));
    }

    @Test
    public void mustLoginSuccessfully() throws Exception {
        String requestBody = """
                    {
                        "userName": "testUserAdmin",
                        "password": "testPassword"
                    }
                """;
        mockMvc.perform(post("/usuario/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").isNotEmpty());
    }

    @Test
    public void shouldReturnUnathorized_BadCredentials() throws Exception {
        String requestBody = """
                    {
                        "userName": "testUserAdmin",
                        "password": "12345"
                    }
                """;
        mockMvc.perform(post("/usuario/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.mensagem").value("Credenciais inválidas."));

    }
}