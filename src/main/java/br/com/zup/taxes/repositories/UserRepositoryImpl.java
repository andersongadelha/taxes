package br.com.zup.taxes.repositories;

import br.com.zup.taxes.controllers.dto.RegisterUserDto;
import br.com.zup.taxes.controllers.dto.ResponseRegisterUserDto;
import br.com.zup.taxes.controllers.dto.RoleEnum;
import br.com.zup.taxes.exceptions.RegisteredUserException;
import br.com.zup.taxes.exceptions.RoleNotFoundException;
import br.com.zup.taxes.models.Role;
import br.com.zup.taxes.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository userJpaRepository;
    private final RoleJpaRepository roleJpaRepository;

    @Override
    public ResponseRegisterUserDto save(RegisterUserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        Role role = roleJpaRepository.findByName(userDto.getRole().name())
                .orElseThrow(() -> new RoleNotFoundException("Papel não cadastrado na base."));
        user.setRole(role);
        User userSaved = userJpaRepository.save(user);

        return ResponseRegisterUserDto.builder()
                .id(userSaved.getId())
                .username(userSaved.getUserName())
                .role(RoleEnum.fromString(userSaved.getRole().getName()))
                .build();
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userJpaRepository.existsByUserName(userName);
    }

    @Override
    public User findByUserName(String userName) {
        return userJpaRepository.findByUserName(userName);
    }
}
