package br.com.zup.taxes.infra;

import br.com.zup.taxes.infra.jwt.JwtAuthenticationEntryPoint;
import br.com.zup.taxes.infra.jwt.JwtAuthenticationFilter;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Answers.RETURNS_DEEP_STUBS;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@ExtendWith(MockitoExtension.class)
class SecurityConfigTest {
    @Mock
    private JwtAuthenticationEntryPoint authenticationEntryPoint;
    @Mock
    private JwtAuthenticationFilter authenticationFilter;

    @Test
    void passwordEncoderBeanShouldBeBCryptPasswordEncoder() {
        SecurityConfig securityConfig = new SecurityConfig(null, null, null);
        PasswordEncoder passwordEncoder = securityConfig.passwordEncoder();

        assertThat(passwordEncoder).isNotNull();
        assertThat(passwordEncoder).isInstanceOf(org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.class);
    }

    @Test
    void securityFilterChainShouldBeConfigured() throws Exception {
        SecurityConfig securityConfig = new SecurityConfig(null, authenticationEntryPoint, authenticationFilter);

        HttpSecurity httpSecurity = mock(HttpSecurity.class, RETURNS_DEEP_STUBS);

        SecurityFilterChain securityFilterChain = securityConfig.securityFilterChain(httpSecurity);

        assertThat(securityFilterChain).isNotNull();
    }

}