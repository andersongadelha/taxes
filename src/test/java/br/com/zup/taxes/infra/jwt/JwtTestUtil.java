package br.com.zup.taxes.infra.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
@RequiredArgsConstructor
public class JwtTestUtil {

    private final JwtTokenProvider jwtTokenProvider;


    public String generateToken(String username, String role) {
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        return jwtTokenProvider.generateToken(authentication);
    }

    public String generateInvalidToken() {
        String validToken = generateToken("testUser", "ROLE_USER");

        return validToken.substring(0, validToken.length() - 1) + "x";
    }
}