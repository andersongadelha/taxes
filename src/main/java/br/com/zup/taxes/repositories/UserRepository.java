package br.com.zup.taxes.repositories;

import br.com.zup.taxes.controllers.dto.RegisterUserDto;
import br.com.zup.taxes.controllers.dto.ResponseRegisterUserDto;
import br.com.zup.taxes.controllers.dto.UserDto;

public interface UserRepository {
    ResponseRegisterUserDto save(RegisterUserDto userDto);
    boolean existsByUserName(String userName);
    UserDto findByUserName(String userName);
}
