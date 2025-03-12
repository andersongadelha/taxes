package br.com.zup.taxes.repositories;

import br.com.zup.taxes.dtos.RegisterUserDto;
import br.com.zup.taxes.dtos.ResponseRegisterUserDto;
import br.com.zup.taxes.dtos.RoleEnum;
import br.com.zup.taxes.dtos.UserDto;
import br.com.zup.taxes.exceptions.RoleNotFoundException;
import br.com.zup.taxes.models.Role;
import br.com.zup.taxes.models.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class UserRepositoryImplTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private RoleJpaRepository roleJpaRepository;

    @InjectMocks
    private UserRepositoryImpl userRepositoryImpl;

    @Test
    void testSave_Success() {
        // Arrange
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setUserName("testUser");
        userDto.setPassword("password123");
        userDto.setRole(RoleEnum.ROLE_USER);

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        User user = new User();
        user.setId(1L);
        user.setUserName("testUser");
        user.setPassword("password123");
        user.setRole(role);

        when(roleJpaRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));
        when(userJpaRepository.save(any(User.class))).thenReturn(user);

        // Act
        ResponseRegisterUserDto response = userRepositoryImpl.save(userDto);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("testUser", response.getUserName());
        assertEquals(RoleEnum.ROLE_USER, response.getRole());

        verify(roleJpaRepository, times(1)).findByName("ROLE_USER");
        verify(userJpaRepository, times(1)).save(any(User.class));
    }

    @Test
    void testSave_RoleNotFoundException() {
        // Arrange
        RegisterUserDto userDto = new RegisterUserDto();
        userDto.setUserName("testUser");
        userDto.setPassword("password123");
        userDto.setRole(RoleEnum.ROLE_ADMIN);

        when(roleJpaRepository.findByName("ROLE_ADMIN")).thenReturn(Optional.empty());

        // Act & Assert
        RoleNotFoundException exception = assertThrows(RoleNotFoundException.class, () -> {
            userRepositoryImpl.save(userDto);
        });

        assertEquals("Papel não cadastrado na base.", exception.getMessage());
        verify(roleJpaRepository, times(1)).findByName("ROLE_ADMIN");
        verify(userJpaRepository, never()).save(any(User.class));
    }

    @Test
    void testExistsByUserName() {
        // Arrange
        String userName = "testUser";
        when(userJpaRepository.existsByUserName(userName)).thenReturn(true);

        // Act
        boolean exists = userRepositoryImpl.existsByUserName(userName);

        // Assert
        assertTrue(exists);
        verify(userJpaRepository, times(1)).existsByUserName(userName);
    }

    @Test
    void testFindByUserName_Success() {
        // Arrange
        String userName = "testUser";

        Role role = new Role();
        role.setId(1L);
        role.setName("ROLE_USER");

        User user = new User();
        user.setId(1L);
        user.setUserName(userName);
        user.setPassword("password123");
        user.setRole(role);

        when(userJpaRepository.findByUserName(userName)).thenReturn(user);

        // Act
        UserDto userDto = userRepositoryImpl.findByUserName(userName);

        // Assert
        assertNotNull(userDto);
        assertEquals(1L, userDto.getId());
        assertEquals("testUser", userDto.getUserName());
        assertEquals("password123", userDto.getPassword());
        assertEquals(role, userDto.getRole());

        verify(userJpaRepository, times(1)).findByUserName(userName);
    }

    @Test
    void testFindByUserName_UserNotFound() {
        // Arrange
        String userName = "nonExistentUser";
        when(userJpaRepository.findByUserName(userName)).thenReturn(null);

        // Act & Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () -> {
            userRepositoryImpl.findByUserName(userName);
        });

        verify(userJpaRepository, times(1)).findByUserName(userName);
    }
}