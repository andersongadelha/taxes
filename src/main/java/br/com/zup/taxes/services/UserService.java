package br.com.zup.taxes.services;

import br.com.zup.taxes.controllers.dto.RegisterUserDto;
import br.com.zup.taxes.controllers.dto.ResponseRegisterUserDto;

public interface UserService {

    ResponseRegisterUserDto register(RegisterUserDto dto);
}

