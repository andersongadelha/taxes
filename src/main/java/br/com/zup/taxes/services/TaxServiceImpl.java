package br.com.zup.taxes.services;

import br.com.zup.taxes.dtos.CalculationRequestDto;
import br.com.zup.taxes.dtos.CalculationResponseDto;
import br.com.zup.taxes.dtos.TaxDto;
import br.com.zup.taxes.dtos.TaxResponseDto;
import br.com.zup.taxes.repositories.TaxRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxServiceImpl implements TaxService {

    private final TaxRepository taxRepository;

    @Override
    public TaxResponseDto register(TaxDto taxDto) {
        return taxRepository.register(taxDto);
    }

    @Override
    public List<TaxResponseDto> findAll() {
        return taxRepository.findAll();
    }

    @Override
    public TaxResponseDto findById(Long id) {
        return taxRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        taxRepository.deleteById(id);
    }

    @Override
    public CalculationResponseDto calculate(CalculationRequestDto requestDto) {
        TaxResponseDto taxDto = taxRepository.findById(requestDto.getTaxId());
        BigDecimal result = requestDto.getBaseValue().multiply(BigDecimal.valueOf(taxDto.getAliquot() / 100));

        return CalculationResponseDto.builder()
                .taxName(taxDto.getName())
                .taxAmount(result)
                .baseValue(requestDto.getBaseValue())
                .aliquot(taxDto.getAliquot())
                .build();
    }
}
