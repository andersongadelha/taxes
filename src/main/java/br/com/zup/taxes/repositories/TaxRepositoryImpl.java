package br.com.zup.taxes.repositories;

import br.com.zup.taxes.controllers.dto.TaxDto;
import br.com.zup.taxes.controllers.dto.TaxResponseDto;
import br.com.zup.taxes.models.Tax;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
}
