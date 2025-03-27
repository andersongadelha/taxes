package br.com.zup.taxes.services;

import br.com.zup.taxes.dtos.ChatLoginResponse;
import br.com.zup.taxes.external.AuthClient;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StackSpotServiceImpl implements StackSpotService {

    private final AuthClient authClient;
    @Value("${stackspot.client-id}")
    private String clientId;
    @Value("${stackspot.client-secret}")
    private String clientSecret;
    private static final String GRANT_TYPE = "client_credentials";

    @Override
    public ChatLoginResponse login() {
        var authResponse = authClient.authenticate(
                clientId,
                GRANT_TYPE,
                clientSecret);

        return ChatLoginResponse.builder()
                .accessToken(authResponse.getAccess_token())
                .refreshToken(authResponse.getRefresh_token())
                .build();
    }

}
