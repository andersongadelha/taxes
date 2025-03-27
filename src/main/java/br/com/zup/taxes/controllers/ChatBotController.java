package br.com.zup.taxes.controllers;

import br.com.zup.taxes.dtos.ChatInputDto;
import br.com.zup.taxes.dtos.ChatLoginResponse;
import br.com.zup.taxes.services.StackSpotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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

    @PostMapping("/input")
    public ResponseEntity<String> input(@RequestHeader("Authorization") String authorization,
                                        @RequestBody ChatInputDto chatInputDto) {
        var conversationId = stackSpotService.input(authorization, chatInputDto);

        return new ResponseEntity<>(conversationId, HttpStatus.OK);
    }

    @GetMapping("/{executionId}")
    public ResponseEntity<Object> result(@RequestHeader("Authorization") String authorization,
                                         @PathVariable(name = "executionId") String executionId) {
        var result = stackSpotService.result(authorization, executionId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
