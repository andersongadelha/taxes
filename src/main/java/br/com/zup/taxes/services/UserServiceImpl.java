package br.com.zup.taxes.services;


import br.com.zup.taxes.controllers.dto.RegisterUserDto;
import br.com.zup.taxes.controllers.dto.ResponseRegisterUserDto;
import br.com.zup.taxes.exceptions.RegisteredUserException;
import br.com.zup.taxes.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public ResponseRegisterUserDto register(RegisterUserDto registerUserDto) {
        if (userRepository.existsByUserName(registerUserDto.getUserName())){
            throw new RegisteredUserException("Usuário já cadastrado.");
        }
        String cryptPassword = bCryptPasswordEncoder.encode(registerUserDto.getPassword());
        registerUserDto.setPassword(cryptPassword);

        return userRepository.save(registerUserDto);
    }
}
