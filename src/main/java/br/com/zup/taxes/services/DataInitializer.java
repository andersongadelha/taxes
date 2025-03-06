package br.com.zup.taxes.services;

import br.com.zup.taxes.controllers.dto.RoleEnum;
import br.com.zup.taxes.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DataInitializer {

    private final RoleRepository roleRepository;

    @PostConstruct
    public void init() {
        createRoleIfNotFound("ROLE_ADMIN");
        createRoleIfNotFound("ROLE_USER");
    }

    private void createRoleIfNotFound(String roleName) {
        if (!roleRepository.existsByName(roleName)) {

            roleRepository.save(RoleEnum.fromString(roleName));
        }
    }
}
