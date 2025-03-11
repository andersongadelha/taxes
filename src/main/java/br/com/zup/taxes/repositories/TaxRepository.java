package br.com.zup.taxes.repositories;

import br.com.zup.taxes.controllers.dto.TaxDto;
import br.com.zup.taxes.controllers.dto.TaxResponseDto;

public interface TaxRepository {
    TaxResponseDto register(TaxDto taxDto);
}
