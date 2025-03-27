package br.com.zup.taxes.external;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "stackSpotIntegration", url = "https://genai-code-buddy-api.stackspot.com/v1/quick-commands")
public interface StackSpotClient {

    @PostMapping("/create-execution/draft-test-bot")
    String postChat(@RequestBody BodySpot bodySpot,
                           @RequestHeader("Authorization") String authorization,
                           @RequestParam(value = "conversation_id", required = false) String conversationId);

    @GetMapping("/callback/{executionId}")
    Object getResult(@RequestHeader("Authorization") String authorization,
                           @PathVariable(value = "executionId") String conversationId);

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    class BodySpot {
        private String input_data;
    }

}