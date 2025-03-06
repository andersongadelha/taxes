package br.com.zup.taxes.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/impostos")
public class TaxController {

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public String teste(){
        return "Deu bom";
    }
}
