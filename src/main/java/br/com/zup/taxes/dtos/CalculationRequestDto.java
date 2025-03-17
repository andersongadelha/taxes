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
@AllArgsConstructor
@NoArgsConstructor
public class CalculationRequestDto {
    @JsonProperty(value = "tipoImpostoId")
    private Long taxId;
    @JsonProperty(value = "valorBase")
    private BigDecimal baseValue;
}
