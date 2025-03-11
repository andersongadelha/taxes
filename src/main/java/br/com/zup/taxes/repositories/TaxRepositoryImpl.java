package br.com.zup.taxes.repositories;

import br.com.zup.taxes.controllers.dto.TaxDto;
import br.com.zup.taxes.controllers.dto.TaxResponseDto;
import br.com.zup.taxes.exceptions.TaxNotFoundException;
import br.com.zup.taxes.models.Tax;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class TaxRepositoryImpl implements TaxRepository {

    private final TaxJpaRepository taxJpaRepository;

    @Override
    public TaxResponseDto register(TaxDto taxDto) {
        Tax taxToPersist = new Tax();
        taxToPersist.setName(taxDto.getName());
        taxToPersist.setDescription(taxDto.getDescription());
        taxToPersist.setAliquot(taxDto.getAliquot());

        Tax taxPersisted = taxJpaRepository.save(taxToPersist);

        return TaxResponseDto.builder()
                .id(taxPersisted.getId())
                .name(taxPersisted.getName())
                .description(taxPersisted.getDescription())
                .aliquot(taxPersisted.getAliquot())
                .build();
    }

    @Override
    public List<TaxResponseDto> findAll() {
        List<Tax> taxes = taxJpaRepository.findAll();
        return taxes.stream()
                .map(tax -> new TaxResponseDto(tax.getId(), tax.getName(), tax.getDescription(), tax.getAliquot()))
                .toList();
    }

    @Override
    public TaxResponseDto findById(Long id) {
        Tax tax = taxJpaRepository.findById(id)
                .orElseThrow(() -> new TaxNotFoundException("Imposto não cadastrado na base."));
        return TaxResponseDto.builder()
                .id(tax.getId())
                .name(tax.getName())
                .description(tax.getDescription())
                .aliquot(tax.getAliquot())
                .build();
    }
}
