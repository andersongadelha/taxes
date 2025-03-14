package br.com.zup.taxes.controllers;

import br.com.zup.taxes.dtos.CalculationRequestDto;
import br.com.zup.taxes.dtos.CalculationResponseDto;
import br.com.zup.taxes.dtos.ResponseRegisterUserDto;
import br.com.zup.taxes.dtos.TaxDto;
import br.com.zup.taxes.dtos.TaxResponseDto;
import br.com.zup.taxes.services.TaxService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/impostos")
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;

    @Operation(summary = "Endpoint para salvar impostos na base. Acesso exclusivo para ADMIN.")
    @ApiResponses(value = @ApiResponse(
            responseCode = "201",
            description = " Imposto salvo com sucesso.",
            content = @Content(schema = @Schema(implementation = TaxResponseDto.class))))
    @PostMapping("/tipos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaxResponseDto> register(@RequestBody TaxDto taxRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taxService.register(taxRequest));
    }

    @Operation(summary = "Endpoint para buscar todos impostos na base.")
    @GetMapping("/tipos")
    public ResponseEntity<List<TaxResponseDto>> findAll() {
        return ResponseEntity.ok(taxService.findAll());
    }

    @Operation(summary = "Endpoint para buscar imposto por id na base.")
    @GetMapping("/tipos/{id}")
    public ResponseEntity<TaxResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(taxService.findById(id));
    }

    @Operation(summary = "Endpoint para deletar um imposto por id na base. Acesso exclusivo para ADMIN.")
    @ApiResponses(value = @ApiResponse(
            responseCode = "204",
            description = " Imposto deletado com sucesso."))
    @DeleteMapping("/tipos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        taxService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/calculo")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CalculationResponseDto> calculate(@RequestBody CalculationRequestDto requestDto) {
        return ResponseEntity.ok(taxService.calculate(requestDto));
    }
}
