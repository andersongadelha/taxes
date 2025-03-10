package br.com.zup.taxes.services;

import br.com.zup.taxes.controllers.dto.AuthResponseDto;
import br.com.zup.taxes.controllers.dto.LoginDto;
import br.com.zup.taxes.controllers.dto.RegisterUserDto;
import br.com.zup.taxes.controllers.dto.ResponseRegisterUserDto;

public interface UserService {

    ResponseRegisterUserDto register(RegisterUserDto dto);
    AuthResponseDto login(LoginDto dto);
}

