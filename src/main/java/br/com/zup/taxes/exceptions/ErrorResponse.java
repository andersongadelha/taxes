package br.com.zup.taxes.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    @JsonProperty(value = "erro")
    private String error;
    @JsonProperty(value = "mensagem")
    private String message;
    @JsonProperty(value = "caminho")
    private String path;
}