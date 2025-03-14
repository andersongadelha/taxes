package br.com.zup.taxes.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    @JsonProperty(value = "usuario")
    private String userName;
    @JsonProperty(value = "senha")
    private String password;
    @JsonProperty(value = "papel")
    private RoleEnum role;
}
