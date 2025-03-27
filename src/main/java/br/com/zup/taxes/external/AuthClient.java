package br.com.zup.taxes.external;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "authClientTest", url = "https://idm.stackspot.com/zup/oidc/oauth/")
public interface AuthClient {

    @PostMapping(value = "/token", consumes = "application/x-www-form-urlencoded")
    AuthResponse authenticate(@RequestParam("client_id") String clientId,
                              @RequestParam("grant_type") String grantType,
                              @RequestParam("client_secret") String clientSecret);

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    class AuthResponse {
        private String access_token;
        private String refresh_token;

    }
}