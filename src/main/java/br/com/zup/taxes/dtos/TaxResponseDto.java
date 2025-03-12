package br.com.zup.taxes.dtos;

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
    private String name;
    private String description;
    private Double aliquot;
}
