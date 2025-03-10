package br.com.zup.taxes.infra.jwt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.Collections;

@ExtendWith(MockitoExtension.class)
class JwtTokenProviderTest {

    @Mock
    private Authentication authentication;
    @InjectMocks
    private JwtTokenProvider jwtTokenProvider;

    @Test
    void testGenerateToken() {
        // Arrange
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        when(authentication.getName()).thenReturn("testUser");
        when(authentication.getAuthorities()).thenAnswer((Answer<Collection<GrantedAuthority>>) invocation -> authorities);

        // Act
        String token = jwtTokenProvider.generateToken(authentication);

        // Assert
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void testValidateToken_ValidToken() {
        // Arrange
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        when(authentication.getName()).thenReturn("testUser");
        when(authentication.getAuthorities()).thenAnswer((Answer<Collection<GrantedAuthority>>) invocation -> authorities);
        String token = jwtTokenProvider.generateToken(authentication);

        // Act
        boolean isValid = jwtTokenProvider.validateToken(token);

        // Assert
        assertTrue(isValid);
    }

    @Test
    void testValidateToken_InvalidToken() {
        // Arrange
        String invalidToken = "invalid.token.value";

        // Act & Assert
        assertThrows(Exception.class, () -> jwtTokenProvider.validateToken(invalidToken));
    }

    @Test
    void testGetUsername() {
        // Arrange
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        when(authentication.getName()).thenReturn("testUser");
               when(authentication.getAuthorities()).thenAnswer((Answer<Collection<GrantedAuthority>>) invocation -> authorities);
        String token = jwtTokenProvider.generateToken(authentication);

        // Act
        String username = jwtTokenProvider.getUsername(token);

        // Assert
        assertEquals("testUser", username);
    }

    @Test
    void testGetAuthentication() {
        // Arrange
        Collection<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        when(authentication.getName()).thenReturn("testUser");
        when(authentication.getAuthorities()).thenAnswer((Answer<Collection<GrantedAuthority>>) invocation -> authorities);
        String token = jwtTokenProvider.generateToken(authentication);

        // Act
        UsernamePasswordAuthenticationToken auth = jwtTokenProvider.getAuthentication(token);

        // Assert
        assertNotNull(auth);
        assertEquals("testUser", auth.getName());
        assertNotNull(auth.getAuthorities());
        assertEquals(1, auth.getAuthorities().size());
        assertTrue(auth.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_USER")));
    }
}