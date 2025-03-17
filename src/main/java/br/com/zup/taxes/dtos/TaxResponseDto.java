package br.com.zup.taxes.dtos;

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
public class TaxResponseDto {
    private Long id;
    @JsonProperty(value = "nome")
    private String name;
    @JsonProperty(value = "descricao")
    private String description;
    @JsonProperty(value = "aliquota")
    private Double aliquot;
}
