package br.com.zup.taxes.repositories;

import br.com.zup.taxes.dtos.RegisterUserDto;
import br.com.zup.taxes.dtos.ResponseRegisterUserDto;
import br.com.zup.taxes.dtos.RoleEnum;
import br.com.zup.taxes.dtos.UserDto;
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
                .userName(userSaved.getUserName())
                .role(RoleEnum.fromString(userSaved.getRole().getName()))
                .build();
    }

    @Override
    public boolean existsByUserName(String userName) {
        return userJpaRepository.existsByUserName(userName);
    }

    @Override
    public UserDto findByUserName(String userName) {
        User user = userJpaRepository.findByUserName(userName);

        return UserDto.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .password(user.getPassword())
                .role(user.getRole())
                .build();
    }
}
