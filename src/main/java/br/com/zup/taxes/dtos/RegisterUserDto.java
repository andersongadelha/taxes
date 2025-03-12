package br.com.zup.taxes.dtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterUserDto {
    private String userName;
    private String password;
    private RoleEnum role;
}
