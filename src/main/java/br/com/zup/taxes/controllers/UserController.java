package br.com.zup.taxes.controllers;


import br.com.zup.taxes.dtos.AuthResponseDto;
import br.com.zup.taxes.dtos.LoginDto;
import br.com.zup.taxes.dtos.RegisterUserDto;
import br.com.zup.taxes.dtos.ResponseRegisterUserDto;
import br.com.zup.taxes.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Endpoint para salvar usuários na base.")
    @ApiResponses(value = @ApiResponse(
            responseCode = "201",
            description = " Usuário salvo com sucesso.",
            content = @Content(schema = @Schema(implementation = ResponseRegisterUserDto.class))))
    @PostMapping("/registrar")
    public ResponseEntity<ResponseRegisterUserDto> registerUser(@RequestBody RegisterUserDto registerUserDto) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(userService.register(registerUserDto));
    }

    @Operation(summary = "Endpoint para logar no sistema, retorna um token JWT.")
    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginDto loginDto) {
        AuthResponseDto responseDto = userService.login(loginDto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
