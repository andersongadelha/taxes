package br.com.zup.taxes.controllers;


import br.com.zup.taxes.controllers.dto.AuthResponseDto;
import br.com.zup.taxes.controllers.dto.LoginDto;
import br.com.zup.taxes.controllers.dto.RegisterUserDto;
import br.com.zup.taxes.controllers.dto.ResponseRegisterUserDto;
import br.com.zup.taxes.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/registrar")
    public ResponseEntity<ResponseRegisterUserDto> registerUser(@RequestBody RegisterUserDto registerUserDto){
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.register(registerUserDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto){
        AuthResponseDto responseDto = userService.login(loginDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
