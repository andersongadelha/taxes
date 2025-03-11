package br.com.zup.taxes.services;

import br.com.zup.taxes.controllers.dto.TaxDto;
import br.com.zup.taxes.controllers.dto.TaxResponseDto;

import java.util.List;

public interface TaxService {
    TaxResponseDto register(TaxDto taxDto);
    List<TaxResponseDto> findAll();
    TaxResponseDto findById(Long id);
}
