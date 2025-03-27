package br.com.zup.taxes.services;

import br.com.zup.taxes.dtos.ChatInputDto;
import br.com.zup.taxes.dtos.ChatLoginResponse;

public interface StackSpotService {
    ChatLoginResponse login();
    String input(String authorization, ChatInputDto inputDto);
    Object result(String authorization, String executionId);
}
