package br.com.zup.taxes.services;

import br.com.zup.taxes.dtos.AuthResponseDto;
import br.com.zup.taxes.dtos.LoginDto;
import br.com.zup.taxes.dtos.RegisterUserDto;
import br.com.zup.taxes.dtos.ResponseRegisterUserDto;
import br.com.zup.taxes.exceptions.RegisteredUserException;
import br.com.zup.taxes.infra.jwt.JwtTokenProvider;
import br.com.zup.taxes.repositories.UserRepository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(value = MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder bCryptPasswordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void register_ShouldRegisterUserSuccessfully() {
        // Arrange
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setUserName("test_user");
        registerUserDto.setPassword("12345");

        ResponseRegisterUserDto expectedResponse = new ResponseRegisterUserDto();
        expectedResponse.setUserName("test_user");

        when(userRepository.existsByUserName("test_user")).thenReturn(false);
        when(bCryptPasswordEncoder.encode("12345")).thenReturn("encoded_password");
        when(userRepository.save(registerUserDto)).thenReturn(expectedResponse);

        // Act
        ResponseRegisterUserDto actualResponse = userService.register(registerUserDto);

        // Assert
        assertNotNull(actualResponse);
        assertEquals("test_user", actualResponse.getUserName());
        verify(userRepository).existsByUserName("test_user");
        verify(bCryptPasswordEncoder).encode("12345");
        verify(userRepository).save(registerUserDto);
    }

    @Test
    void register_ShouldThrowException_WhenUserAlreadyExists() {
        // Arrange
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setUserName("existing_user");

        when(userRepository.existsByUserName("existing_user")).thenReturn(true);

        // Act & Assert
        RegisteredUserException exception = assertThrows(RegisteredUserException.class, () -> {
            userService.register(registerUserDto);
        });

        assertEquals("Usuário já cadastrado.", exception.getMessage());
        verify(userRepository).existsByUserName("existing_user");
    }

    @Test
    void login_ShouldAuthenticateAndReturnToken() {
        // Arrange
        LoginDto loginDto = new LoginDto();
        loginDto.setUserName("test_user");
        loginDto.setPassword("12345");

        Authentication authentication = Mockito.mock(Authentication.class);
        String expectedToken = "jwt_token";

        when(authenticationManager.authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(authentication);
        when(jwtTokenProvider.generateToken(authentication)).thenReturn(expectedToken);

        // Act
        AuthResponseDto authResponse = userService.login(loginDto);

        // Assert
        assertNotNull(authResponse);
        assertEquals(expectedToken, authResponse.getAccessToken());
        verify(authenticationManager).authenticate(Mockito.any(UsernamePasswordAuthenticationToken.class));
        verify(jwtTokenProvider).generateToken(authentication);
    }
}