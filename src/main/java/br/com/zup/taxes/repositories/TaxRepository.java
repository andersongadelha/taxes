package br.com.zup.taxes.repositories;

import br.com.zup.taxes.dtos.TaxDto;
import br.com.zup.taxes.dtos.TaxResponseDto;

import java.util.List;

public interface TaxRepository {
    TaxResponseDto register(TaxDto taxDto);
    List<TaxResponseDto> findAll();
    TaxResponseDto findById(Long id);
    void deleteById(Long id);
}
