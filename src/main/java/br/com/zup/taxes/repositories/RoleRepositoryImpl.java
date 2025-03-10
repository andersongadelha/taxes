package br.com.zup.taxes.repositories;

import br.com.zup.taxes.controllers.dto.RoleEnum;
import br.com.zup.taxes.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository roleJpaRepository;

    @Override
    public boolean existsByName(String name) {
         return roleJpaRepository.existsByName(name);
    }

    @Override
    public void save(RoleEnum role) {
        Role roleToSave = new Role();
        roleToSave.setName(role.name());

        roleJpaRepository.save(roleToSave);
    }
}
