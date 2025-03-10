package br.com.zup.taxes.controllers.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseRegisterUserDto {
    private Long id;
    @JsonProperty(value = "usuario")
    private String userName;
    @JsonProperty(value = "papel")
    private RoleEnum role;
}
