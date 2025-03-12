package br.com.zup.taxes.exceptions;

public class TaxNotFoundException extends RuntimeException {
    public TaxNotFoundException(String message) {
        super(message);
    }
}
