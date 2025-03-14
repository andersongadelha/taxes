package br.com.zup.taxes.repositories;

import br.com.zup.taxes.dtos.TaxDto;
import br.com.zup.taxes.dtos.TaxResponseDto;
import br.com.zup.taxes.exceptions.TaxNotFoundException;
import br.com.zup.taxes.models.Tax;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    @Test
    void testFindAll_ShouldReturnListOfTaxResponseDto() {
        // Arrange
        Tax tax1 = new Tax();
        tax1.setId(1L);
        tax1.setName("Tax 1");
        tax1.setDescription("Description 1");
        tax1.setAliquot(5.0);

        Tax tax2 = new Tax();
        tax2.setId(2L);
        tax2.setName("Tax 2");
        tax2.setDescription("Description 2");
        tax2.setAliquot(10.0);

        when(taxJpaRepository.findAll()).thenReturn(Arrays.asList(tax1, tax2));

        // Act
        List<TaxResponseDto> response = taxRepositoryImpl.findAll();

        // Assert
        assertEquals(2, response.size());

        assertEquals(1L, response.get(0).getId());
        assertEquals("Tax 1", response.get(0).getName());
        assertEquals("Description 1", response.get(0).getDescription());
        assertEquals(5.0, response.get(0).getAliquot());

        assertEquals(2L, response.get(1).getId());
        assertEquals("Tax 2", response.get(1).getName());
        assertEquals("Description 2", response.get(1).getDescription());
        assertEquals(10.0, response.get(1).getAliquot());
    }

    @Test
    void testFindById_ShouldReturnTaxResponseDto_WhenTaxExists() {
        // Arrange
        Long taxId = 1L;
        Tax tax = new Tax();
        tax.setId(taxId);
        tax.setName("Tax Name");
        tax.setDescription("Tax Description");
        tax.setAliquot(10.0);

        when(taxJpaRepository.findById(taxId)).thenReturn(Optional.of(tax));

        // Act
        TaxResponseDto response = taxRepositoryImpl.findById(taxId);

        // Assert
        assertEquals(taxId, response.getId());
        assertEquals("Tax Name", response.getName());
        assertEquals("Tax Description", response.getDescription());
        assertEquals(10.0, response.getAliquot());
    }

    @Test
    void testFindById_ShouldThrowTaxNotFoundException_WhenTaxDoesNotExist() {
        // Arrange
        Long taxId = 999L;
        when(taxJpaRepository.findById(taxId)).thenReturn(Optional.empty());

        // Act & Assert
        TaxNotFoundException exception = assertThrows(TaxNotFoundException.class, () -> taxRepositoryImpl.findById(taxId));
        assertEquals("Imposto não cadastrado na base.", exception.getMessage());
    }

    @Test
    void testDeleteById_ShouldDeleteTaxSuccessfully() {
        // Arrange
        Long taxId = 1L;

        // Act
        taxRepositoryImpl.deleteById(taxId);

        // Assert
        verify(taxJpaRepository, times(1)).deleteById(taxId);
    }
}
