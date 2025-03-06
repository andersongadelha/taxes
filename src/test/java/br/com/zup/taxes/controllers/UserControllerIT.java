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
                "userName": "test_user",
                "password": "12345",
                "role": "ADMIN"
            }
        """;
        mockMvc.perform(post("/usuario/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.usuario").value("test_user"));

    }

    @Test
    public void shouldReturnBadRequest_userRegistered() throws Exception {
        String requestBody = """
            {
                "userName": "testUser",
                "password": "12345",
                "role": "ADMIN"
            }
        """;
        mockMvc.perform(post("/usuario/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Usuário já cadastrado."));

    }

    @Test
    public void shouldReturnBadRequest_roleNotFound() throws Exception {
        String requestBody = """
            {
                "userName": "test_user",
                "password": "12345",
                "role": "NEW_ROLE"
            }
        """;
        mockMvc.perform(post("/usuario/registrar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Papel não cadastrado na base."));
    }
}