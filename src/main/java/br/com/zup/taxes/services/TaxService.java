package br.com.zup.taxes.services;

import br.com.zup.taxes.controllers.dto.TaxDto;
import br.com.zup.taxes.controllers.dto.TaxResponseDto;

public interface TaxService {
    TaxResponseDto register(TaxDto taxDto);
}
