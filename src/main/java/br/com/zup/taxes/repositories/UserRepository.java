package br.com.zup.taxes.repositories;

import br.com.zup.taxes.dtos.RegisterUserDto;
import br.com.zup.taxes.dtos.ResponseRegisterUserDto;
import br.com.zup.taxes.dtos.UserDto;

public interface UserRepository {
    ResponseRegisterUserDto save(RegisterUserDto userDto);
    boolean existsByUserName(String userName);
    UserDto findByUserName(String userName);
}
