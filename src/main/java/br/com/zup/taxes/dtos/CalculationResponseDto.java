package br.com.zup.taxes.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CalculationResponseDto {
    @JsonProperty(value = "tipoImposto")
    private String taxName;
    @JsonProperty(value = "valorBase")
    private BigDecimal baseValue;
    @JsonProperty(value = "aliquota")
    private Double aliquot;
    @JsonProperty(value = "valorImposto")
    private BigDecimal taxAmount;
}
