package br.com.zup.taxes.infra;


import br.com.zup.taxes.controllers.dto.RegisterUserDto;
import br.com.zup.taxes.controllers.dto.RoleEnum;
import br.com.zup.taxes.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
@Transactional
public abstract class BaseIT {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setUp() {

        if (!userRepository.existsByUserName("testUser")) {
            RegisterUserDto user = new RegisterUserDto();
            user.setUserName("testUser");
            user.setRole(RoleEnum.ADMIN);
            user.setPassword(passwordEncoder.encode("testPassword"));

            userRepository.save(user);
        }
    }
}