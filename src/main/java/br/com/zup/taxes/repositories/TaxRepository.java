package br.com.zup.taxes.repositories;

import br.com.zup.taxes.controllers.dto.TaxDto;
import br.com.zup.taxes.controllers.dto.TaxResponseDto;

import java.util.List;

public interface TaxRepository {
    TaxResponseDto register(TaxDto taxDto);
    List<TaxResponseDto> findAll();
    TaxResponseDto findById(Long id);
}
