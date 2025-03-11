package br.com.zup.taxes.services;

import br.com.zup.taxes.controllers.dto.TaxDto;
import br.com.zup.taxes.controllers.dto.TaxResponseDto;
import br.com.zup.taxes.repositories.TaxRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(value = MockitoExtension.class)

class TaxServiceImplTest {

    @Mock
    private TaxRepository taxRepository;
    @InjectMocks
    private TaxServiceImpl taxService;

    @Test
    void shouldRegisterTaxSuccessfully() {
        //Arrange
        TaxDto taxDto = new TaxDto();
        TaxResponseDto responseDto = TaxResponseDto.builder()
                .id(1L)
                .name("Tax name")
                .description("Tax description")
                .aliquot(1.0)
                .build();

        when(taxRepository.register(taxDto)).thenReturn(responseDto);

        //Act
        TaxResponseDto result = taxService.register(taxDto);

        //Assert
        assertEquals(responseDto.getId(), result.getId());
        assertEquals(responseDto.getName(), result.getName());
        assertEquals(responseDto.getDescription(), result.getDescription());
        assertEquals(responseDto.getAliquot(), result.getAliquot());
        verify(taxRepository, times(1)).register(taxDto);
    }
}