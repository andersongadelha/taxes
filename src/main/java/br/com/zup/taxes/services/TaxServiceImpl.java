package br.com.zup.taxes.services;

import br.com.zup.taxes.controllers.dto.TaxDto;
import br.com.zup.taxes.controllers.dto.TaxResponseDto;
import br.com.zup.taxes.repositories.TaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxServiceImpl implements TaxService {

    private final TaxRepository taxRepository;

    @Override
    public TaxResponseDto register(TaxDto taxDto) {
        return taxRepository.register(taxDto);
    }
}
