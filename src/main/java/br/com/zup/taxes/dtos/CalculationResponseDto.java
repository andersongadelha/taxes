package br.com.zup.taxes.dtos;

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
    private String taxName;
    private BigDecimal baseValue;
    private Double aliquot;
    private BigDecimal taxAmount;
}
