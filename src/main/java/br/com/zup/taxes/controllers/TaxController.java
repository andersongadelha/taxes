package br.com.zup.taxes.controllers;

import br.com.zup.taxes.dtos.TaxDto;
import br.com.zup.taxes.dtos.TaxResponseDto;
import br.com.zup.taxes.services.TaxService;
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

    @PostMapping("/tipos")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TaxResponseDto> register(@RequestBody TaxDto taxRequest) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taxService.register(taxRequest));
    }

    @GetMapping("/tipos")
    public ResponseEntity<List<TaxResponseDto>> findAll() {
        return ResponseEntity.ok(taxService.findAll());
    }

    @GetMapping("/tipos/{id}")
    public ResponseEntity<TaxResponseDto> findById(@PathVariable Long id) {
        return ResponseEntity.ok(taxService.findById(id));
    }

    @DeleteMapping("/tipos/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        taxService.deleteById(id);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
