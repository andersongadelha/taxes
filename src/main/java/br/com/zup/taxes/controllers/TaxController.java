package br.com.zup.taxes.controllers;

import br.com.zup.taxes.controllers.dto.TaxDto;
import br.com.zup.taxes.controllers.dto.TaxResponseDto;
import br.com.zup.taxes.services.TaxService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/impostos")
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;

    @PostMapping("/tipos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaxResponseDto> register(@RequestBody TaxDto taxRequest){

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taxService.register(taxRequest));
    }
}
