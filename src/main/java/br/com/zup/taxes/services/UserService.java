package br.com.zup.taxes.services;

import br.com.zup.taxes.dtos.AuthResponseDto;
import br.com.zup.taxes.dtos.LoginDto;
import br.com.zup.taxes.dtos.RegisterUserDto;
import br.com.zup.taxes.dtos.ResponseRegisterUserDto;

public interface UserService {

    ResponseRegisterUserDto register(RegisterUserDto dto);
    AuthResponseDto login(LoginDto dto);
}

