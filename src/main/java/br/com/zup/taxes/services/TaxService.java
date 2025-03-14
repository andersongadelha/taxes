package br.com.zup.taxes.services;

import br.com.zup.taxes.dtos.CalculationRequestDto;
import br.com.zup.taxes.dtos.CalculationResponseDto;
import br.com.zup.taxes.dtos.TaxDto;
import br.com.zup.taxes.dtos.TaxResponseDto;

import java.util.List;

public interface TaxService {
    TaxResponseDto register(TaxDto taxDto);
    List<TaxResponseDto> findAll();
    TaxResponseDto findById(Long id);
    void deleteById(Long id);
    CalculationResponseDto calculate(CalculationRequestDto requestDto);
}
