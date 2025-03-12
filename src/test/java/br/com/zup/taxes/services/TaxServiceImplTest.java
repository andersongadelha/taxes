package br.com.zup.taxes.services;

import br.com.zup.taxes.dtos.TaxDto;
import br.com.zup.taxes.dtos.TaxResponseDto;
import br.com.zup.taxes.exceptions.TaxNotFoundException;
import br.com.zup.taxes.repositories.TaxRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

@ExtendWith(value = MockitoExtension.class)
class TaxServiceImplTest {

    @Mock
    private TaxRepository taxRepository;

    @InjectMocks
    private TaxServiceImpl taxService;

    @Test
    void shouldRegisterTaxSuccessfully() {
        // Arrange
        TaxDto taxDto = new TaxDto();
        TaxResponseDto responseDto = TaxResponseDto.builder()
                .id(1L)
                .name("Tax name")
                .description("Tax description")
                .aliquot(1.0)
                .build();

        when(taxRepository.register(taxDto)).thenReturn(responseDto);

        // Act
        TaxResponseDto result = taxService.register(taxDto);

        // Assert
        assertEquals(responseDto.getId(), result.getId());
        assertEquals(responseDto.getName(), result.getName());
        assertEquals(responseDto.getDescription(), result.getDescription());
        assertEquals(responseDto.getAliquot(), result.getAliquot());
        verify(taxRepository, times(1)).register(taxDto);
    }

    @Test
    void shouldReturnAllTaxesSuccessfully() {
        // Arrange
        TaxResponseDto tax1 = TaxResponseDto.builder()
                .id(1L)
                .name("Tax 1")
                .description("Description 1")
                .aliquot(5.0)
                .build();

        TaxResponseDto tax2 = TaxResponseDto.builder()
                .id(2L)
                .name("Tax 2")
                .description("Description 2")
                .aliquot(10.0)
                .build();

        List<TaxResponseDto> taxes = List.of(tax1, tax2);

        when(taxRepository.findAll()).thenReturn(taxes);

        // Act
        List<TaxResponseDto> result = taxService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertEquals(tax1.getId(), result.get(0).getId());
        assertEquals(tax1.getName(), result.get(0).getName());
        assertEquals(tax1.getDescription(), result.get(0).getDescription());
        assertEquals(tax1.getAliquot(), result.get(0).getAliquot());
        assertEquals(tax2.getId(), result.get(1).getId());
        assertEquals(tax2.getName(), result.get(1).getName());
        assertEquals(tax2.getDescription(), result.get(1).getDescription());
        assertEquals(tax2.getAliquot(), result.get(1).getAliquot());
        verify(taxRepository, times(1)).findAll();
    }

    @Test
    void shouldReturnTaxByIdSuccessfully() {
        // Arrange
        Long taxId = 1L;
        TaxResponseDto responseDto = TaxResponseDto.builder()
                .id(taxId)
                .name("Tax name")
                .description("Tax description")
                .aliquot(1.0)
                .build();

        when(taxRepository.findById(taxId)).thenReturn(responseDto);

        // Act
        TaxResponseDto result = taxService.findById(taxId);

        // Assert
        assertEquals(responseDto.getId(), result.getId());
        assertEquals(responseDto.getName(), result.getName());
        assertEquals(responseDto.getDescription(), result.getDescription());
        assertEquals(responseDto.getAliquot(), result.getAliquot());
        verify(taxRepository, times(1)).findById(taxId);
    }

    @Test
    void shouldThrowExceptionWhenTaxNotFoundById() {
        // Arrange
        Long taxId = 999L;
        when(taxRepository.findById(taxId)).thenThrow(new TaxNotFoundException("Imposto não cadastrado na base."));

        // Act & Assert
        TaxNotFoundException exception = assertThrows(TaxNotFoundException.class, () -> taxService.findById(taxId));
        assertEquals("Imposto não cadastrado na base.", exception.getMessage());
        verify(taxRepository, times(1)).findById(taxId);
    }

    @Test
    void shouldDeleteTaxSuccessfully() {
        // Arrange
        Long taxId = 1L;

        // Act
        taxService.deleteById(taxId);

        // Assert
        verify(taxRepository, times(1)).deleteById(taxId);
    }
}