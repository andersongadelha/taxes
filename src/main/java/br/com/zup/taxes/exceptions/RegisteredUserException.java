package br.com.zup.taxes.exceptions;

public class RegisteredUserException extends RuntimeException {
    public RegisteredUserException(String message) {
        super(message);
    }
}
