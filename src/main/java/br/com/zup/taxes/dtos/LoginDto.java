package br.com.zup.taxes.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @JsonProperty(value = "usuario")
    private String userName;
    @JsonProperty(value = "senha")
    private String password;
}
