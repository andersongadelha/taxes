package br.com.zup.taxes.controllers;

import br.com.zup.taxes.dtos.ChatLoginResponse;
import br.com.zup.taxes.services.StackSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatBotController {

    private final StackSpotService stackSpotService;

    @PostMapping("/login")
    public ResponseEntity<ChatLoginResponse> login() {
        ChatLoginResponse responseDto = stackSpotService.login();

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

}
