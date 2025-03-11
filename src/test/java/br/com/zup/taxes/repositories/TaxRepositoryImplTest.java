package br.com.zup.taxes.repositories;

import br.com.zup.taxes.controllers.dto.TaxDto;
import br.com.zup.taxes.controllers.dto.TaxResponseDto;
import br.com.zup.taxes.models.Tax;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TaxRepositoryImplTest {

    @Mock
    private TaxJpaRepository taxJpaRepository;

    @InjectMocks
    private TaxRepositoryImpl taxRepositoryImpl;

    @Test
    void testRegister_ShouldPersistTaxAndReturnResponseDto() {
        // Arrange
        TaxDto taxDto = new TaxDto();
        taxDto.setName("Tax Name");
        taxDto.setDescription("Tax Description");
        taxDto.setAliquot(10.0);

        Tax taxToPersist = new Tax();
        taxToPersist.setName(taxDto.getName());
        taxToPersist.setDescription(taxDto.getDescription());
        taxToPersist.setAliquot(taxDto.getAliquot());

        Tax taxPersisted = new Tax();
        taxPersisted.setId(1L);
        taxPersisted.setName(taxDto.getName());
        taxPersisted.setDescription(taxDto.getDescription());
        taxPersisted.setAliquot(taxDto.getAliquot());

        when(taxJpaRepository.save(any(Tax.class))).thenReturn(taxPersisted);

        // Act
        TaxResponseDto response = taxRepositoryImpl.register(taxDto);

        // Assert
        assertEquals(1L, response.getId());
        assertEquals("Tax Name", response.getName());
        assertEquals("Tax Description", response.getDescription());
        assertEquals(10.0, response.getAliquot());
    }
}