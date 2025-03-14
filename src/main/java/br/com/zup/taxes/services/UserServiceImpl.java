package br.com.zup.taxes.services;


import br.com.zup.taxes.dtos.AuthResponseDto;
import br.com.zup.taxes.dtos.LoginDto;
import br.com.zup.taxes.dtos.RegisterUserDto;
import br.com.zup.taxes.dtos.ResponseRegisterUserDto;
import br.com.zup.taxes.exceptions.RegisteredUserException;
import br.com.zup.taxes.infra.jwt.JwtTokenProvider;
import br.com.zup.taxes.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public ResponseRegisterUserDto register(RegisterUserDto registerUserDto) {
        if (userRepository.existsByUserName(registerUserDto.getUserName())){
            throw new RegisteredUserException("Usuário já cadastrado.");
        }
        String cryptPassword = bCryptPasswordEncoder.encode(registerUserDto.getPassword());
        registerUserDto.setPassword(cryptPassword);

        return userRepository.save(registerUserDto);
    }

    @Override
    public AuthResponseDto login(LoginDto dto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                dto.getUserName(),
                dto.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtTokenProvider.generateToken(authentication);

        return AuthResponseDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
